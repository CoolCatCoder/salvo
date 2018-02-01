package com.salvo.salvo;

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

    @RequestMapping("/games")
    public List<Object> getAllGames() {
        return repo.findAll()
                .stream()
                .map(game ->game.toDTO())
                .collect(Collectors.toList());
    }


    @Autowired
    private GamePlayerRepository repoGp;
    private long nn;

    @RequestMapping("/game_view/{gamePlayerId}")
    private Object getGameView (@PathVariable long gamePlayerId){
        return gameView(repoGp.findOne(gamePlayerId));
    }

    public Map<String,Object> gameView(GamePlayer gamePlayer){
        Map<String,Object> viewGame;
        viewGame = new LinkedHashMap<>();
        viewGame.put("id", gamePlayer.getGame().getId());
        viewGame.put("created", gamePlayer.getGame().getCreationDate());
        viewGame.put("gamePlayers", gamePlayer.gPToDTO());
        return viewGame;
    }

   //WHERE DO I PUT THE PATH VARIABLE AND WHERE DO I USE THE GAME PLAYERS REPOSITORY?????

    //Annotate the method with @RequestMapping to map the URL /game_view/nn to that method.
    //Annotate the method parameter with @PathVariable to extract the desired game player ID nn from the URL as a long integer.

    }
