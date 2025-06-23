package com.baumstaemme.backend.game.upgrade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UpgradeRepo extends JpaRepository<Upgrade, Long> {
    List<Upgrade> findByStatus(UpgradeStatus status);

    @Query("SELECT upgrade.id FROM Upgrade upgrade ")
    List<Long> findAllIds();
}
