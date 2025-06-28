package com.baumstaemme.backend.user;

import com.baumstaemme.backend.game.player.Player;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
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

    public void addPlayer(Long id, Player player) {
        User user = findById(id);
        List<Player> players = user.getPlayers();
        players.add(player);
        user.setPlayers(players);
        userRepo.save(user);
    }




}
