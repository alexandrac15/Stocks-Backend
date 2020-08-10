package com.example.stocks.ML;

import com.example.stocks.domain.Graph;

public interface PredictionService {

    Graph getPrediction(String symbol, String Sector , int days);
}
