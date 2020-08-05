package com.example.stocks.controller;

import com.example.stocks.domain.Company;
import com.example.stocks.domain.Graph;
import com.example.stocks.service.*;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utilities.Reader;
import utilities.UpdateDataService;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.io.IOException;


import java.text.ParseException;
import java.util.Properties;

@RestController
@CrossOrigin(origins = "*")
public class CompanyController {


    @Autowired
    private CompanyService companyservice;
    private Reader ds=new ReaderImpl();
//    @PostMapping("/companies")
//    Company addCompany(@RequestBody Company company) throws IOException {
//        return companyservice.addCompany(company);
//    }



    // Single item

    @GetMapping("/companies/{id}")
    Company getCompany(@PathVariable int id) {
        return companyservice.getCompanyById(id);

    }

    @GetMapping("/home")
    Graph getCompany() throws IOException, ParseException {
        //asta va fi cu permisiion
        ExecutorImpl e = new ExecutorImpl();

        Process p = e.execute("loadData.py 5");
        ReaderImpl r = new ReaderImpl();   ///MAKE THEM STATIC OR SMTH
        Graph g = r.readHistoricData(p);
        String s = g.toString();
        return g;

    }

    @PostMapping("/companies/{symbol}")
    Company addCompany(@PathVariable String  symbol) throws IOException {
         Company c=UpdateDataServiceImpl.getCompanyData( symbol);
         UpdateDataServiceImpl.getHistoricalData(symbol);
        return companyservice.addCompany(c); //adauga in vbaza
    }

    @PostMapping("/err")
    void fun() throws IOException, InterruptedException {

        UpdateDataServiceImpl.appendLastTradingDay("ceva");
    }




}






