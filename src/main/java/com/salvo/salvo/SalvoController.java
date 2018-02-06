package com.salvo.salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")  // adds /api to all URLs for this controller
public class SalvoController {

    @Autowired
    private GameRepository repo;

    @Autowired
    private GamePlayerRepository repoGp;

    @RequestMapping("/games")
    public List<Object> getAllGames() {
        return repo.findAll()
                .stream()
                .map(game -> makeGameDTO(game))
                .collect(Collectors.toList());
    }



    @RequestMapping("/game_view/{gamePlayerId}")
    private Object getGameView (@PathVariable long gamePlayerId){
        return gameView(repoGp.findOne(gamePlayerId));
    }

    public Map<String,Object> gameView(GamePlayer gamePlayer){
        Map<String,Object> viewGame;
        viewGame = new LinkedHashMap<>();
        viewGame.put("id", gamePlayer.getGame().getId());
        viewGame.put("created", gamePlayer.getGame().getCreationDate());
        viewGame.put("gamePlayers", getGamePlayers(gamePlayer.getGame()));
        viewGame.put("ships", getMyShips(gamePlayer));
        return viewGame;
    }

    //In the Map for each game, put keys and values for the game ID, creation date, and gamePlayers.
    public Map<String, Object> makeGameDTO(Game game) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", game.getId());
        dto.put("created", game.getCreationDate());
        dto.put("gamePlayers", getGamePlayers(game));
        return dto;
    }

    //For the value for the gamePlayers key, create a List with a Map for each GamePlayer.
    public Map<String, Object> makeGamePlayerDTO(GamePlayer gamePlayer) {
        Map<String, Object> gPDto = new LinkedHashMap<String, Object>();
        gPDto.put("id", gamePlayer.getId());         //In the Map for each GamePlayer, put keys and values for the GamePlayer ID and the player.
        gPDto.put("player", gamePlayer.getPlayer_playing());
        return gPDto;
    }

    public Map<String, Object> makeShipsDTO(Ship ship) {
        Map<String, Object> sDto = new LinkedHashMap<String, Object>();
        sDto.put("type",ship.getType());
        sDto.put("locations", ship.getShipLocations());
        return sDto;
    }

    @JsonIgnore
    public List<Object> getGamePlayers(Game game) {
        return game.gamePlayerSet.stream()
                .map(gamePlayer -> makeGamePlayerDTO(gamePlayer))
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public List<Object> getMyShips(GamePlayer gamePlayer) {
        return gamePlayer.getShipSet().stream()
                .map(ship ->makeShipsDTO(ship))
                .collect(Collectors.toList());
    }

   //WHERE DO I PUT THE PATH VARIABLE AND WHERE DO I USE THE GAME PLAYERS REPOSITORY?????

    //Annotate the method with @RequestMapping to map the URL /game_view/nn to that method.
    //Annotate the method parameter with @PathVariable to extract the desired game player ID nn from the URL as a long integer.

    }
