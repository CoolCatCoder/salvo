package com.salvo.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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

}
//    @RequestMapping("/games")
//    public List<Object> getGameIds() {
//       return repo.findAll().stream()
//                .map(b -> b.getId())
//                .collect(toList());
//    }

//    List<Object> getGameIds(Set<Game> allGames) {
//        return allGames.stream().map(Game::getId).collect(Collectors::toList);
//    }

//public Set<BillingType> getBillingTypes(List<Billing> billings) {
//  return
//    billings.stream()
//            .map(b -> b.getType())
//            .collect(toSet());
//}