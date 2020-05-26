package com.example.stocks.controller;

import com.example.stocks.domain.Graph;
import com.example.stocks.service.ExecutorImpl;
import com.example.stocks.service.ReaderImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import utilities.Executor;
import utilities.Reader;

import java.io.IOException;
import java.text.ParseException;

@RestController
@CrossOrigin(origins = "*")
public class GraphController {



    @GetMapping("/graph/{nDays}")
    Graph getGraph(@PathVariable int nDays) throws IOException, ParseException {
        //historical graph
        Executor e = new ExecutorImpl();
        Process p= e.execute("loadData.py "+nDays);
        Reader r=new ReaderImpl();   ///MAKE THEM STATIC OR SMTH
        Graph g= r.readHistoricData(p);

        return g;

    }
}
