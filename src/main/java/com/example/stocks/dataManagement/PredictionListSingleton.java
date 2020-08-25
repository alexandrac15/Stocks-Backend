package com.example.stocks.dataManagement;

import com.example.stocks.services.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;

public class PredictionListSingleton {

    @Autowired
    private PredictionService predictionService = null;


}
