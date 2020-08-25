package com.example.stocks.utilities;

import com.example.stocks.domain.Graph;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface Reader {

     List<Float> readPrediction(Process p);
     Graph readHistoricData(Process p) throws IOException, ParseException;
     String readConsoleOutput(Process p) throws IOException;
}
