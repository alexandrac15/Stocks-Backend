package com.example.stocks.utilities;

import com.example.stocks.domain.MLModel;

import java.util.List;

 public class CompanyUtils {

    public static MLModel getDefaultModel(List<MLModel> modelList){
        for(MLModel model : modelList)
            if(model.getDefault())
                return model;

        return null;
    }
}
