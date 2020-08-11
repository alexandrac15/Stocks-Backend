package com.example.stocks.controller;

import com.example.stocks.dataManagement.DataServiceImpl;
import com.example.stocks.domain.Company;
import com.example.stocks.domain.Graph;

import com.example.stocks.domain.Sector;
import com.example.stocks.repositories.CompanyRepository;
import com.example.stocks.repositories.SectorRepository;
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
public class CompanyController {

    @Autowired
    private CompanyService companyservice;
    @Autowired
    private DataServiceImpl dataService;



    @GetMapping("/companies/{id}")
    Company getCompany(@PathVariable int id){

        return companyservice.getCompanyById(id);
    }

    @GetMapping("/companies")
    List<Company> getCompanies() {

        return companyservice.getCompanies();

    }

    @GetMapping("/companies/home")
    String home(){

        return "Welcome!";
    }

    @PostMapping("/companies/{symbol}")
    Company addCompany(@PathVariable String symbol) throws IOException {
        Company company = dataService.getCompanyData(symbol);
       String path= DataServiceImpl.getHistoricalData(symbol);
       company.setHistoricDataPath(path);
        return companyservice.addCompany(company); //adauga in baza
    }
    @GetMapping("/sectors")
    List<Sector> getSectors(){
        return companyservice.getSectors();
    }



}






