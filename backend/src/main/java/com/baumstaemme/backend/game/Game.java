package com.baumstaemme.backend.game;

import com.baumstaemme.backend.game.map.Map;
import com.baumstaemme.backend.game.player.Player;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "GAME")
@Data
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @CreationTimestamp
    private LocalDateTime created;

    @Enumerated(EnumType.STRING)
    private GameStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    private Map map;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Player> players;
}
