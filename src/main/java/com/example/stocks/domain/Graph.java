package com.example.stocks.domain;

import java.util.ArrayList;
import java.util.List;

public class Graph {


    private List<Pair> series;

    public Graph(){
        this.series=new ArrayList<>();
    }
    public Graph(List<Float> priceValues){}

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
