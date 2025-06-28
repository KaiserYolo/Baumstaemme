package com.baumstaemme.backend.game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface GameRepo extends JpaRepository<Game,Long> {

    @Query("SELECT game.id FROM Game game")
    List<Long> findAllIds();
}
