package com.baumstaemme.backend.game.unit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitQueueEntryRepo extends JpaRepository<UnitQueueEntry, Long> {
}
