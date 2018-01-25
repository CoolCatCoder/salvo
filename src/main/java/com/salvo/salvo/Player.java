package com.salvo.salvo;


import javax.persistence.*;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity   //tells Spring to create a player table for this class.
public class Player {           // Warning: You must define a default (no-argument) constructor for any entity class.

    @Id    // says that the id instance variable holds the database key for this class.
    @GeneratedValue(strategy = GenerationType.AUTO)  // tells JPA to get the Id from the DBMS.
    private long id;
    private String userName;
    //    private String firstName;
    //    private String lastName;

    @OneToMany(mappedBy="player_playing", fetch = FetchType.EAGER)
    Set<GamePlayer> gamePlayerSet;

    public Player (){ }

    public Player (String user){
        userName = user;
        //        firstName = first;
//        lastName = last;
    }


    public long getId (){
        return id;
    }

//  public String getFirstName(){
//        return firstName;
//  }
//
//  public void setFirstName(String newFirstName){
//        this.firstName = newFirstName;
//  }
//
//  public String getLastName(){
//        return lastName;
//  }

//  public void setLastName (String newLastName){
//        this.lastName = newLastName;
//  }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String newUserName){
        this.userName = newUserName;
    }

    public void addGamePlayer(GamePlayer gamePlayer){
        gamePlayer.setPlayer_playing(this);
        gamePlayerSet.add(gamePlayer);
    }

    public List<Game> getGames() {
        return gamePlayerSet.stream().map(sub -> sub.getGame()).collect(toList());
    }

//    public String toString(){
//        return (firstName + " " + lastName);
//    }


}