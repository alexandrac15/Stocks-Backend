package com.example.stocks.services;

import com.example.stocks.domain.Graph;
import org.springframework.stereotype.Component;
import sun.awt.Mutex;

import java.util.HashMap;
import java.util.Map;
@Component
public class PredictionService {

    Map<Integer,Graph> predictions=new HashMap<Integer,Graph>();
    Mutex mutex = new Mutex();

    public PredictionService() {
    }

    public void addPrediction(Integer idCompany,Graph prediction){
        mutex.lock();
        predictions.put(idCompany, prediction);
        mutex.unlock();
    }
     public Graph findPredictionByCompanyID(Integer companyId){
         mutex.lock();
         Graph returnedGraph = predictions.getOrDefault(companyId, null);
         mutex.unlock();
         return returnedGraph;

     }

}
