package com.example.pubg_multiplayer.model;

public class Tournament_detail {

    private  Integer per_kill;
    private  Integer participants;
    private  Integer winning_price;

    public Tournament_detail(Integer winning_price) {
        this.winning_price = winning_price;
    }

    public Integer getWinning_price() {
        return winning_price;
    }

    public void setWinning_price(Integer winning_price) {
        this.winning_price = winning_price;
    }

    public Tournament_detail() {
    }

    public Tournament_detail(Integer per_kill, Integer participants) {
        this.per_kill = per_kill;
        this.participants = participants;
    }

    public Integer getPer_kill() {
        return per_kill;
    }

    public void setPer_kill(Integer per_kill) {
        this.per_kill = per_kill;
    }

    public Integer getParticipants() {
        return participants;
    }

    public void setParticipants(Integer participants) {
        this.participants = participants;
    }
}
