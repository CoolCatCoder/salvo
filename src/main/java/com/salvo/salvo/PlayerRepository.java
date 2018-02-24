package com.salvo.salvo;

        import java.util.List;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.repository.query.Param;
        import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// PlayerRepository is defined to be a JpaRepository that manages instances of Players

@RepositoryRestResource   // A Repository class is analogous to a table
public interface PlayerRepository extends JpaRepository<Player, Long> {
 List<Player> findByUserName (@Param("userName")String userName);
 Player findOneByUserName (@Param("userName")String userName);
}