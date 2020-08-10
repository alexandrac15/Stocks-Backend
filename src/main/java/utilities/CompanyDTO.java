package utilities;



import com.example.stocks.domain.Sector;

import javax.persistence.*;


public class CompanyDTO {

    private int id;

    private String companyName;

    private String symbol;


    private int employees;

    private String  industry;

    private String  website;

    private String description;

    private String CEO;


    private String  sector;

    private String  country;

    public CompanyDTO() {
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

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
                ", sector=" + sector +
                ", country='" + country + '\'' +
                '}';
    }
}
