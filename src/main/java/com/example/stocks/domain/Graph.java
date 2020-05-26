package com.example.stocks.domain;

import java.util.ArrayList;
import java.util.List;

public class Graph {


    private List<Pair> domain;

    public Graph(){
        this.domain=new ArrayList<>();
    }
    public Graph(List<Float> priceValues){}

    public List<Pair> getDomain() {
        return domain;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "domain=" + domain+
                '}';
    }
}
