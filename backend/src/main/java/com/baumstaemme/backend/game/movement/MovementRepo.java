package com.baumstaemme.backend.game.movement;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementRepo extends JpaRepository<Movement, Long> {
}
