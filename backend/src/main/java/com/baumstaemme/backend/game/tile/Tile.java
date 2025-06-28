package com.baumstaemme.backend.game.tile;


import com.baumstaemme.backend.game.tree.Tree;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;


@Entity
@Table(name = "TILES")
@Data
@NoArgsConstructor
public class Tile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Point position;

    @Enumerated(EnumType.STRING)
    private TileType type;

    @OneToOne
    private Tree tree;
}
