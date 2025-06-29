package com.baumstaemme.backend.game.player;

import com.baumstaemme.backend.game.Game;
import com.baumstaemme.backend.game.tree.Tree;
import com.baumstaemme.backend.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "PLAYERS")
@Data
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne()
    @JoinColumn(name = "game_id")
    Game game;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Tree> trees = new ArrayList<>();
}
