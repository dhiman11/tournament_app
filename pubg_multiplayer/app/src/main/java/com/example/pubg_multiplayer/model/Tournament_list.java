package com.example.pubg_multiplayer.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;

public class Tournament_list {

    private String name;
    private String entry_fee;
    private String game;
    private String game_id;
    private String game_map;
    private String image;
    private Integer participants;
    private Integer per_kill;
    private String room_detail;
    private String server;
    private String team;
    private Timestamp tournament_start_date;
    @DocumentId
    private String uid;

    public Tournament_list() {
    }

    public Tournament_list(String name, String entry_fee, String game, String game_id, String game_map, String image, Integer participants, Integer per_kill, String room_detail, String server, String team, Timestamp tournament_start_date, String uid) {
        this.name = name;
        this.entry_fee = entry_fee;
        this.game = game;
        this.game_id = game_id;
        this.game_map = game_map;
        this.image = image;
        this.participants = participants;
        this.per_kill = per_kill;
        this.room_detail = room_detail;
        this.server = server;
        this.team = team;
        this.tournament_start_date = tournament_start_date;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEntry_fee() {
        return entry_fee;
    }

    public void setEntry_fee(String entry_fee) {
        this.entry_fee = entry_fee;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public String getGame_map() {
        return game_map;
    }

    public void setGame_map(String game_map) {
        this.game_map = game_map;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getParticipants() {
        return participants;
    }

    public void setParticipants(Integer participants) {
        this.participants = participants;
    }

    public Integer getPer_kill() {
        return per_kill;
    }

    public void setPer_kill(Integer per_kill) {
        this.per_kill = per_kill;
    }

    public String getRoom_detail() {
        return room_detail;
    }

    public void setRoom_detail(String room_detail) {
        this.room_detail = room_detail;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public Timestamp getTournament_start_date() {
        return tournament_start_date;
    }

    public void setTournament_start_date(Timestamp tournament_start_date) {
        this.tournament_start_date = tournament_start_date;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
