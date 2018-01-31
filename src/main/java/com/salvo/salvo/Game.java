package com.salvo.salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

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
    public List<Object> getPlayers() {
        return gamePlayerSet.stream()
                .map(gamePlayer -> gamePlayer.gPToDTO())
                .collect(Collectors.toList());
    }


    //In the Map for each game, put keys and values for the game ID, creation date, and gamePlayers.
    public Map<String, Object> toDTO() {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", getId());
        dto.put("created", getCreationDate());
        dto.put("gamePlayers", getPlayers());
        return dto;
    }



}
