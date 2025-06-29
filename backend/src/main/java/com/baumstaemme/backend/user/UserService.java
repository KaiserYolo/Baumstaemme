package com.baumstaemme.backend.user;

import com.baumstaemme.backend.game.player.Player;
import com.baumstaemme.backend.game.player.PlayerRepo;
import com.baumstaemme.backend.game.player.PlayerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final PlayerService playerService;

    public UserService(UserRepo userRepo, PlayerService playerService) {
        this.userRepo = userRepo;
        this.playerService = playerService;
    }

    public User save(User user) {
        if (user == null) {
            return null;
        }
        return userRepo.save(user);
    }

    public User findById(Long id) {
        return userRepo.findById(id).orElse(null);
    }


    public Player addPlayer(Long id) {
        User user = findById(id);
        if (user == null) {
            return null;
        }
        Player player = playerService.create(user);
        user.getPlayers().add(player);

        save(user);

        return player;
    }




}
