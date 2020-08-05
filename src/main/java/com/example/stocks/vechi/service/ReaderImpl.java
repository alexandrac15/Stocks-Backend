package com.example.stocks.vechi.service;

import com.example.stocks.vechi.domain.Graph;
import com.example.stocks.vechi.domain.Pair;
import utilities.Reader;

import java.io.*;
import java.text.ParseException;
import java.util.List;

public class ReaderImpl implements Reader {


    @Override
    public List<Float> readPrediction(Process p) {
        return null;
    }

    @Override
    public Graph readHistoricData(Process p) throws IOException, ParseException {
        Graph g = new Graph();
        String out="";
        BufferedReader stdError=new BufferedReader(new InputStreamReader(p.getErrorStream()));
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));


        String s;
        String s1;
        String[] arr;
        while ((s = stdInput.readLine()) != null) {
            s1=s.substring(1);
              arr=s1.split("  ");
              g.getSeries().add(new Pair(arr[0],arr[1]));

        }

        return g;
    }

    @Override
    public String readConsoleOutput(Process p) throws IOException {
        String out="";
        BufferedReader stdError=new BufferedReader(new InputStreamReader(p.getErrorStream()));
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));


       String s;
        while ((s = stdInput.readLine()) != null) {
            out=out+" "+s;

        }
        return out;
    }
}
