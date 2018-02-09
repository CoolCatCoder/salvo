package com.salvo.salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.GeneratedValue;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
        viewGame.put("salvoes", getAllSalvoes(gamePlayer.getGame().getGamePlayerSet()));
      //  viewGame.put("salvoes", getDTOSalvoes(getAllSalvoes(gamePlayer.getGame().getGamePlayerSet())));
        return viewGame;
}

    //In the Map for each game, put keys and values for the game ID, creation date, and gamePlayers.
    public Map<String, Object> makeGameDTO(Game game) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", game.getId());
        dto.put("created", game.getCreationDate());
        dto.put("gamePlayers", getGamePlayers(game));
        return dto;
        //For the value for the gamePlayers key, create a List with a Ma
    }

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

//    public Map<String, Object> makeSalvoesDTO(Salvo salvo) {
//        Map<String, Object> svDto = new LinkedHashMap<String, Object>();
//        svDto.put("turn", salvo.getTurn());
//        svDto.put("player", salvo.getGamePlayer().getPlayer_playing().getId());   //OR JUST PLAYER ID????
//        svDto.put("locations", salvo.getSalvoLocations());
//        return svDto;
//    }


    public List<Object> getGamePlayers(Game game) {
        return game.gamePlayerSet.stream()
                .map(gamePlayer -> makeGamePlayerDTO(gamePlayer))
                .collect(Collectors.toList());
    }

    public List<Object> getMyShips(GamePlayer gamePlayer) {
        return gamePlayer.getShipSet().stream()
                .map(ship ->makeShipsDTO(ship))
                .collect(Collectors.toList());
    }

//Create list and stream and join , the same we did for
    public Map<String, Object> getAllSalvoes(Set <GamePlayer> gamePlayerSet){

          List<Map<String, Object>>  allGpSalvoesList = gamePlayerSet.stream()
                  .map(gamePlayer -> getMySalvoes(gamePlayer))
                  .collect(Collectors.toList());

          Map<String, Object> allGpSalvoesMap = joinMaps(allGpSalvoesList);
        return allGpSalvoesMap;

    }

    // Created a map of Salvoes for a gamePlayer
    public Map<String, Object> getMySalvoes(GamePlayer gamePlayer) {
        Map<String, Object> getMySvs = new LinkedHashMap<>();
        List<Map<String, Object>> salvoesMapsList = gamePlayer.getSalvoSet()
                .stream()
                .map(salvo -> makeSalvoesDTO(salvo))
                .collect(Collectors.toList());

        Map<String, Object> allSalvoes = joinMaps(salvoesMapsList);

        getMySvs.put(String.valueOf(gamePlayer.getPlayer_playing().getId()), allSalvoes);
      return getMySvs;
    }

// Create the DTO for the salvoes
    public Map<String, Object> makeSalvoesDTO(Salvo salvo) {
        Map<String, Object> svDto = new LinkedHashMap<String, Object>();
        svDto.put(String.valueOf(salvo.getTurn()), salvo.getSalvoLocations());
        return svDto;
    }

    //Create a function that joins the maps in a List of maps
    public Map<String, Object> joinMaps(List <Map <String, Object>> mapsList){
        Map<String, Object> bigMap = new LinkedHashMap<>();
        mapsList.forEach(map -> {
            map.forEach((key, value) -> {
                bigMap.put(key, value);
            });
        });

        return bigMap;
    }

//    @JsonIgnore
//    public List<Object> getAllSalvoes(Set <GamePlayer> gamePlayerSet) {
//        return gamePlayerSet.stream()
//                .map(gamePlayer -> gamePlayer.getSalvoSet())
//                .collect(Collectors.toList());
//    }

//    @JsonIgnore
//    public List<Object> getDTOSalvoes(List <Salvo> getAllSalvoes) {
//        return getAllSalvoes.stream()
//                .map(salvo -> makeSalvoesDTO(salvo))
//                .collect(Collectors.toList());
}


   //WHERE DO I PUT THE PATH VARIABLE AND WHERE DO I USE THE GAME PLAYERS REPOSITORY?????

    //Annotate the method with @RequestMapping to map the URL /game_view/nn to that method.
    //Annotate the method parameter with @PathVariable to extract the desired game player ID nn from the URL as a long integer.


