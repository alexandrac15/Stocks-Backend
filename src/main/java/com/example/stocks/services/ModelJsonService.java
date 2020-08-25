package com.example.stocks.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import static com.example.stocks.utilities.JsonManipulationUtilities.getFeatures;
import static com.example.stocks.utilities.JsonManipulationUtilities.replaceFeatures;

@Component


public class ModelJsonService {

    @Autowired
    CompanyService companyService;

    public String convertCompanySymbolsIntoPath(String MlConfigJson){

        ArrayList<String> companySymbols = getFeatures(MlConfigJson, "\"PATH");
        ArrayList<String> replacementPaths = new ArrayList<>();

        for(int symbolIdx = 0; symbolIdx < companySymbols.size(); symbolIdx++)
            replacementPaths.add("\\\""+companyService.getCompanyBySymbol(companySymbols.get(symbolIdx)).getHistoricDataPath()+"\\\",");

        return replaceFeatures(MlConfigJson, "\"PATH", replacementPaths);

    }

}
