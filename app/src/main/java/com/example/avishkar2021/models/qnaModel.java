package com.example.avishkar2021.models;

import java.util.List;

public class qnaModel {
    private String profilePic;
    private String userName;
    private String question;
    private List<AnswersModel> replyList;

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

    public List<AnswersModel> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<AnswersModel> replyList) {
        this.replyList = replyList;
    }
}
