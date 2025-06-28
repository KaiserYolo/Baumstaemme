package com.baumstaemme.backend.game.player;

import com.baumstaemme.backend.game.map.Map;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepo extends JpaRepository<Player,Long> {
}
