package com.example.avishkar2021.models;

import java.util.ArrayList;

public class qnaModel {
    private String profilePic;
    private String userName;
    private String question;
    private String question_id;
    private ArrayList<AnswersModel> replyList;

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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<AnswersModel> getReplyList() {
        return replyList;
    }

    public void setReplyList(ArrayList<AnswersModel> replyList) {
        this.replyList = replyList;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }
}
