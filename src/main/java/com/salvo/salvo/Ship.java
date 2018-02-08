package com.salvo.salvo;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
    public class Ship {

        @Id    // says that the id instance variable holds the database key for this class.
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;
        private String type;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name= "gamePlayer_id")
        private GamePlayer gamePlayer;

    //In that case, we can annotate the Ship class to say that a ship has a list of location strings by just adding these lines of code:
    @ElementCollection
    @Column(name="ship_locations")
    private List<String> shipLocations = new ArrayList<>();

    public Ship() {}

    public Ship(GamePlayer gamePlayer, String type, List<String> shipLocations){
        this.gamePlayer = gamePlayer;
        this.type = type;
        this.shipLocations = shipLocations;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public List<String> getShipLocations() {
        return shipLocations;
    }

    public void setShipLocations(List<String> shipLocations) {
        this.shipLocations = shipLocations;
    }
}
