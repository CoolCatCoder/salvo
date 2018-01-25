package com.salvo.salvo;

import com.sun.webkit.dom.KeyboardEventImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import java.util.Date;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);

	}

	@Bean
	public CommandLineRunner initData(PlayerRepository repository1, GameRepository repository2, GamePlayerRepository repository3) {
		return (args) -> {
			// save a couple of players
			Player Jack = new Player("jb@gmail.com");
			Player Chloe = new Player("chloe_ob@hotmail.com");
			Player Kim = new Player("kimmy@gmail.com");
			Player David = new Player("davidpalmer@freemail.com");
			Player Michelle = new Player("m.dessler@gmail.com");

			repository1.save(Jack);
			repository1.save(Chloe);
			repository1.save(Kim);
			repository1.save(David);
			repository1.save(Michelle);

			Date date1 = new Date();
			Game game1 = new Game(date1);

			Date date2 = Date.from(date1.toInstant().plusSeconds(3600));
			Game game2 = new Game(date2);

			Date date3 = Date.from(date1.toInstant().plusSeconds(7200));
			Game game3 = new Game(date3);

			repository2.save(game1);
			repository2.save(game2);
			repository2.save(game3);


			GamePlayer gp1 = new GamePlayer(game1, Jack);
			repository3.save(gp1);

		};
	}


}
