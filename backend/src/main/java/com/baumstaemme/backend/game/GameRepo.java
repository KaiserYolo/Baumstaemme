package com.baumstaemme.backend.game;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepo extends JpaRepository<Game,Long> {}
