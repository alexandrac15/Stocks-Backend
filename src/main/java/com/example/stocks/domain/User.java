package com.example.stocks.domain;

import org.springframework.beans.factory.support.ManagedSet;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    int id;
    @Column(name="googleUserId")
    private String googleUserId;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;

    @ManyToMany(
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "tracked_companies",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "id") } )
    private List<Company> trackedCompanies = new ArrayList<>();



    public User() {
    }

    public User(String googleUserId, String name, String email) {
        this.googleUserId = googleUserId;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoogleUserId() {
        return googleUserId;
    }

    public void setGoogleUserId(String googleUserId) {
        this.googleUserId = googleUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Company> getTrackedCompanies() {
        return trackedCompanies;
    }

    public void setTrackedCompanies(List<Company> trackedCompanies) {
        this.trackedCompanies = trackedCompanies;
    }

    public void addTrackedCompany(Company trackedCompany){
        this.trackedCompanies.add(trackedCompany);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", googleUserId='" + googleUserId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
