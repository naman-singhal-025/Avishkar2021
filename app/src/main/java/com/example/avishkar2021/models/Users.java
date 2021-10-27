package com.example.avishkar2021.models;

import java.net.URI;
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
    private String status;
    private String lockStatus;
    private String verStatus;
    private String branch;
    private String phone;
    private String feedback_desc;
    private URI pImage;



    private String reg_no;

    public Users(String profilePic, String userName, String mail, String phone, String branch) {
        this.profilePic = profilePic;
        this.userName = userName;
        this.mail = mail;
        this.phone = phone;
        this.branch = branch;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(String lockStatus) {
        this.lockStatus = lockStatus;
    }

    public String getVerStatus() {
        return verStatus;
    }

    public void setVerStatus(String verStatus) {
        this.verStatus = verStatus;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public URI getpImage() {
        return pImage;
    }

    public void setpImage(URI pImage) {
        this.pImage = pImage;
    }
    public String getReg_no() {
        return reg_no;
    }

    public void setReg_no(String reg_no) {
        this.reg_no = reg_no;
    }

    public String getFeedback_desc() {
        return feedback_desc;
    }

    public void setFeedback_desc(String feedback_desc) {
        this.feedback_desc = feedback_desc;
    }
}
