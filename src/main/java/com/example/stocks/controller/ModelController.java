package com.example.stocks.controller;

import com.example.stocks.domain.Company;
import com.example.stocks.domain.MLModel;
import com.example.stocks.domain.MLModel;
import com.example.stocks.repositories.MLModelRepository;
import com.example.stocks.services.CompanyService;
import com.example.stocks.services.ModelJsonService;
import com.example.stocks.vechi.service.ExecutorImpl;
import com.example.stocks.vechi.service.ReaderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.stocks.utilities.Reader;

import java.io.IOException;
import java.util.List;

import static com.example.stocks.utilities.JsonManipulationUtilities.*;


@RestController
@CrossOrigin(origins = "*")
public class ModelController {

    @Autowired
    CompanyService companyService;

    @Autowired
    MLModelRepository mlModelRepository;

    @Autowired
    ModelJsonService modelJsonService;

    @PostMapping("/create/model/{CompanyId}")
    @CrossOrigin(origins = "*")
    String createMlModel(@RequestBody String MlConfigJson, @PathVariable int CompanyId) throws IOException, InterruptedException {

        System.out.println(
                MlConfigJson
        );

        String path = extractFeatureFromJson(MlConfigJson, "MODEL_SAVED_PATH");
        Company company = companyService.getCompanyById(CompanyId);

        MLModel mlModel = new MLModel(path, false, company);
        mlModelRepository.save(mlModel);

        String finalJson = modelJsonService.convertCompanySymbolsIntoPath(MlConfigJson);

        System.out.println("Converted JSON");
        System.out.println(finalJson);

        Process p = ExecutorImpl.execute("model_creation.py \""+ finalJson +"\"");

        return "ok";
    }

    private MLModel getDefaultModel(List<MLModel> modelList){
        for(MLModel model : modelList)
            if(model.getDefault())
                return model;

        return null;
    }

    @GetMapping("/getPrediction/{CompanyId}")
    @CrossOrigin(origins = "*")
    String getModelPrediction(@PathVariable int CompanyId) throws IOException {

        Company company = companyService.getCompanyById(CompanyId);
        List<MLModel> list = company.getModels();
        MLModel defaultModel = getDefaultModel(company.getModels());

        if(defaultModel == null)
            return "Could not find default model for company " + CompanyId;

        System.out.println("Start predicting");

        Process p=ExecutorImpl.execute("model_predict.py "+ defaultModel.getModelPath());
        Reader r=new ReaderImpl();
        String s=r.readConsoleOutput(p);
        System.out.println(s);

        return s;

    }
}
