package com.example.tinderclone.Matches;

public class MatchesObject {
    private String userId;
    private String userName;
    private String profImgUrl;

    public String getProfImgUrl() {
        return profImgUrl;
    }

    public void setProfImgUrl(String profImgUrl) {
        this.profImgUrl = profImgUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public MatchesObject(String  userId, String userName, String profImgUrl) {
        this.userId = userId;
        this.userName = userName;
        this.profImgUrl = profImgUrl;
    }

    public String getuserName() {
        return userName;
    }

    public void setuserName(String userName) {
        this.userName = userName;
    }
}
