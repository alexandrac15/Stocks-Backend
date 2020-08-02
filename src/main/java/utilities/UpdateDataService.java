package utilities;

import java.io.IOException;

public interface UpdateDataService {

    int getHistoricalData(String symbol);
    int appendLastTradingDay(String symbol) throws IOException, InterruptedException;
    int updateFiles();
    int getHistoricalDataForFiles();

}
