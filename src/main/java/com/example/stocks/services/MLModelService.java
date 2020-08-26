package com.example.stocks.services;

import com.example.stocks.domain.Company;
import com.example.stocks.domain.MLModel;
import com.example.stocks.repositories.MLModelRepository;
import com.example.stocks.vechi.service.ExecutorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

import static com.example.stocks.utilities.JsonManipulationUtilities.extractFeatureFromJson;

@Component
public class MLModelService{

    @Autowired
    MLModelRepository mlModelRepository;
    @Autowired
    CompanyService companyService;
    @Autowired
    ModelJsonService modelJsonService;
    public Process createMlModel (String MlConfigJson,  int CompanyId) throws IOException, InterruptedException {

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

        return p;

    }
}
