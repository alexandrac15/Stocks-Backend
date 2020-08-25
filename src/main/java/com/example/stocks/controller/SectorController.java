package com.example.stocks.controller;

import com.example.stocks.domain.Sector;
import com.example.stocks.repositories.SectorRepository;
import com.example.stocks.services.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@CrossOrigin(origins = "*")
public class SectorController {

    @Autowired
    SectorService sectorService;

    @GetMapping("/sectors")
    List<Sector> getSectors() {
        return sectorService.getSectors();

    }
}
