package com.example.stocks.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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

//    @OneToMany( mappedBy = "sector",fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL)
//    @JoinColumn(name="sector")
    @JsonManagedReference
    @OneToMany(targetEntity=Company.class , fetch = FetchType.LAZY, mappedBy = "sector")
    private List<Company> companies=new ArrayList<>();
    //private List<Company> companies = new ArrayList<>();



    public Sector() {
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

    @Override
    public String toString() {
        return "Sector{" +
                "id=" + id +
                ", sector='" + sector + '\'' +
                ", companies=" + companies +
                '}';
    }
}
