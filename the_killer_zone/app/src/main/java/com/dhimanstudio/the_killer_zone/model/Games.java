package com.dhimanstudio.the_killer_zone.model;

import com.google.firebase.firestore.DocumentId;

public class Games {
    private String name;
    private String image;
    private String button;

    @DocumentId
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    ///Constructor
    public Games(String name, String image) {
        this.name = name;
        this.image = image;
        this.button = button;
        this.button = button;
    }

    public Games() {

    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
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
