package com.factoria.moments.models;

import java.util.List;

public class Moment {
    private String user, location, title, description, imgUrl, avatarUrl;
    private boolean isLiked;
    private Long likes;
    private Long comments;
    private Long saves;
    private Long id;

    public Moment(String user, String location, String title,  String description, String imgUrl, String avatarUrl, Long likes, Long comments, Long saves, Long id){
        this.user = user;
        this.location = location;
        this.title = title.toLowerCase();
        this.description = description;
        this.imgUrl = imgUrl.toLowerCase();
        this.avatarUrl = avatarUrl;
        this.likes = likes;
        this.comments = comments;
        this.saves = saves;
        this.id = id;
    }

    //GETTERS
    public String getTitle(){
        return this.title;
    }
    public String getImgUrl(){
        return this.imgUrl;
    }
    public String getDescription(){return this.description;}
    public Long getId(){
        return this.id;
    }

    public String getUser() {
        return user;
    }

    public String getLocation() {
        return location;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public Long getLikes() {
        return likes;
    }

    public Long getComments() {
        return comments;
    }

    public Long getSaves() {
        return saves;
    }

}
