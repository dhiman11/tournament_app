package com.example.pubg_multiplayer.model;

public class Profile {
    private String name;
    private String username;
    private String email;
    private String phone;
    private String pubgid;

    public Profile(String name, String username, String email, String phone, String pubgid) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.pubgid = pubgid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPubgid() {
        return pubgid;
    }

    public void setPubgid(String pubgid) {
        this.pubgid = pubgid;
    }
}
