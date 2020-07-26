package com.dhimanstudio.the_killer_zone.model;

public class Tournament_joined {

    private String entry_fee_paid;
    private Boolean joined;
    private Boolean payment_status;
    private String tournament_id;
    private String user_id;


    public Tournament_joined() {
    }

    public Tournament_joined(String entry_fee_paid, Boolean joined, Boolean payment_status, String tournament_id, String user_id) {
        this.entry_fee_paid = entry_fee_paid;
        this.joined = joined;
        this.payment_status = payment_status;
        this.tournament_id = tournament_id;
        this.user_id = user_id;
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
