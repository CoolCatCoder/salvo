package com.salvo.salvo;

import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.Date;

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

}
