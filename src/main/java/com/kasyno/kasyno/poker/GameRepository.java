package com.kasyno.kasyno.poker;

import com.kasyno.kasyno.poker.player.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game,Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Game g " + "SET g.player1 = ?2 " + "WHERE g.id = ?1")
    void updatePlayer1(Long id, Player player1);

    @Transactional
    @Modifying
    @Query("UPDATE Game g " + "SET g.player2 = ?2 " + "WHERE g.id = ?1")
    void updatePlayer2(Long id, Player player2);

    @Transactional
    @Modifying
    @Query("UPDATE Game g " + "SET g.player3 = ?2 " + "WHERE g.id = ?1")
    void updatePlayer3(Long id, Player player3);

    @Transactional
    @Modifying
    @Query("UPDATE Game g " + "SET g.player4 = ?2 " + "WHERE g.id = ?1")
    void updatePlayer4(Long id, Player player4);


    List<GameIdAndPlayers> findAllByOrderById();
}
