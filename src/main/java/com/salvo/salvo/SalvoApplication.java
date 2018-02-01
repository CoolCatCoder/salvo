package com.salvo.salvo;

import com.salvo.salvo.ShipRepository;
import com.sun.webkit.dom.KeyboardEventImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);

	}

	@Bean
	public CommandLineRunner initData(PlayerRepository repository1, GameRepository repository2, GamePlayerRepository repository3, ShipRepository repository4) {
		return (args) -> {
			// save a couple of players
			Player Jack = new Player("j.bauer@ctu.gov");
			Player Chloe = new Player("c.obrian@ctu.gov");
			Player Kim = new Player("kim_bauer@gmail.com");
			Player Tony =  new Player ("t.almeida@ctu.gov");
			Player David = new Player("davidpalmer@freemail.com");
			Player Michelle = new Player("m.dessler@gmail.com");

			repository1.save(Jack);
			repository1.save(Chloe);
			repository1.save(Kim);
			repository1.save(Tony);
			repository1.save(David);
			repository1.save(Michelle);

			Date date1 = new Date();
			Game game1 = new Game(date1);

			Date date2 = Date.from(date1.toInstant().plusSeconds(3600));
			Game game2 = new Game(date2);

			Date date3 = Date.from(date1.toInstant().plusSeconds(7200));
			Game game3 = new Game(date3);

			Date date4 = Date.from(date3.toInstant().plusSeconds(3600));
			Game game4 = new Game(date4);

			Date date5 = Date.from(date4.toInstant().plusSeconds(3600));
			Game game5 = new Game(date5);

			repository2.save(game1);
			repository2.save(game2);
			repository2.save(game3);
			repository2.save(game4);
			repository2.save(game5);


			GamePlayer gp1_g1 = new GamePlayer(game1, Jack);
			GamePlayer gp2_g1 = new GamePlayer(game1, Chloe);
			GamePlayer gp1_g2 = new GamePlayer(game2, Jack);
			GamePlayer gp2_g2 = new GamePlayer(game2, Chloe);
			GamePlayer gp1_g3 = new GamePlayer(game3, Chloe);
			GamePlayer gp2_g3 = new GamePlayer(game3, Tony);
			GamePlayer gp1_g4 = new GamePlayer(game4, Chloe);
			GamePlayer gp2_g4 = new GamePlayer(game4, Jack);
			GamePlayer gp1_g5 = new GamePlayer(game5, Tony);
			GamePlayer gp2_g5 = new GamePlayer(game5, Jack);

			repository3.save(gp1_g1);
			repository3.save(gp2_g1);
			repository3.save(gp1_g2);
			repository3.save(gp2_g2);
			repository3.save(gp1_g3);
			repository3.save(gp2_g3);
			repository3.save(gp1_g4);
			repository3.save(gp2_g4);
			repository3.save(gp1_g5);
			repository3.save(gp2_g5);

			Ship gp1_g1_s1= new Ship(gp1_g1, "Destroyer", Arrays.asList("H2", "H3", "H4"));
			Ship gp1_g1_s2= new Ship(gp1_g1, "Submarine", Arrays.asList("E1", "F1", "G1"));
			Ship gp1_g1_s3= new Ship(gp1_g1, "Patrol Boat", Arrays.asList("B4", "B5"));
			Ship gp2_g1_s1= new Ship(gp2_g1, "Destroyer", Arrays.asList("B5", "C5", "D5"));
			Ship gp2_g1_s2= new Ship(gp2_g1, "Patrol Boat", Arrays.asList("F1", "F2"));

			repository4.save(gp1_g1_s1);
			repository4.save(gp1_g1_s2);
			repository4.save(gp1_g1_s3);
			repository4.save(gp2_g1_s1);
			repository4.save(gp2_g1_s2);

			Ship gp1_g2_s1= new Ship(gp1_g2, "Destroyer", Arrays.asList("B5", "C5", "D5"));
			Ship gp1_g2_s2= new Ship(gp1_g2, "Patrol Boat", Arrays.asList("C6", "C7"));
			Ship gp2_g2_s1= new Ship(gp2_g2, "Submarine", Arrays.asList("A2", "A3", "A4"));
			Ship gp2_g2_s2= new Ship(gp2_g2, "Patrol Boat", Arrays.asList("G6", "H6"));

			repository4.save(gp1_g2_s1);
			repository4.save(gp1_g2_s2);
			repository4.save(gp2_g2_s1);
			repository4.save(gp2_g2_s2);

			Ship gp1_g3_s1= new Ship(gp1_g3, "Destroyer", Arrays.asList("B5", "C5", "D5"));
			Ship gp1_g3_s2= new Ship(gp1_g3, "Patrol Boat", Arrays.asList("C6", "C7"));
			Ship gp2_g3_s1= new Ship(gp2_g3, "Submarine", Arrays.asList("A2", "A3", "A4"));
			Ship gp2_g3_s2= new Ship(gp2_g3, "Patrol Boat", Arrays.asList("G6", "H6"));

			repository4.save(gp1_g3_s1);
			repository4.save(gp1_g3_s2);
			repository4.save(gp2_g3_s1);
			repository4.save(gp2_g3_s2);

			Ship gp1_g4_s1= new Ship(gp1_g4, "Destroyer", Arrays.asList("B5", "C5", "D5"));
			Ship gp1_g4_s2= new Ship(gp1_g4, "Patrol Boat", Arrays.asList("C6", "C7"));
			Ship gp2_g4_s1= new Ship(gp2_g4, "Submarine", Arrays.asList("A2", "A3", "A4"));
			Ship gp2_g4_s2= new Ship(gp2_g4, "Patrol Boat", Arrays.asList("G6", "H6"));

			repository4.save(gp1_g4_s1);
			repository4.save(gp1_g4_s2);
			repository4.save(gp2_g4_s1);
			repository4.save(gp2_g4_s2);

			Ship gp1_g5_s1= new Ship(gp1_g5, "Destroyer", Arrays.asList("B5", "C5", "D5"));
			Ship gp1_g5_s2= new Ship(gp1_g5, "Patrol Boat", Arrays.asList("C6", "C7"));
			Ship gp2_g5_s1= new Ship(gp2_g5, "Submarine", Arrays.asList("A2", "A3", "A4"));
			Ship gp2_g5_s2= new Ship(gp2_g5, "Patrol Boat", Arrays.asList("G6", "H6"));

			repository4.save(gp1_g5_s1);
			repository4.save(gp1_g5_s2);
			repository4.save(gp2_g5_s1);
			repository4.save(gp2_g5_s2);




















		};
	}


}
