package com.example.avishkar2021.models;

//model to encapsulate variables and corresponding methods

public class ProjectInternModel {
    private String Title;
    private String PDescription;
    private String Organisation;
    private String IDescription;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPDescription() {
        return PDescription;
    }

    public void setPDescription(String PDescription) {
        this.PDescription = PDescription;
    }

    public String getOrganisation() {
        return Organisation;
    }

    public void setOrganisation(String organisation) {
        Organisation = organisation;
    }

    public String getIDescription() {
        return IDescription;
    }

    public void setIDescription(String IDescription) {
        this.IDescription = IDescription;
    }
}
