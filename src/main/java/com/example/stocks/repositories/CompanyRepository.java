package com.example.stocks.repositories;

import com.example.stocks.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Integer> {

    List<Company> findCompaniesBySectorId(int id);
    Company findCompanyBySymbol(String symbol);


}
