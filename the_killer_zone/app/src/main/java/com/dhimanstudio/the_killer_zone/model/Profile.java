package com.dhimanstudio.the_killer_zone.model;

public class Profile {
    private String name;
    private String username;
    private String email;
    private String phone;
    private String pubgid;
    private Integer wallet_amount;



    public Profile(String name, String username, String email, String phone, String pubgid,Integer wallet_amount) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.pubgid = pubgid;
        this.wallet_amount = wallet_amount;
    }

    public Profile() {
    }

    public Integer getWallet_amount() {
        return wallet_amount;
    }

    public void setWallet_amount(Integer wallet_amount) {
        this.wallet_amount = wallet_amount;
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
