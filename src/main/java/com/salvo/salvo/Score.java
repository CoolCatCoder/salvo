package com.salvo.salvo;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Date finishDate = new Date();
    private double score;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "game_id")
    private Game game;

    public Score() {}

    public Score(GamePlayer gamePlayer, double score, Date date) {
        this.player = gamePlayer.getPlayer_playing();
        this.game = gamePlayer.getGame();
        this.score = score;
        this.finishDate = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date newFinishDate) {
        this.finishDate = newFinishDate;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double newScore) {
        this.score = newScore;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
