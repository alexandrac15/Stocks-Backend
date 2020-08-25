package com.example.stocks.controller;

import com.example.stocks.dataManagement.DataServiceImpl;
import com.example.stocks.domain.Company;
import com.example.stocks.domain.Graph;

import com.example.stocks.services.CompanyService;
import com.example.stocks.vechi.service.ExecutorImpl;
import com.example.stocks.vechi.service.ReaderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.stocks.utilities.Reader;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class CompanyController {

    @Autowired
    private CompanyService companyservice;
    @Autowired
    private DataServiceImpl dataService;


    @GetMapping("/companies/{id}")
    Company getCompany(@PathVariable int id) {
        return companyservice.getCompanyById(id);
    }

    @GetMapping("/companies")
    List<Company> getCompanies() {
        return companyservice.getCompanies();
    }

    @GetMapping("/companies/home")
    String home() {
        return "Welcome!";
    }

    @PostMapping("/companies/{symbol}")
    Company addCompany(@PathVariable String symbol) throws IOException {
        Company company = dataService.getCompanyData(symbol);
        String path = DataServiceImpl.getHistoricalData(symbol);
        company.setHistoricDataPath(path);
        System.out.println("INAINTE DE DB");
        System.out.println(company);

        return companyservice.addCompany(company); //adauga in baza
    }



    @GetMapping("/graph/{idCompany}/{days}")
    Graph getHistoricChart(@PathVariable int idCompany, @PathVariable int days) throws IOException, ParseException {
            Company c= companyservice.getCompanyById(idCompany);
            Process p= ExecutorImpl.execute("loadData.py \""+c.getHistoricDataPath()+"\" "+days);
        System.out.println("a"+c.getHistoricDataPath());
            Reader r=new ReaderImpl();   ///MAKE THEM STATIC OR SMTH
            Graph g= r.readHistoricData(p);
            System.out.println(g);
            return g;



    }


    @GetMapping("/sector/companies/{idSector}")
    List<Company> getCompaniesBysector(@PathVariable int idSector){
        return companyservice.getCompaniesBySector(idSector);
    }



}






