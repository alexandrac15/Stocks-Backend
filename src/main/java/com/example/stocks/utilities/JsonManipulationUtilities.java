package com.example.stocks.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class JsonManipulationUtilities {

    public static String extractFeatureFromJson(String Json, String Feature){
        String aux = Json;
        aux = aux.split(Feature)[1];
        aux = aux.split(",")[0];
        return aux.split("\"")[2];
    }

    public static String replaceFeatures(String Json, String Feature, ArrayList<String> Replacements){

        String finalJson = "";

        String[] features = Json.split(Feature);

        finalJson += features[0];
        for(int featureIndex = 1; featureIndex < features.length; featureIndex++)
        {
            finalJson += Feature;
            String feature = features[featureIndex];
            if(featureIndex - 1 < Replacements.size() && (!Replacements.get(featureIndex - 1).equals(""))) {
                String[] tokens = feature.split(" ");
                tokens[1] = Replacements.get(featureIndex - 1);
                feature = String.join("", tokens);
            }
            finalJson += feature;
        }
        return finalJson;
    }

    public static ArrayList<String> getFeatures(String Json, String Feature){

        ArrayList<String> extractedFeatures = new ArrayList<>();
        String[] features = Json.split(Feature);

        for(int featureIndex = 1; featureIndex < features.length; featureIndex++)
        {
            String feature = features[featureIndex];
            String[] tokens = feature.split(" ");
            extractedFeatures.add(tokens[1].replace("\"", "").replace("\\", "").replace(",", ""));
        }

        return extractedFeatures;
    }

    public static ArrayList<Float> extractPredictionsFromPythonOutput(String Json)
    {
        ArrayList<Float> values = new ArrayList<>();

        String aux = Json.replace("[", "").replace("]", "").replace("\n", "").trim().replaceAll(" +", " ");
        String[] stringValues = aux.split(" ");

        for(String v: stringValues)
        {
            values.add(Float.parseFloat(v));
        }

        return values;

    }
    public static String getJsonFromFile( String jsonPath) throws IOException {
        String s;
        File f=new File(jsonPath);
        Scanner sc = new Scanner(f);

        // we just need to use \\Z as delimiter
        sc.useDelimiter("//Z");
        s=sc.next().replaceAll("\r?\n", " ").trim();
        return s;

    }
}
