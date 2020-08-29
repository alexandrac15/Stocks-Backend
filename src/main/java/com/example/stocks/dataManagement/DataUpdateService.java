package com.example.stocks.dataManagement;

import com.example.stocks.domain.Company;
import com.example.stocks.domain.Graph;
import com.example.stocks.domain.MLModel;
import com.example.stocks.services.CompanyService;
import com.example.stocks.services.PredictionService;
import com.example.stocks.utilities.CompanyUtils;
import com.example.stocks.utilities.JsonManipulationUtilities;
import com.example.stocks.utilities.Reader;
import com.example.stocks.vechi.service.ExecutorImpl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import com.example.stocks.notification.EmailServiceImpl;
import com.example.stocks.vechi.service.ReaderImpl;
import com.google.gson.Gson;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;


public class DataUpdateService implements Runnable {

    private static final int STATUS_SUCCESS = 0 ;

    private String companySymbol;
    private String predictionModelPath;
    private Integer companyId;
    private Boolean updateHistoricalData;
    @Autowired
    private PredictionService predictionService;

    DataUpdateService(String companySymbol, String predictionModelPath, Integer companyId, PredictionService predictionService, Boolean updateHistoricalData){
        this.companySymbol = companySymbol;
        this.predictionService=predictionService;
        this.predictionModelPath = predictionModelPath;
        this.companyId = companyId;
        this.updateHistoricalData = updateHistoricalData;
    }
    private void updateHistoricData(){
        Process p = null;

        try {
            p = ExecutorImpl.execute("FileUpdate.py "+ this.companySymbol);
        } catch (IOException e) {
            throw new RuntimeException("Error encountered when updating historical data for company " + companySymbol + " \nError is " + e.getMessage());
        }

        try {
            if(!p.waitFor(5, TimeUnit.MINUTES)) {
                p.destroy();
                throw new RuntimeException("Process time expired for company " + companySymbol + " " + p.exitValue());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Process error during execution for company " + companySymbol + "error is " + e.getMessage());
        }

        int processStatus = p.exitValue();
        if(processStatus != STATUS_SUCCESS)
        {
            throw new RuntimeException("Historic Data process exited with status " + processStatus);
        }

       return;
    }



    private ArrayList<Float> updatePredictions(){

        Process p = null;
        String s = null;

        try {
             p = ExecutorImpl.execute("model_predict.py "+ predictionModelPath);
        } catch (IOException e) {
            throw new RuntimeException("Error encountered when predicting historical data for company " + companySymbol + " \nError is " + e.getMessage());
        }

        try {
            if(!p.waitFor(15, TimeUnit.MINUTES)) {
                throw new RuntimeException("Process time expired for company " + companySymbol + ' ');
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Process error during execution for company " + companySymbol + "error is " + e.getMessage());
        }

        int processStatus = p.exitValue();
        if(processStatus != STATUS_SUCCESS)
        {
            throw new RuntimeException("Update Prediction process exited with status " + processStatus);
        }

        Reader r = new ReaderImpl();
        try {
            s = r.readConsoleOutput(p);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not read standard output from prediction script for company " + companySymbol);
        }

        return JsonManipulationUtilities.extractPredictionsFromPythonOutput(s);
    }

    @Override
    public void run() {

        Graph g;

        if(this.updateHistoricalData) {
            try {
                updateHistoricData();
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                EmailServiceImpl.sendEmailToAdmin(e.getMessage());
                return;
            }
            System.out.print("Historical data updated for " + this.companySymbol+"\n");

        }

        if(this.predictionModelPath != null) {
            try {
                g = new Graph(updatePredictions());
            } catch (RuntimeException | ParseException e) {
                System.out.println(e.getMessage());
                EmailServiceImpl.sendEmailToAdmin(e.getMessage());
                return;
            }
            System.out.print("Predictions updated for " + this.companySymbol+"\n");

            predictionService.addPrediction(companyId, g);
        }

        System.out.print("All ok for company " + this.companySymbol+"\n");
        return;
    }
}
