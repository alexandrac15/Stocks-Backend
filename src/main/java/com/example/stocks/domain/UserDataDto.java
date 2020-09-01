package com.example.stocks.domain;

import java.util.ArrayList;

public class UserDataDto {

    private ArrayList<Integer> trackedCompanies;
    private String role;

    public UserDataDto(){
        trackedCompanies = new ArrayList<>();
    }

    public UserDataDto(User u){
        trackedCompanies = new ArrayList<>();
        for(Company c : u.getTrackedCompanies()){
            trackedCompanies.add(c.getId());
        }
        role = u.getRole();
    }

    public UserDataDto(ArrayList<Integer> trackedCompanies, String role) {
        this.trackedCompanies = trackedCompanies;
        this.role = role;
    }

    public ArrayList<Integer> getTrackedCompanies() {
        return trackedCompanies;
    }

    public void setTrackedCompanies(ArrayList<Integer> trackedCompanies) {
        this.trackedCompanies = trackedCompanies;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
