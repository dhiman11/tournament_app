package com.dhimanstudio.the_killer_zone.model;

public class Tournament_joined {

    private String entry_fee_paid;
    private Boolean joined;
    private Boolean payment_status;
    private String tournament_id;
    private String user_id;
    private String user_name;
    private String image;
    private String name;
    private String match_date;



    public Tournament_joined(String image, String name,String match_date) {
        this.image = image;
        this.name = name;
        this.match_date = match_date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tournament_joined(String user_name) {
        this.user_name = user_name;
    }

    public Tournament_joined() {
    }

    public Tournament_joined(String entry_fee_paid, Boolean joined, Boolean payment_status, String tournament_id, String user_id) {
        this.entry_fee_paid = entry_fee_paid;
        this.joined = joined;
        this.payment_status = payment_status;
        this.tournament_id = tournament_id;
        this.user_id = user_id;
    }


    public String getMatch_date() {
        return match_date;
    }

    public void setMatch_date(String match_date) {
        this.match_date = match_date;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public String getEntry_fee_paid() {
        return entry_fee_paid;
    }

    public void setEntry_fee_paid(String entry_fee_paid) {
        this.entry_fee_paid = entry_fee_paid;
    }

    public Boolean getJoined() {
        return joined;
    }

    public void setJoined(Boolean joined) {
        this.joined = joined;
    }

    public Boolean getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(Boolean payment_status) {
        this.payment_status = payment_status;
    }

    public String getTournament_id() {
        return tournament_id;
    }

    public void setTournament_id(String tournament_id) {
        this.tournament_id = tournament_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
