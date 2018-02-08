package com.salvo.salvo;

import javax.persistence.*;
import java.util.*;

@Entity
public class GamePlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Date joinDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "player_playing_id")
    private Player player_playing;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    private Game game;

    @OneToMany(mappedBy="gamePlayer", fetch = FetchType.EAGER)
    private Set<Ship> shipSet= new HashSet<>();

    @OneToMany(mappedBy="gamePlayer", fetch = FetchType.EAGER)
    private Set<Salvo> salvoSet= new HashSet<>();

    public GamePlayer() {}

    public GamePlayer(Game game, Player player_playing) {
        this.game = game;
        this.player_playing = player_playing;
        joinDate = new Date();
    }

    public long getId (){
        return id;
    }

    public Player getPlayer_playing(){
        return player_playing;
    }

    public void setPlayer_playing(Player newPlayer_playing){
        this.player_playing= newPlayer_playing;
    }

    public Game getGame(){
        return game;
    }

    public void setGame(Game newGame){
        this.game = newGame;
    }


    public void addShip(Ship ship){
        ship.setGamePlayer(this);
        shipSet.add(ship);
    }

    public Set<Ship> getShipSet() {
        return shipSet;
    }

    public void setShipSet(Set<Ship> shipSet) {
        this.shipSet = shipSet;
    }

    public void addSalvo(Salvo salvo){
        salvo.setGamePlayer(this);
        salvoSet.add(salvo);
    }

    public Set<Salvo> getSalvoSet() {
        return salvoSet;
    }

    public void setSalvoSet(Set<Salvo> salvoSet) {
        this.salvoSet = salvoSet;
    }
}
