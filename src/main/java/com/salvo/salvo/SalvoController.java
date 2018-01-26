package com.salvo.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")  // adds /api to all URLs for this controller, so that they are not confused with the /rest
public class SalvoController {

    @Autowired
    private GameRepository repo;

    @RequestMapping("/games")
    public List<Object> getGameIds() {
       return repo.findAll().stream()
                .map(b -> b.getId())
                .collect(toList());
    }

}

//    List<Object> getGameIds(Set<Game> allGames) {
//        return allGames.stream().map(Game::getId).collect(Collectors::toList);
//    }

//public Set<BillingType> getBillingTypes(List<Billing> billings) {
//  return
//    billings.stream()
//            .map(b -> b.getType())
//            .collect(toSet());
//}