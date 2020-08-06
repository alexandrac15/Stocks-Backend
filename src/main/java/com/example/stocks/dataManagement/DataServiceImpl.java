package com.example.stocks.dataManagement;

import com.example.stocks.domain.Company;
import com.example.stocks.notification.EmailServiceImpl;
import com.example.stocks.repositories.CompanyRepository;
import com.example.stocks.vechi.service.ExecutorImpl;
import com.example.stocks.vechi.service.ReaderImpl;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.util.List;

@Configuration
@EnableScheduling
public class DataServiceImpl {

    @Autowired
    private CompanyRepository companyRepository;




    public DataServiceImpl() {


    }
    @Scheduled(cron = "10 13 11 * * 1-6")
    public void scheduledexec() throws IOException {
        System.out.println("S-A EXECUTAT ");
        List<Company> symbols=companyRepository.findAll();
        //ia din baza de date toate companiile, ca sq stie ce fisiere trebuie sa updateze. ficare companie din db
        // are un fisier in folderul historical_data
        for (Company company: symbols){
            ExecutorImpl.execute("FileUpdate.py "+company.getSymbol());
        }


    }
    public static int getHistoricalData(String symbol) throws IOException {
        //when a new company is added to the db this is called
        Process p = ExecutorImpl.execute("aquisition.py " + symbol);
        return 0;
    }
    public static Company getCompanyData(String symbol) throws IOException {

        Gson g = new Gson();
        Process p = ExecutorImpl.execute("companyData.py "+symbol); //ia datele din api
        ReaderImpl r = new ReaderImpl();   ///MAKE THEM STATIC OR SMTH
        String  str = r.readConsoleOutput(p);
        Company c1 = g.fromJson(str, Company.class); //formeaza obiectul din outputul procesului
        return c1;
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
