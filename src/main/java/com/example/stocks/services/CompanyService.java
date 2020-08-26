package com.example.stocks.services;

import com.example.stocks.domain.Sector;
import com.example.stocks.repositories.CompanyRepository;
import com.example.stocks.repositories.SectorRepository;
import com.example.stocks.vechi.service.ExecutorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.stocks.domain.Company;

import java.io.IOException;
import java.util.List;

@Component
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private SectorRepository sectorRepository;



    public Company addCompany( Company company) throws IOException {
            return companyRepository.save(company);
    }
    public Company getCompanyById(int id){
        return companyRepository.findById(id).get();
    }

    public Company getCompanyBySymbol(String symbol){
         return companyRepository.findCompanyBySymbol(symbol);
    }

    public List<Company> getCompaniesBySector(int idSector){
        //return sectorRepository.findAllBySector(idSector);
        return companyRepository.findCompaniesBySectorId(idSector);
    }
    public List<Company> getCompanies(){
        return companyRepository.findAll();
    }




}
