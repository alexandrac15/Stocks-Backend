package com.example.stocks.dataManagement;

import java.io.IOException;

public interface DataService {

    int getHistoricalData(String symbol);
    int appendLastTradingDay(String symbol) throws IOException, InterruptedException;
    int updateFiles();
    int getHistoricalDataForFiles();

}
