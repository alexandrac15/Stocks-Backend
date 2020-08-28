package com.example.stocks.controller;

import com.example.stocks.dataManagement.DataServiceImpl;
import com.example.stocks.domain.Company;
import com.example.stocks.domain.Graph;

import com.example.stocks.domain.Pair;
import com.example.stocks.notification.EmailService;
import com.example.stocks.notification.EmailServiceImpl;
import com.example.stocks.repositories.CompanyRepository;
import com.example.stocks.services.CompanyService;
import com.example.stocks.services.MLModelService;
import com.example.stocks.services.PredictionService;
import com.example.stocks.services.SectorService;
import com.example.stocks.utilities.DateFormatter;
import com.example.stocks.utilities.JsonManipulationUtilities;
import com.example.stocks.vechi.service.ExecutorImpl;
import com.example.stocks.vechi.service.ReaderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.stocks.utilities.Reader;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin(origins = "*")
public class CompanyController {

    @Autowired
    private CompanyService companyservice;
    @Autowired
    private DataServiceImpl dataService;
    @Autowired
    private PredictionService predictionService;
    @Autowired
    private MLModelService mlModelService;
    @Autowired
    private SectorService sectorService;

    @GetMapping("/companies/{id}")
    Company getCompany(@PathVariable int id) {
        Company c=companyservice.getCompanyById(id);
        return companyservice.getCompanyById(id);
    }
    @GetMapping ("/stuff")
    void ceva() throws IOException, ParseException {
        dataService.updateHistoricalData();
   }

    @GetMapping ("/updatePredictions")
    void updatePredictionList() throws IOException, ParseException {
        dataService.updatePredictions();
    }

   @GetMapping("/graph/prediction/{idCompany}/{previousDays}")
   public Graph getChart(@PathVariable  int idCompany, @PathVariable int previousDays) throws IOException, ParseException {
        //service this
        Company c = companyservice.getCompanyById(idCompany);
        Process p= ExecutorImpl.execute("loadData.py \""+c.getHistoricDataPath()+"\" "+previousDays);
        System.out.println("a"+c.getHistoricDataPath());
        Reader r=new ReaderImpl();   ///MAKE THEM STATIC OR SMTH
        Graph graphH= r.readHistoricData(p);
        Graph graphP=predictionService.findPredictionByCompanyID(idCompany);
        Graph chart= new Graph(graphH, graphP);
        return chart;
   }

    @GetMapping("/companies")
    List<Company> getCompanies() {
        return companyservice.getCompanies();
    }

    @GetMapping("/companies/home")
    String home() {
        return "Welcome!";
    }

    @Autowired
    CompanyRepository companyRepository;

    @GetMapping("/companies/updateAll")
    void update() throws IOException, InterruptedException {
        List<Company> companies = companyRepository.findAll();
        for(Company company : companies)
        {
            String jsonString=JsonManipulationUtilities.getJsonFromFile(company.getSector().getDefaultModelpath());
            List<String> tokens = JsonManipulationUtilities.getFeatures(jsonString, "\"PATH");

            ArrayList<String> replacements = new ArrayList<>();

            for(String token: tokens){

                if(token.equals("REPLACE"))
                {
                    replacements.add("\\\"" + company.getSymbol() + "\\\", ");
                }
                else {
                    replacements.add("");
                }
            }

            jsonString = JsonManipulationUtilities.replaceFeatures(jsonString, "\"PATH", replacements);

            replacements = new ArrayList<String>();
            replacements.add("\\\"D:\\\\DEFAULT_SECTOR_MODELS\\\\"+company.getSector().getSector().replace(" ", "_")+"\\\\"+company.getSymbol()+"\\\\tensorboard\\\",");
            jsonString = JsonManipulationUtilities.replaceFeatures(jsonString, "TENSORBOARD_PATH_CONFIG", replacements);

            replacements = new ArrayList<String>();
            replacements.add("\\\"D:\\\\DEFAULT_SECTOR_MODELS\\\\"+company.getSector().getSector().replace(" ", "_")+"\\\\"+company.getSymbol()+"\\\\model\\\",");
            jsonString = JsonManipulationUtilities.replaceFeatures(jsonString, "MODEL_SAVED_PATH", replacements);

            Process p= mlModelService.createMlModel(jsonString,company.getId());
            p.waitFor(15, TimeUnit.MINUTES);
            System.out.println("Exit status = " + p.exitValue());
            System.out.println("INAINTE DE DB");
            System.out.println(company);
        }
    }

    @PostMapping("/companies/{symbol}")
    Company addCompany(@PathVariable String symbol) throws IOException, InterruptedException {
        Company company = dataService.getCompanyData(symbol);

        String path = DataServiceImpl.getHistoricalData(symbol).trim();
        company.setHistoricDataPath(path);
        //createmodel:

        String jsonString=JsonManipulationUtilities.getJsonFromFile(company.getSector().getDefaultModelpath());
        List<String> tokens = JsonManipulationUtilities.getFeatures(jsonString, "\"PATH");
        ArrayList<String> replacements = new ArrayList<>();

        for(String token: tokens){

            if(token.equals("REPLACE"))
            {
                replacements.add("\\\"" + symbol + "\\\", ");
            }
            else {
                replacements.add("");
            }
        }

        jsonString = JsonManipulationUtilities.replaceFeatures(jsonString, "\"PATH", replacements);

        replacements = new ArrayList<String>();
        replacements.add("\\\"D:\\\\DEFAULT_SECTOR_MODELS\\\\"+company.getSector().getSector().replace(" ", "_")+"\\\\"+symbol+"\\\\tensorboard\\\",");
        jsonString = JsonManipulationUtilities.replaceFeatures(jsonString, "TENSORBOARD_PATH_CONFIG", replacements);

        replacements = new ArrayList<String>();
        replacements.add("\\\"D:\\\\DEFAULT_SECTOR_MODELS\\\\"+company.getSector().getSector().replace(" ", "_")+"\\\\"+symbol+"\\\\model\\\",");
        jsonString = JsonManipulationUtilities.replaceFeatures(jsonString, "MODEL_SAVED_PATH", replacements);

        Company c = companyservice.addCompany(company); //adauga in baza

        Process p= mlModelService.createMlModel(jsonString,c.getId());
        System.out.println("INAINTE DE DB");
        System.out.println(c);
        return c;
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

   @GetMapping("/fisier")
   String fisier() throws IOException {

       String s=JsonManipulationUtilities.getJsonFromFile("C:\\Users\\aalex\\Desktop\\alo.txt");
       return s;
   }

}






