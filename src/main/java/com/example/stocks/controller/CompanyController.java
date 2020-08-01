package com.example.stocks.controller;

import com.example.stocks.domain.Company;
import com.example.stocks.domain.Graph;
import com.example.stocks.service.*;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/companies")
    Company addCompany(@RequestBody Company company) {
        return companyservice.addCompany(company);
    }

    // Single item

    @GetMapping("/companies/{id}")
    Company getCompany(@PathVariable int id) {
        return companyservice.getCompanyById(id);

    }

    @GetMapping("/home")
    Graph getCompany() throws IOException, ParseException {

        ExecutorImpl e = new ExecutorImpl();

        Process p = e.execute("loadData.py 5");
        ReaderImpl r = new ReaderImpl();   ///MAKE THEM STATIC OR SMTH
        Graph g = r.readHistoricData(p);
        String s = g.toString();
        return g;

    }

    @PostMapping("/companiestest/{symbol}")
    Company addCompany1(@PathVariable String  symbol) throws IOException {
        Gson g = new Gson();
//        Company company = new Company("companie test1j");
//        String s = "{ name : " + "\"companie chiar din string\" }";
//
//        Company c1 = g.fromJson(s, Company.class);

        ExecutorImpl e = new ExecutorImpl();

        Process p = e.execute("companyData.py "+symbol);
        ReaderImpl r = new ReaderImpl();   ///MAKE THEM STATIC OR SMTH
        String  str = r.readConsoleOutput(p);

        Company c1 = g.fromJson(str, Company.class);
        System.out.println(c1);
        return companyservice.addCompany(c1);
    }

    @PostMapping("/err")
    void fun() throws IOException {
        UpdateDataService im=new UpdateDataServiceImpl (new EmailServiceImpl(),new ExecutorImpl()) ;
        im.appendLastTradingDay("ceva");
    }



}






