package com.example.stocks.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "company")

public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "companyName")
    private String companyName;
    @Column(name = "symbol")
    private String symbol;

    @Column(name = "employees")
    private int employees;
    @Column(name = "industry")
    private String  industry;
    @Column(name = "website")
    private String  website;
    @Column(name = "description")
    private String description;
    @Column(name = "CEO")
    private String CEO;
    @Column(name ="historicDataPath")
    private String historicDataPath;
    @Column(name ="url")
    private String url;

    @OneToMany( fetch = FetchType.LAZY,
            mappedBy = "parentCompany")
    private List<MLModel> models;

    @ManyToOne( fetch = FetchType.EAGER)
   // @JsonBackReference
    private Sector  sector;
    @Column(name = "country")
    private String  country;

    @ManyToMany(mappedBy = "trackedCompanies")
    @JsonIgnore
    private List<User> trackingUsers = new ArrayList<>();

    public Company() {
    }

    public Company(String companyName, String symbol, int employees, String industry, String website, String description, String CEO, Sector sector, String country,String url) {
        this.companyName = companyName;
        this.symbol = symbol;
        this.employees = employees;
        this.industry = industry;
        this.website = website;
        this.description = description;
        this.CEO = CEO;
        this.sector = sector;
        this.country = country;
        this.url = url;
    }

    public Company(String companyName, String symbol, int employees, String industry, String website, String description, String CEO, String historicDataPath, Sector sector, String country,String url) {
        this.companyName = companyName;
        this.symbol = symbol;
        this.employees = employees;
        this.industry = industry;
        this.website = website;
        this.description = description;
        this.CEO = CEO;
        this.historicDataPath = historicDataPath;
        this.sector = sector;
        this.country = country;
        this.url = url;
    }
    public Company(int id, String companyName, String symbol, int employees, String industry, String website, String description, String CEO, String historicDataPath, Sector sector, String country, List<MLModel> models) {
        this.id = id;
        this.companyName = companyName;
        this.symbol = symbol;
        this.employees = employees;
        this.industry = industry;
        this.website = website;
        this.description = description;
        this.CEO = CEO;
        this.historicDataPath = historicDataPath;
        this.sector = sector;
        this.country = country;
        this.models = models;
    }
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getEmployees() {
        return employees;
    }

    public void setEmployees(int employees) {
        this.employees = employees;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCEO() {
        return CEO;
    }

    public void setCEO(String CEO) {
        this.CEO = CEO;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHistoricDataPath() {
        return historicDataPath;
    }

    public void setHistoricDataPath(String historicDataPath) {
        this.historicDataPath = historicDataPath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    @JsonIgnore
    @Transactional
    public List<MLModel> getModels() {
        return models;
    }

    public void setModels(List<MLModel> models) {
        this.models = models;
    }

    public List<User> getTrackingUsers() {
        return trackingUsers;
    }

    public void setTrackingUsers(List<User> trackingUsers) {
        this.trackingUsers = trackingUsers;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", symbol='" + symbol + '\'' +
                ", employees=" + employees +
                ", industry='" + industry + '\'' +
                ", website='" + website + '\'' +
                ", description='" + description + '\'' +
                ", CEO='" + CEO + '\'' +
                ", historicDataPath='" + historicDataPath + '\'' +
                ", logoPath='" + url + '\'' +
                ", sector=" + sector +
                ", country='" + country + '\'' +
                '}';
    }
}
