package com.example.stocks.services;

import com.example.stocks.repositories.CompanyRepository;
import com.example.stocks.vechi.service.ExecutorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.stocks.domain.Company;

import java.io.IOException;

@Component
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;



    public Company addCompany( Company company) throws IOException {
        System.out.println("hey" +company);
         ExecutorImpl.execute("C:\\Users\\aalex\\source\\repos\\DataTest\\aquisition.py");
         return companyRepository.save(company);
    }
    public Company getCompanyById(int id){
        return companyRepository.findById(id).get();
    }
}
