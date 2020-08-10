package com.example.stocks.dataManagement;

import com.example.stocks.domain.Company;
import com.example.stocks.domain.Sector;
import com.example.stocks.notification.EmailServiceImpl;
import com.example.stocks.repositories.CompanyRepository;
import com.example.stocks.repositories.SectorRepository;
import com.example.stocks.vechi.service.ExecutorImpl;
import com.example.stocks.vechi.service.ReaderImpl;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import utilities.CompanyDTO;

import java.io.IOException;
import java.util.List;

@Configuration
@EnableScheduling
public class DataServiceImpl {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private SectorRepository sectorRepository;




    public DataServiceImpl() {


    }
    @Scheduled(cron = "10 22 18 * * 1-7")
    public void scheduledexec() throws IOException {
        System.out.println("S-A EXECUTAT ");
        List<Company> symbols=companyRepository.findAll();
        //ia din baza de date toate companiile, ca sq stie ce fisiere trebuie sa updateze. ficare companie din db
        // are un fisier in folderul historical_data
        for (Company company: symbols){
            ExecutorImpl.execute("FileUpdate.py "+company.getSymbol());
        }
        System.out.println("S-A EXECUTAT ");

    }
    public static int getHistoricalData(String symbol) throws IOException {
        //when a new company is added to the db this is called
        Process p = ExecutorImpl.execute("aquisition.py " + symbol);
        return 0;
    }
    public  Company getCompanyData(String symbol) throws IOException {

        Gson g = new Gson();
        Process p = ExecutorImpl.execute("companyData.py "+symbol); //ia datele din api
        ReaderImpl r = new ReaderImpl();   ///MAKE THEM STATIC OR SMTH
        String  str = r.readConsoleOutput(p);
        CompanyDTO c1 = g.fromJson(str, CompanyDTO.class); //formeaza obiectul din outputul procesului
        String strs = c1.getSector();
        Sector s= sectorRepository.findBySector(strs).get(0);
        Company cy=new Company(c1.getCompanyName(),c1.getSymbol(),c1.getEmployees(),c1.getIndustry(),c1.getWebsite(),c1.getDescription(),c1.getCEO(),s,c1.getCountry());
        return cy;
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