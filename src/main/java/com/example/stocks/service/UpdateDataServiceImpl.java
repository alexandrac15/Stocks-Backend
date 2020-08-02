package com.example.stocks.service;

import com.example.stocks.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import utilities.EmailService;
import utilities.Executor;
import utilities.UpdateDataService;

import java.io.IOException;

public class UpdateDataServiceImpl implements UpdateDataService {

    @Autowired
    private CompanyRepository companyRepository;
    private EmailService emailService;

    private Executor executor;

    public UpdateDataServiceImpl(EmailService emailService, Executor executor) {
        this.emailService = emailService;
        this.executor = executor;
    }

    @Override
    public int getHistoricalData(String symbol) {
        //when a new company is added to the db this is called
        return 0;
    }

    @Override
    public int appendLastTradingDay(String symbol) throws IOException {
        //for existing files the last trading day is appended
        try {

            Process p = executor.execute("FileUpdate.py " + symbol);
        }

        catch(Exception e){
            emailService.sendEmailToAdmin(e.getMessage());
        }
        return 0;
    }

    @Override
    public int updateFiles() {
        return 0;
    }

    @Override
    public int getHistoricalDataForFiles() {
        return 0;
    }
}
