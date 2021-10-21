package com.example.avishkar2021.models;

public class AddCompaniesModel {
    private String company;
    private String branches;
    private String stipend;
    private String deadline;
    private String date;
    private String lockS;
    private String verS;
    private String internS;

    public AddCompaniesModel() {
    }

    public AddCompaniesModel(String company, String branches, String deadline, String stipend ) {
        this.company = company;
        this.branches = branches;
        this.stipend = stipend;
        this.deadline = deadline;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBranches() {
        return branches;
    }

    public void setBranches(String branches) {
        this.branches = branches;
    }

    public String getStipend() {
        return stipend;
    }

    public void setStipend(String stipend) {
        this.stipend = stipend;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLockS() {
        return lockS;
    }

    public void setLockS(String lockS) {
        this.lockS = lockS;
    }

    public String getVerS() {
        return verS;
    }

    public void setVerS(String verS) {
        this.verS = verS;
    }

    public String getInternS() {
        return internS;
    }

    public void setInternS(String internS) {
        this.internS = internS;
    }
}
