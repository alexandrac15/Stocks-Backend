package com.example.stocks.controller;

import com.example.stocks.domain.Company;
import com.example.stocks.domain.MLModel;
import com.example.stocks.domain.json_config_classes.JsonConfig;
import com.example.stocks.repositories.MLModelRepository;
import com.example.stocks.services.CompanyService;
import com.example.stocks.vechi.service.ExecutorImpl;
import com.example.stocks.vechi.service.ReaderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utilities.Reader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")

public class ModelController {
    @Autowired
    CompanyService companyService;
    @Autowired
    MLModelRepository mlModelRepository;
    String extractFeatureFromJson(String Json, String Feature){
        String aux = Json;
        aux = aux.split(Feature)[1];
        aux = aux.split(",")[0];
        return aux.split("\"")[2];
    }

    String replacePath(String Json, String Feature, ArrayList<String> Replacements){

        String finalJson = "";

        String[] features = Json.split(Feature);

        finalJson += features[0];
        for(int featureIndex = 1; featureIndex < features.length - 1; featureIndex++)
        {
            finalJson += Feature;
            String feature = features[featureIndex];
            feature.split("");



            finalJson += Replacements.get(featureIndex);

        }
        return "ok";
    }

    @PostMapping("/create")
    @CrossOrigin(origins = "*")
    String createMlModel(@RequestBody String MlConfigJson/*, int cmpid*/) throws IOException, InterruptedException {

        int company_id = 1;

        System.out.println(
                MlConfigJson
        );

        String path = extractFeatureFromJson(MlConfigJson, "MODEL_SAVED_PATH");
        Company company = companyService.getCompanyById(company_id);

        MLModel mlModel = new MLModel(path, false, company);
        mlModelRepository.save(mlModel);


        Process p = ExecutorImpl.execute("model_creation.py \""+MlConfigJson+"\"");


        return "ok";
    }

    @GetMapping("/getPrediction/{CompanyId}")
    @CrossOrigin(origins = "*")
    String getModelPrediction(@PathVariable int CompanyId) throws IOException {

        //companyService.getCompanyById(CompanyId);

        System.out.println(
                "Start predicting"
        );

        Process p=ExecutorImpl.execute("model_predict.py "+"D:\\EXPERIMENTS\\TEST_ENDPOINT\\model_NFLX-close-10_3_volume_1\\");
        Reader r=new ReaderImpl();
        String s=r.readConsoleOutput(p);
        System.out.println(s);

        return s;

    }
}
