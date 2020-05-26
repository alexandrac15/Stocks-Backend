package com.example.stocks.controller;

import com.example.stocks.domain.Company;
import com.example.stocks.domain.Graph;
import com.example.stocks.service.CompanyService;
import com.example.stocks.service.ExecutorImpl;
import com.example.stocks.service.ReaderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utilities.Executor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

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
    Graph  getCompany() throws IOException, ParseException {

        ExecutorImpl e = new ExecutorImpl();

       Process p= e.execute("loadData.py 5");
        ReaderImpl r=new ReaderImpl();   ///MAKE THEM STATIC OR SMTH
        Graph g= r.readHistoricData(p);
        String s=g.toString();
       return g;

    }


}
