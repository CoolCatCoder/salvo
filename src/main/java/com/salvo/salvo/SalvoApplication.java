package com.salvo.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);

	}

	@Bean
	public CommandLineRunner initData(PlayerRepository repository1, GameRepository repository2, GamePlayerRepository repository3,
									  ShipRepository repository4, SalvoRepository repository5, ScoreRepository repository6) {
		return (args) -> {

				Player Jack = new Player("j.bauer@ctu.gov","24");
				Player Chloe = new Player("c.obrian@ctu.gov","42");
				Player Kim = new Player("kim_bauer@gmail.com","kb");
				Player Tony =  new Player ("t.almeida@ctu.gov","mole");
				Player David = new Player("davidpalmer@freemail.com","dp");
				Player Michelle = new Player("m.dessler@gmail.com","md");

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

				Ship gp1_g1_ship1= new Ship(gp1_g1, "Destroyer", Arrays.asList("H2", "H3", "H4"));
				Ship gp1_g1_ship2= new Ship(gp1_g1, "Submarine", Arrays.asList("E1", "F1", "G1"));
				Ship gp1_g1_ship3= new Ship(gp1_g1, "Patrol Boat", Arrays.asList("B4", "B5"));
				Ship gp2_g1_ship1= new Ship(gp2_g1, "Destroyer", Arrays.asList("B5", "C5", "D5"));
				Ship gp2_g1_ship2= new Ship(gp2_g1, "Patrol Boat", Arrays.asList("F1", "F2"));

				repository4.save(gp1_g1_ship1);
				repository4.save(gp1_g1_ship2);
				repository4.save(gp1_g1_ship3);
				repository4.save(gp2_g1_ship1);
				repository4.save(gp2_g1_ship2);

				Ship gp1_g2_ship1= new Ship(gp1_g2, "Destroyer", Arrays.asList("B5", "C5", "D5"));
				Ship gp1_g2_ship2= new Ship(gp1_g2, "Patrol Boat", Arrays.asList("C6", "C7"));
				Ship gp2_g2_ship1= new Ship(gp2_g2, "Submarine", Arrays.asList("A2", "A3", "A4"));
				Ship gp2_g2_ship2= new Ship(gp2_g2, "Patrol Boat", Arrays.asList("G6", "H6"));

				repository4.save(gp1_g2_ship1);
				repository4.save(gp1_g2_ship2);
				repository4.save(gp2_g2_ship1);
				repository4.save(gp2_g2_ship2);

				Ship gp1_g3_ship1= new Ship(gp1_g3, "Destroyer", Arrays.asList("B5", "C5", "D5"));
				Ship gp1_g3_ship2= new Ship(gp1_g3, "Patrol Boat", Arrays.asList("C6", "C7"));
				Ship gp2_g3_ship1= new Ship(gp2_g3, "Submarine", Arrays.asList("A2", "A3", "A4"));

				Ship gp2_g3_ship2= new Ship(gp2_g3, "Patrol Boat", Arrays.asList("G6", "H6"));

				repository4.save(gp1_g3_ship1);
				repository4.save(gp1_g3_ship2);
				repository4.save(gp2_g3_ship1);
				repository4.save(gp2_g3_ship2);

				Ship gp1_g4_ship1= new Ship(gp1_g4, "Destroyer", Arrays.asList("B5", "C5", "D5"));
				Ship gp1_g4_ship2= new Ship(gp1_g4, "Patrol Boat", Arrays.asList("C6", "C7"));
				Ship gp2_g4_ship1= new Ship(gp2_g4, "Submarine", Arrays.asList("A2", "A3", "A4"));
				Ship gp2_g4_ship2= new Ship(gp2_g4, "Patrol Boat", Arrays.asList("G6", "H6"));

				repository4.save(gp1_g4_ship1);
				repository4.save(gp1_g4_ship2);
				repository4.save(gp2_g4_ship1);
				repository4.save(gp2_g4_ship2);

				Ship gp1_g5_ship1= new Ship(gp1_g5, "Destroyer", Arrays.asList("B5", "C5", "D5"));
				Ship gp1_g5_ship2= new Ship(gp1_g5, "Patrol Boat", Arrays.asList("C6", "C7"));
				Ship gp2_g5_ship1= new Ship(gp2_g5, "Submarine", Arrays.asList("A2", "A3", "A4"));
				Ship gp2_g5_ship2= new Ship(gp2_g5, "Patrol Boat", Arrays.asList("G6", "H6"));

				repository4.save(gp1_g5_ship1);
				repository4.save(gp1_g5_ship2);
				repository4.save(gp2_g5_ship1);
				repository4.save(gp2_g5_ship2);

				repository5.save(new Salvo(gp1_g1, 1, Arrays.asList("B5", "C5", "F1")));
				repository5.save(new Salvo(gp2_g1, 1, Arrays.asList("B4", "B5", "B6")));
				repository5.save(new Salvo(gp1_g1, 2, Arrays.asList("F2", "D5")));
				repository5.save(new Salvo(gp2_g1, 2, Arrays.asList("E1","H3","A2")));

			    repository5.save(new Salvo(gp1_g2, 1, Arrays.asList("A2", "A4", "G6")));
			    repository5.save(new Salvo(gp2_g2, 1, Arrays.asList("B5", "D5", "C7")));
			    repository5.save(new Salvo(gp1_g2, 2, Arrays.asList("A3", "H6")));
			    repository5.save(new Salvo(gp2_g2, 2, Arrays.asList("C5","C6")));

			    repository5.save(new Salvo(gp1_g3, 1, Arrays.asList("G6", "H6", "A4")));
			    repository5.save(new Salvo(gp2_g3, 1, Arrays.asList("H1", "H2", "H3")));
			    repository5.save(new Salvo(gp1_g3, 2, Arrays.asList("A2", "A3","D8")));
			    repository5.save(new Salvo(gp2_g3, 2, Arrays.asList("E1","F2","G3")));

			    repository5.save(new Salvo(gp1_g4, 1, Arrays.asList("A3", "A4", "F7")));
			    repository5.save(new Salvo(gp2_g4, 1, Arrays.asList("B5", "C6", "H1")));
			    repository5.save(new Salvo(gp1_g4, 2, Arrays.asList("A2", "G6","H6")));
			    repository5.save(new Salvo(gp2_g4, 2, Arrays.asList("C5","C7","D5")));

                repository5.save(new Salvo(gp1_g5, 1, Arrays.asList("A1", "A2", "A3")));
                repository5.save(new Salvo(gp2_g5, 1, Arrays.asList("B5", "B6", "B7")));
                repository5.save(new Salvo(gp1_g5, 2, Arrays.asList("G6", "G7","G8")));
                repository5.save(new Salvo(gp2_g5, 2, Arrays.asList("C6","D6","E6")));
                repository5.save(new Salvo(gp2_g5, 3, Arrays.asList("H1","H8")));

                Date newDate1 = Date.from(date1.toInstant().plusSeconds(1800));
                Date newDate2 = Date.from(date2.toInstant().plusSeconds(1800));
                Date newDate3 = Date.from(date3.toInstant().plusSeconds(1800));
                Date newDate4 = Date.from(date4.toInstant().plusSeconds(1800));

                repository6.save(new Score(gp1_g1,1.0, newDate1));
                repository6.save(new Score(gp2_g1,0.0, newDate1));
			    repository6.save(new Score(gp1_g2,0.5, newDate2));
			    repository6.save(new Score(gp2_g2,0.5, newDate2));
			    repository6.save(new Score(gp1_g3,1.0, newDate3));
			    repository6.save(new Score(gp2_g3,0.0, newDate3));
			    repository6.save(new Score(gp1_g4,0.5, newDate4));
			    repository6.save(new Score(gp2_g4,0.5, newDate4));

			};
	}

}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {
//The job of this new class is to take the name someone has entered for log in,
// search the database with that name, and return a UserDetails object with name,
// password, and role information for that user, if any.
	@Autowired
	PlayerRepository playerRepository;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService());
	}

	@Bean
	UserDetailsService userDetailsService() {
		return new UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				List<Player> players = playerRepository.findByUserName(username);
				if (!players.isEmpty()) {
					Player player = players.get(0);
					return new User(player.getUserName(), player.getPassWord(),
							AuthorityUtils.createAuthorityList("USER"));
				} else {
					throw new UsernameNotFoundException("Username " + username + " not found");
				}
			}
		};
	}
}

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()  // Who can see what
				      .antMatchers("/web/games.html","/styles/games.css", "/scripts/games.js",
							  "/styles/copy_waves.jpg", "/styles/faviconit/favicon.ico").permitAll()
				      .antMatchers("/api/login", "/api/games", "/api/players").permitAll()
				      .anyRequest().fullyAuthenticated() //ANY other I forgot, only if fully authenticated
				      .and()    // and() is separating authorizeRequests() from formLogin()
				.formLogin()
				      .usernameParameter("username")  //By default Spring understands them as "username" and "password"
				      .passwordParameter("password")
				      .loginProcessingUrl("/api/login");

		http.logout().logoutUrl("/api/logout");   // where am I going to tell you to perform a logout

		// turn off checking for CSRF tokens
		http.csrf().disable();

		// if user is not authenticated, just send an authentication failure response
		http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if login is successful, just clear the flags asking for authentication
		http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

		// if login fails, just send an authentication failure response
		http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if logout is successful, just send a success response
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
	}

	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}
}