package com.example.pawhand2.Models;


public class PostModel {
    String title;
    String description;
    String picture;
    String userID;
    String userpicture;
    long timeStamp;
    String postkey;
    String contact;
    String address;

    public PostModel()
    {

    }

    public String getContact() {
        return contact;
    }

    public PostModel(String title, String description, String picture, String userID, String userpicture, String contact,String address) {
        this.title = title;
        this.description = description;
        this.picture = picture;
        this.userID = userID;
        this.userpicture = userpicture;
        this.timeStamp = System.currentTimeMillis();
        this.contact=contact;
        this.address=address;

    }

    public String getPostkey() {
        return postkey;
    }

    public void setPostkey(String postkey) {
        this.postkey = postkey;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public String getPicture() {
        return picture;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserpicture() {
        return userpicture;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
