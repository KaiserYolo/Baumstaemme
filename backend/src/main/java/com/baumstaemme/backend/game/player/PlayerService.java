package com.baumstaemme.backend.game.player;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private final PlayerRepo playerRepo;

    public PlayerService(PlayerRepo playerRepo) {
        this.playerRepo = playerRepo;
    }

    public Player save(Player player) {
        if (player == null) {
            return null;
        }
        return playerRepo.save(player);
    }

    @Transactional
    public Player create() {
        Player player = new Player();
        return save(player);
    }

    public Player findById(Long id) {
        return playerRepo.findById(id).orElse(null);
    }
}
