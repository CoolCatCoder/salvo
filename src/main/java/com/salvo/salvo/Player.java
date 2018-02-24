package com.salvo.salvo;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity   //tells Spring to create a player table for this class.
public class Player {           // Warning: You must define a default (no-argument) constructor for any entity class.

    @Id    // says that the id instance variable holds the database key for this class.
    @GeneratedValue(strategy = GenerationType.AUTO)  // tells JPA to get the Id from the DBMS.
    private long id;
    private String userName;
    private String passWord;

    @OneToMany(mappedBy="player_playing", fetch = FetchType.EAGER)
    Set<GamePlayer> gamePlayerSet;

    @OneToMany(mappedBy="player", fetch = FetchType.EAGER)
    Set<Score> scores;

    public Player (){ }

    public Player (String user, String password){
        userName = user;
        passWord = password;
    }

    public long getId (){
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getUserName(){
        return userName;
    }

    public void setUserName(String newUserName){
        this.userName = newUserName;
    }

    public String getPassWord(){
        return passWord;
    }

    public void setPassWord(String newPassword){
        this.passWord = newPassword;
    }

    public void addGamePlayer(GamePlayer gamePlayer){
        gamePlayer.setPlayer_playing(this);
        gamePlayerSet.add(gamePlayer);
    }

    public Set<GamePlayer> getGamePlayerSet() {
        return gamePlayerSet;
    }

    public void setGamePlayerSet(Set<GamePlayer> gamePlayerSet) {
        this.gamePlayerSet = gamePlayerSet;
    }

    @JsonIgnore
    public List<Game> getGames() {
        return gamePlayerSet.stream()
                .map(sub -> sub.getGame())
                .collect(toList());
    }

    public Set<Score> getScoreSet() {
        return scores;
    }

    public void setScoreSet(Set<Score> scoreSet) {
        this.scores = scoreSet;
    }

    public Optional<Score> getScore(Game thisGame){
        return this.getScoreSet().stream()
                .filter(a -> a.getGame().equals(thisGame))
                .findFirst();
    }

}