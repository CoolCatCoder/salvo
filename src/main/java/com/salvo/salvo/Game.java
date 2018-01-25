package com.salvo.salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity
public class Game {

    @Id    // says that the id instance variable holds the database key for this class.
    @GeneratedValue(strategy = GenerationType.AUTO)  // tells JPA to get the Id from the DBMS.
    private long id;

    private Date creationDate = new Date();

    @OneToMany(mappedBy= "game", fetch=FetchType.EAGER) //Why "game?"
            Set<GamePlayer> gamePlayerSet;

    public Game(){ }

    public Game(Date date){
        creationDate = date;
    }

    public long getId (){
        return id;
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

    @JsonIgnore
    public List<Player> getPlayers() {
        return gamePlayerSet.stream().map(sub -> sub.getPlayer_playing()).collect(toList());
    }





}
