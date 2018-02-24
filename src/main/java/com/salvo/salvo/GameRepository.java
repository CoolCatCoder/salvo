package com.salvo.salvo;


        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.rest.core.annotation.RepositoryRestResource;

        import java.util.List;

// GameRepository is defined to be a JpaRepository that manages instances of Games

@RepositoryRestResource   // A Repository class is analogous to a table
public interface GameRepository extends JpaRepository<Game, Long> {

}
