package com.example.stocks.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sector")

public class Sector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @Column(name="sector")
    private String sector;

    @OneToMany( fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)

    //@OneToMany(targetEntity=Company.class , fetch = FetchType.LAZY, mappedBy = "sector")
    //@JsonManagedReference
    private List<Company> companies=new ArrayList<>();
    //private List<Company> companies = new ArrayList<>();

    @JsonIgnore
    private String defaultModelpath;


    public Sector() {
    }

    public Sector(String sector, List<Company> companies, String defaultModelpath) {
        this.sector = sector;
        this.companies = companies;
        this.defaultModelpath = defaultModelpath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public String getDefaultModelpath() {
        return defaultModelpath;
    }

    public void setDefaultModelpath(String defaultModelpath) {
        this.defaultModelpath = defaultModelpath;
    }

    @Override
    public String toString() {
        return "Sector{" +
                "id=" + id +
                ", sector='" + sector + '\'' +
                ", companies=" + companies +
                ", defaultModelpath='" + defaultModelpath + '\'' +
                '}';
    }
}
