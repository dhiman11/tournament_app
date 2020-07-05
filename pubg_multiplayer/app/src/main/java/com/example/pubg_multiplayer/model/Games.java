package com.example.pubg_multiplayer.model;

public class Games {
    private String name;
    private String image;


    ///Constructor
    public Games(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public Games() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
