package com.example.avishkar2021.models;

import java.util.List;

public class Users {
    private String profilePic;
    private String userName;
    private String mail;
    private String userId;
    private String password;
    private String resume;
    private List<String> personal;
    private List<String> academic;
    private ProjectInternModel pimodel;

    public Users(String profilePic, String userName, String mail, String userId, String password) {
        this.profilePic = profilePic;
        this.userName = userName;
        this.mail = mail;
        this.userId = userId;
        this.password = password;
    }

    public Users() {
    }

    //Sign Up constructor
    public Users(String userName, String mail, String password) {
        this.userName = userName;
        this.mail = mail;
        this.password = password;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public List<String> getPersonal() {
        return personal;
    }

    public void setPersonal(List<String> personal) {
        this.personal = personal;
    }

    public List<String> getAcademic() {
        return academic;
    }

    public void setAcademic(List<String> academic) {
        this.academic = academic;
    }

    public ProjectInternModel getPimodel() {
        return pimodel;
    }

    public void setPimodel(ProjectInternModel pimodel) {
        this.pimodel = pimodel;
    }
}
