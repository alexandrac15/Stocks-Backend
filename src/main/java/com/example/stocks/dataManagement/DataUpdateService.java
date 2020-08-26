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

    private String companySymbol;
    private String predictionModelPath;
    private Integer companyId;
    @Autowired
    private PredictionService predictionService;

    DataUpdateService(String companySymbol, String predictionModelPath, Integer companyId, PredictionService predictionService){
        this.companySymbol = companySymbol;
        this.predictionService=predictionService;
        this.predictionModelPath = predictionModelPath;
        this.companyId = companyId;
    }
    private void updateHistoricData(){
        Process p = null;
        try {
            p = ExecutorImpl.execute("FileUpdate.py "+ this.companySymbol);
        } catch (IOException e) {
            System.out.print("Error encountered when updating historical data for company " + companySymbol + " \nError is " + e.getMessage());
            throw new RuntimeException("Error encountered when updating historical data for company " + companySymbol + " \nError is " + e.getMessage());
            /// TODO: SEND MAIL
        }

        if(p == null)
        {
            System.out.print("Update Historic Data Process is null for some reason for company " + companySymbol);
            /// TODO: SEND MAIL
            throw new RuntimeException("Update Historic Data Process is null for some reason for company " + companySymbol);
        }

        try {
            if(!p.waitFor(5, TimeUnit.MINUTES)) {
                System.out.print("Process time expired for company " + companySymbol);
                throw new RuntimeException("Process time expired for company " + companySymbol);
            }
        } catch (InterruptedException e) {
            System.out.print("Process error during execution for company " + companySymbol + "error is " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Process error during execution for company " + companySymbol + "error is " + e.getMessage());
        }

       return;
    }



    private ArrayList<Float> updatePredictions(){

        Process p = null;
        String s = null;

        try {
             p = ExecutorImpl.execute("model_predict.py "+ predictionModelPath);
        } catch (IOException e) {
            System.out.print("Error encountered when predicting historical data for company " + companySymbol + " \nError is " + e.getMessage());
            throw new RuntimeException("Error encountered when predicting historical data for company " + companySymbol + " \nError is " + e.getMessage());
        }

        if(p == null)
        {
            System.out.print("Update Prediction Process is null for some reason for company " + companySymbol);
            throw new RuntimeException("\"Update Prediction Process is null for some reason for company \" + companySymbol");
        }

        try {
            if(!p.waitFor(15, TimeUnit.MINUTES)) {
                System.out.print("Process time expired for company " + companySymbol );
                throw new RuntimeException("Process time expired for company " + companySymbol);
            }
        } catch (InterruptedException e) {
            System.out.print("Process error during execution for company " + companySymbol + "error is " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Process error during execution for company " + companySymbol + "error is " + e.getMessage());
        }

        Reader r = new ReaderImpl();
        try {
            s = r.readConsoleOutput(p);
        } catch (IOException e) {
            System.out.print("Could not read standard output from prediction script for company " + companySymbol);
            e.printStackTrace();
            throw new RuntimeException("Could not read standard output from prediction script for company " + companySymbol);
        }

        return JsonManipulationUtilities.extractPredictionsFromPythonOutput(s);
    }

    @Override
    public void run() {

        Graph g;

//        try {
//            updateHistoricData();
//        } catch (RuntimeException e) {
//            //// TODO SEND MAIL
//            return;
//        }

        if(this.predictionModelPath != null) {
            try {
                g = new Graph(updatePredictions());
            } catch (RuntimeException | ParseException e) {
                //// TODO SEND MAIL
                return;
            }

            predictionService.addPrediction(companyId, g);
        }

        System.out.print("All ok");
        return;
    }
}
