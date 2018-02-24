package com.salvo.salvo;


import javax.persistence.*;
import java.util.*;


@Entity
public class Game {

    @Id    // says that the id instance variable holds the database key for this class.
    @GeneratedValue(strategy = GenerationType.AUTO)  // tells JPA to get the Id from the DBMS.
    private long id;
    private Date creationDate = new Date();

    @OneToMany(mappedBy = "game", fetch=FetchType.EAGER) //Why "game?"
            Set<GamePlayer> gamePlayerSet;

    @OneToMany(mappedBy = "score", fetch=FetchType.EAGER)
            Set<Score> scoreSet;

    public Game(){ }

    public Game(Date date){
        creationDate = date;
    }

    public long getId (){
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date newDate){
       creationDate = newDate;
    }

    public void addGamePlayer(GamePlayer gamePlayer){
        gamePlayer.setGame(this);
        gamePlayerSet.add(gamePlayer);
    }

    public void addScore(Score score) {
        score.setGame(this);
        scoreSet.add(score);
    }

    public Set<GamePlayer> getGamePlayerSet() {
        return gamePlayerSet;
    }

    public void setGamePlayerSet(Set<GamePlayer> gamePlayerSet) {
        this.gamePlayerSet = gamePlayerSet;
    }

    public Set<Score> getScoreSet(){
        return scoreSet;
    }

    public void setScoreSet(Set<Score> scoreSet) {
        this.scoreSet = scoreSet;
    }
}
