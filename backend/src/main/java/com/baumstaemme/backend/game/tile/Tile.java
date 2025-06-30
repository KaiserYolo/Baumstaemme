package com.baumstaemme.backend.game.tile;

import com.baumstaemme.backend.game.tree.Tree;
import jakarta.persistence.*;
import lombok.Data;

import java.awt.*;

@Entity
@Table(name = "TILES")
@Data
public class Tile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Point position; // ist des überhaupt sinnvoll?

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TileType type;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Tree tree;
}
