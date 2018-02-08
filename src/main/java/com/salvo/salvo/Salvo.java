package com.salvo.salvo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Salvo {

    @Id
    @GeneratedValue
    private long id;
    private int turn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "gamePlayer_id")
    private GamePlayer gamePlayer;

    @ElementCollection
    @Column(name="salvo_locations")
    private List<String> salvoLocations = new ArrayList<>();

    public Salvo() {}

    public Salvo(GamePlayer gamePlayer, int turn, List<String> salvoLocations) {
    this.gamePlayer = gamePlayer;
    this.turn = turn;
    this.salvoLocations = salvoLocations;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public List<String> getSalvoLocations() {
        return salvoLocations;
    }

    public void setSalvoLocations(List<String> salvoLocations) {
        this.salvoLocations = salvoLocations;
    }
}
