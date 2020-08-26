package com.example.stocks.dataManagement;

import com.example.stocks.domain.Company;
import com.example.stocks.domain.MLModel;
import com.example.stocks.domain.Sector;
import com.example.stocks.notification.EmailServiceImpl;
import com.example.stocks.services.CompanyService;
import com.example.stocks.services.PredictionService;
import com.example.stocks.services.SectorService;
import com.example.stocks.utilities.CompanyUtils;
import com.example.stocks.vechi.service.ExecutorImpl;
import com.example.stocks.vechi.service.ReaderImpl;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import com.example.stocks.utilities.CompanyDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableScheduling
public class DataServiceImpl {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private SectorService sectorService;
    @Autowired
    private PredictionService predictionService;


    public DataServiceImpl() {

    }
    @Scheduled(cron = "30 09 18 * * 1-7")
    public void updateHistoricalData() throws IOException {
        System.out.println("S-A EXECUTAT 1 ");
        List<Company> symbols=companyService.getCompanies();
        //ia din baza de date toate companiile, ca sq stie ce fisiere trebuie sa updateze. ficare companie din db
        // are un fisier in folderul historical_data
        ArrayList<Thread> threads;
        for (Company company: symbols){
            String modelPath = null;
            try {
                List<MLModel> models = company.getModels();
                MLModel mlModel = CompanyUtils.getDefaultModel(models);
                modelPath = (mlModel == null ? null : mlModel.getModelPath());

            }catch (Exception e){

            }
            new Thread(new DataUpdateService(company.getSymbol(), modelPath, company.getId() ,predictionService)).start();
        }
        System.out.println("S-A EXECUTAT 2 ");

    }
    public static String getHistoricalData(String symbol) throws IOException {
        //when a new company is added to the db this is called
        Process p = ExecutorImpl.execute("aquisition.py " + symbol);
        ReaderImpl r = new ReaderImpl();
        String  str = r.readConsoleOutput(p);
        return str ;
    }
    public  Company getCompanyData(String symbol) throws IOException {

        Gson g = new Gson();
        Process p = ExecutorImpl.execute("companyData.py "+symbol); //ia datele din api
        ReaderImpl r = new ReaderImpl();   ///MAKE THEM STATIC OR SMTH
        String  str = r.readConsoleOutput(p);
        System.out.println(str);
        CompanyDTO c1 = g.fromJson(str, CompanyDTO.class); //formeaza obiectul din outputul procesului
        String sectorName = c1.getSector();
        Sector s= sectorService.getSectorByName(sectorName); //pentru legatura cu tabelul sector
        Company company=new Company(c1.getCompanyName(),c1.getSymbol(),c1.getEmployees(),c1.getIndustry(),c1.getWebsite(),c1.getDescription(),c1.getCEO(),s,c1.getCountry(),c1.getUrl());
        return company;
    }

    public static  int appendLastTradingDay(String symbol) throws IOException {
        //for existing files the last trading day is appended
        try {

            Process p = ExecutorImpl.execute("FileUpdate.py " + symbol);
        }

        catch(Exception e){
            //a mail is sent to the admin in case smth goes wrong
            EmailServiceImpl.sendEmailToAdmin(e.getMessage()+ "for symbol "+ symbol);
        }
        return 0;
    }


    public int updateFiles() {
        return 0;
    }


}
