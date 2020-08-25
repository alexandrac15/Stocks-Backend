package com.example.stocks.repositories;

import com.example.stocks.domain.Company;
import com.example.stocks.domain.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SectorRepository extends JpaRepository<Sector,Integer> {
     List<Company> findAllBySector(int idSector);
     Sector findBySector(String sector);


}
