package com.example.stocks.controller;

import com.example.stocks.domain.Company;
import com.example.stocks.domain.json_config_classes.JsonConfig;
import com.example.stocks.vechi.service.ExecutorImpl;
import com.example.stocks.vechi.service.ReaderImpl;
import org.springframework.web.bind.annotation.*;
import utilities.Reader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")

public class ModelController {

    @PostMapping("/create")
    @CrossOrigin(origins = "*")
    String createMlModel(@RequestBody String MlConfigJson) throws IOException, InterruptedException {
        System.out.println(
                MlConfigJson
        );
        Process p=ExecutorImpl.execute("model_creation.py \""+MlConfigJson+"\"");
        InputStream err = p.getErrorStream();
        InputStreamReader isr = new InputStreamReader(err);

        Reader r=new ReaderImpl();
        String s=r.readConsoleOutput(p);

        System  .out.println(s);
        System.out.print(isr.read());
        return s;
    }

}
