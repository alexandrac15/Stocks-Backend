package com.example.stocks.services;

import com.example.stocks.domain.Company;
import com.example.stocks.domain.Sector;
import com.example.stocks.repositories.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
@Component
public class SectorService {

    @Autowired
    SectorRepository sectorRepository;

    public List<Sector> getSectors(){

        return sectorRepository.findAll();
    }

    public Sector getSectorByName(String sector){
        return sectorRepository.findBySector(sector);
    }

}
