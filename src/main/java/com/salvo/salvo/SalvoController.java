package com.salvo.salvo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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

    public Map<String, Object> makeLeaderboard(Authentication authentication) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (authentication != null){
            map.put("player", makePlayerDTO(getCurrentUser(authentication)));
        }
        map.put("games", getAllGames());
        map.put("leaderboard", playersScoreInfo());
        return map;
    }

    public Player getCurrentUser(Authentication authentication){

        if (isGuest(authentication)){
            return null;
        }   else   {
       return repoPlayers.findOneByUserName(authentication.getName());
        }
    }

    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

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

    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createPlayer(@RequestParam String username, @RequestParam String password) {
        if (username == null) {
            return new ResponseEntity<>(makeMap("error", "No username"), HttpStatus.FORBIDDEN);
        } else if (password == null){
            return new ResponseEntity<>(makeMap("error", "No password"), HttpStatus.FORBIDDEN);
        }
        Player player = repoPlayers.findOneByUserName(username);
        if (player != null) {
            return new ResponseEntity<>(makeMap("error", "Name already in use"), HttpStatus.CONFLICT);
        }
        player = repoPlayers.save(new Player(username, password));
        return new ResponseEntity<>(makeMap("username", player.getUserName()), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/games", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createGame(Authentication authentication) {
        Player currentPlayer = getCurrentUser(authentication);
        if (authentication != null){
            Game newGame = repo.save(new Game());
            GamePlayer newGamePlayer = repoGp.save(new GamePlayer(newGame, currentPlayer));
            return new ResponseEntity<>(makeMap("newGamePlayerId", newGamePlayer.getId()), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(makeMap("error","Unauthorized access"), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(path = "/game/{gameId}/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> joinGame(Authentication authentication, @PathVariable long gameId){
        Player currentPlayer = getCurrentUser(authentication);

        if (authentication != null){
            Game currentGame = repo.findOne(gameId);
            if (currentGame != null){
                Set<GamePlayer> currentGPs = currentGame.getGamePlayerSet();
                if (currentGPs.size() == 2){
                    return new ResponseEntity<>(makeMap("error", "This game is full"), HttpStatus.FORBIDDEN);
                } else {
                    GamePlayer alreadyGp = currentGPs.stream().findFirst().orElse(null);  //Find 1st of set, we know there is something there, have to put null
                    if (alreadyGp.getPlayer_playing().getId() != currentPlayer.getId()){
                    GamePlayer joinedGamePlayer = repoGp.save(new GamePlayer(currentGame, currentPlayer));
                               //If i can add something in the set, that means that it currently doesn´t exist in it...
                        return new ResponseEntity<>(makeMap("joinedGamePlayerId", joinedGamePlayer.getId()), HttpStatus.CREATED);
                    }  else{
                         return new ResponseEntity<>(makeMap("error", "Player already joined"),HttpStatus.FORBIDDEN);
                        }
                }
            } else {
                return new ResponseEntity<>(makeMap("error", "No game found"), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(makeMap("error", "Unauthorized access"), HttpStatus.UNAUTHORIZED);
        }
    }

//    @RequestMapping(path = "/game/{gameID}/players", method = RequestMethod.POST)
//    public ResponseEntity<Map<String, Object>> joinGame(@PathVariable long gameID, Authentication authentication){
//        Player playa = getCurrentUser(authentication);
//        if(playa == null){
//            return new ResponseEntity<>(makeMap("error", "Unauthorized access"), HttpStatus.UNAUTHORIZED);
//        } else {
//            Game thisGame = repo.findOne(gameID);
//            if (thisGame == null) {
//                return new ResponseEntity<>(makeMap("error", "No such game"), HttpStatus.FORBIDDEN);
//            } else if(thisGame.getGamePlayerSet().size() == 2) {
//                return new ResponseEntity<>(makeMap("error", "Game is full"), HttpStatus.FORBIDDEN);
//            } else {
//                GamePlayer nuGP = new GamePlayer(thisGame, playa);
//                repoGp.save(nuGP);
//                return new ResponseEntity<>(makeMap("gpid", nuGP.getId()), HttpStatus.CREATED);
//            }
//        }
//    }

    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    @RequestMapping("/game_view/{gamePlayerId}")
    private ResponseEntity<Object> getGameView (@PathVariable long gamePlayerId, Authentication authentication){
        GamePlayer loggedUser = repoGp.findOne(gamePlayerId);
        if (getCurrentUser(authentication).getId() == loggedUser.getPlayer_playing().getId()){
            return new ResponseEntity<>(gameView(loggedUser), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(makeMap("error", "Unauthorized access"), HttpStatus.UNAUTHORIZED);
        }
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





