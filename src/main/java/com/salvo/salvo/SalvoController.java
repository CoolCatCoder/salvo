package com.salvo.salvo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private PlayerRepository repoPlayers;
    @Autowired
    private GamePlayerRepository repoGp;

    @RequestMapping("/games")

    public Map<String, Object> makeLeaderboard() {
        Map<String, Object> map = new LinkedHashMap<>();
//       map.put("player", getCurrentUser(authentication));
        map.put("games", getAllGames());
        map.put("leaderboard", playersScoreInfo());
        return map;
    }

//    public Map<String, Object> getCurrentUser(Authentication authentication){
//        Player currentPlayer;
//        Map<String, Object> currentUser =  new LinkedHashMap<>();
//        currentUser.put("name", repoPlayers.findOneByUserName(authentication.getName()));
//        return currentUser;
//    }
//
//    private boolean isGuest(Authentication authentication) {
//        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
//    }

    public List<Object> getAllGames() {
        return repo.findAll()
                .stream()
                .map(game -> makeGameDTO(game))
                .collect(Collectors.toList());
    }

    public List<Object> playersScoreInfo() {
        return repoPlayers.findAll()
                .stream()
                .map(player ->makeScoreInfo(player) )
                .collect(Collectors.toList());
    }

    public Map<String, Object> makeScoreInfo(Player player){
    Map <String, Object> playerInfo = new LinkedHashMap<>();
    Set<Score> allScores = player.getScoreSet();
        double total = 0;
        int win = 0;
        int loss = 0;
        int tie = 0;
        for (Score score: allScores) {

        if (score.getScore() == 1.0){
            win++;
            total = total + 1.0;
        }else if (score.getScore() == 0.0){
            loss++;
        }else if(score.getScore() == 0.5){
            tie++;
            total = total + 0.5;
        }
    }

    playerInfo.put("name", player.getUserName());
    playerInfo.put("total_score", total);
    playerInfo.put("total_wins", win);
    playerInfo.put("total_losses", loss);
    playerInfo.put("total_ties", tie);
    return playerInfo;
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
        Score score= gamePlayer.getScore().orElse(null);
        gPDto.put("id", gamePlayer.getId());
        gPDto.put("player", makePlayerDTO(gamePlayer.getPlayer_playing()));  //I HAVE CHANGED IT AND THE HTML IS NOT WORKING NOW!!!
            if (score != null) {
            gPDto.put("score", score.getScore());
        }else{
            gPDto.put("score", null);
        }
        return gPDto;
    }

    public Map<String, Object> makePlayerDTO (Player player){
        Map<String, Object> pDto = new LinkedHashMap<>();
        pDto.put("id", player.getId());
        pDto.put("username", player.getUserName());
        return pDto;
    }


    public Map<String, Object> makeShipsDTO(Ship ship) {
        Map<String, Object> sDto = new LinkedHashMap<String, Object>();
        sDto.put("type",ship.getType());
        sDto.put("locations", ship.getShipLocations());
        return sDto;
    }


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


}





