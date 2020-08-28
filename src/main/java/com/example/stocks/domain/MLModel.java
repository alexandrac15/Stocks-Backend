package com.example.stocks.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Table(name = "MLModel")
public class MLModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String modelPath;
    private Boolean isDefault;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_company_id")
    private Company parentCompany;

    public MLModel(){}

    public MLModel(String modelPath, Boolean isDefault, Company parentCompany) {
        this.modelPath = modelPath;
        this.isDefault = isDefault;
        this.parentCompany = parentCompany;
    }

    public MLModel(int id, String modelPath, Boolean isDefault, Company parentCompany) {
        this.id = id;
        this.modelPath = modelPath;
        this.isDefault = isDefault;
        this.parentCompany = parentCompany;
    }

    public int getId() {
        return id;
    }

    public String getModelPath() {
        return modelPath;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public Company getParentCompany() {
        return parentCompany;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setModelPath(String modelPath) {
        this.modelPath = modelPath;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public void setParentCompany(Company parentCompany) {
        this.parentCompany = parentCompany;
    }

}
