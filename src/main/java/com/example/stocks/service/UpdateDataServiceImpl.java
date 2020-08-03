package com.example.stocks.service;

import com.example.stocks.domain.Company;
import com.example.stocks.repository.CompanyRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import utilities.EmailService;
import utilities.Executor;
import utilities.UpdateDataService;

import java.io.IOException;

public class UpdateDataServiceImpl  {

    @Autowired
    private CompanyRepository companyRepository;




    public UpdateDataServiceImpl() {


    }


    public static int getHistoricalData(String symbol) throws IOException {
        //when a new company is added to the db this is called
        Process p = ExecutorImpl.execute("aquisition.py " + symbol);
        return 0;
    }
    public static Company getCompanyData(String symbol) throws IOException {

        Gson g = new Gson();
        Process p = ExecutorImpl.execute("companyData.py "+symbol); //ia datele din api
        ReaderImpl r = new ReaderImpl();   ///MAKE THEM STATIC OR SMTH
        String  str = r.readConsoleOutput(p);
        Company c1 = g.fromJson(str, Company.class); //formeaza obiectul din outputul procesului
        return c1;
    }

    public static  int appendLastTradingDay(String symbol) throws IOException {
        //for existing files the last trading day is appended
        try {

            Process p = ExecutorImpl.execute("FileUpdate.py " + symbol);
        }

        catch(Exception e){
            EmailServiceImpl.sendEmailToAdmin(e.getMessage());
        }
        return 0;
    }


    public int updateFiles() {
        return 0;
    }

    public int getHistoricalDataForFiles() {
        return 0;
    }
}
