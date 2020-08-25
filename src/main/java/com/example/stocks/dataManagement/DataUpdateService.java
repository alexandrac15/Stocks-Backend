package com.example.stocks.dataManagement;

import com.example.stocks.domain.Company;
import com.example.stocks.services.CompanyService;
import com.example.stocks.services.PredictionService;
import com.example.stocks.utilities.CompanyUtils;
import com.example.stocks.vechi.service.ExecutorImpl;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import com.example.stocks.notification.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class DataUpdateService implements Runnable {

    private String companySymbol;
    private PredictionService predictionService;
    @Autowired
    private CompanyService companyService;

    DataUpdateService(String companySymbol, PredictionService predictionService){
        this.companySymbol = companySymbol;
        this.predictionService=predictionService;
    }
    private boolean  updateHistoricData(){
        Process p = null;
        try {
            p = ExecutorImpl.execute("FileUpdate.py "+ this.companySymbol);
        } catch (IOException e) {
            System.out.print("Error encountered when updating historical data for company " + companySymbol + " \nError is " + e.getMessage());
            return false;
            /// TODO: SEND MAIL
        }

        if(p == null)
        {
            System.out.print("Process is null for some reason for company " + companySymbol );
            /// TODO: SEND MAIL
            return false;
        }

        try {
            if(!p.waitFor(5, TimeUnit.MINUTES)) {
                System.out.print("Process time expired for company " + companySymbol );
                return false; /// TODO: SEND MAIL
            }
        } catch (InterruptedException e) {
            System.out.print("Process error during execution for company " + companySymbol + "error is " + e.getMessage());
            e.printStackTrace(); /// TODO: SEND MAIL
        }

       return true;
    }



    private boolean updatePredictions(){


        Process p = null;
        try {
              Company company=companyService.getCompanyBySymbol(companySymbol);
              p=ExecutorImpl.execute("model_predict.py "+ CompanyUtils.getDefaultModel(company.getModels()).getModelPath());
        } catch (IOException e) {
            System.out.print("Error encountered when updating historical data for company " + companySymbol + " \nError is " + e.getMessage());
            return false;
            /// TODO: SEND MAIL
        }

        if(p == null)
        {
            System.out.print("Process is null for some reason for company " + companySymbol );
            /// TODO: SEND MAIL
            return false;
        }

        try {
            if(!p.waitFor(5, TimeUnit.MINUTES)) {
                System.out.print("Process time expired for company " + companySymbol );
                return false; /// TODO: SEND MAIL
            }
        } catch (InterruptedException e) {
            System.out.print("Process error during execution for company " + companySymbol + "error is " + e.getMessage());
            e.printStackTrace(); /// TODO: SEND MAIL
        }

        return true;




    }
    @Override
    public void run() {

        if(!updateHistoricData()) {
            return;
        }




        System.out.print("All ok");

    }
}
