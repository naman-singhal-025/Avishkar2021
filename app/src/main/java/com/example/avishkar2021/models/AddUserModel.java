package com.example.avishkar2021.models;

//model to encapsulate variables and corresponding methods

public class AddUserModel {
    private String editTextMail;
    private String editTextPassword;
    private String numbering;
    private String reg_no;

    public String getEditTextMail() {
        return editTextMail;
    }

    public void setEditTextMail(String editTextMail) {
        this.editTextMail = editTextMail;
    }

    public String getEditTextPassword() {
        return editTextPassword;
    }

    public void setEditTextPassword(String editTextPassword) {
        this.editTextPassword = editTextPassword;
    }

    public String getNumbering() {
        return numbering;
    }

    public void setNumbering(String numbering) {
        this.numbering = numbering;
    }

    public String getReg_no() {
        return reg_no;
    }

    public void setReg_no(String reg_no) {
        this.reg_no = reg_no;
    }
}
