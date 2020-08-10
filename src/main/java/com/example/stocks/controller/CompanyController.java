package com.example.stocks.controller;

import com.example.stocks.dataManagement.DataServiceImpl;
import com.example.stocks.domain.Company;
import com.example.stocks.domain.Graph;

import com.example.stocks.domain.Sector;
import com.example.stocks.services.CompanyService;
import com.example.stocks.vechi.service.ExecutorImpl;
import com.example.stocks.vechi.service.ReaderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utilities.Reader;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class




CompanyController {


    @Autowired
    private CompanyService companyservice;
    private Reader ds=new ReaderImpl();
//    @PostMapping("/companies")
//    Company addCompany(@RequestBody Company company) throws IOException {
//        return companyservice.addCompany(company);
//    }
    @Autowired
    private DataServiceImpl dataService;



    // Single item

    @GetMapping("/companies/{id}")
    Company getCompany(@PathVariable int id) {
        return companyservice.getCompanyById(id);

    }
    @GetMapping("/sectors")
    List<Sector> getSectors() {
        return companyservice.getSectors();

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
         Company c= dataService.getCompanyData( symbol);
         DataServiceImpl.getHistoricalData(symbol);
        return companyservice.addCompany(c); //adauga in vbaza
    }

    @PostMapping("/err")
    void fun() throws IOException, InterruptedException {

        DataServiceImpl.appendLastTradingDay("ceva");
    }




}






