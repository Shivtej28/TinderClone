package com.example.tinderclone.Cards;

public class Cards {
    private String userId;
    private String name;
    private String profImgUrl;


    public Cards(String userId, String name, String profImgUrl) {
        this.userId = userId;
        this.name = name;
        this.profImgUrl = profImgUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getProfImgUrl() {
        return profImgUrl;
    }

    public void setProfImgUrl(String profImgUrl) {
        this.profImgUrl = profImgUrl;
    }


}
