package com.baumstaemme.backend.game;

import com.baumstaemme.backend.game.map.Map;
import com.baumstaemme.backend.game.player.Player;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "GAMES")
@Data
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Date created;
    @Enumerated(EnumType.STRING)
    private GameStatus status;

    @OneToOne
    private Map map;
    @OneToMany
    private List<Player> players;
}
