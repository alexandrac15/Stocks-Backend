package com.example.stocks.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Graph {


    private List<Pair> series;

    public Graph(){
        this.series=new ArrayList<>();
    }
    public Graph(List<Float> priceValues) throws ParseException {
        series=new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat();
        sdf.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        Date currentDate = calendar.getTime();
        String currentDateString=sdf.format(currentDate);

        for(Float f : priceValues){
            Pair p=new Pair(currentDateString,f);
            calendar.setTime(currentDate);
            calendar.add(Calendar.DATE, 1);
            currentDate=calendar.getTime();
            currentDateString=sdf.format(currentDate);
            series.add(p);
        }


    }
    public Graph(Graph graph1,Graph graph2 ){
        this.series=new ArrayList<>();
        this.series.addAll(graph1.getSeries());
        this.series.addAll(graph2.getSeries());

    }
    public List<Pair> getSeries() {
        return series;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "series=" + series+
                '}';//hey
    }
}
