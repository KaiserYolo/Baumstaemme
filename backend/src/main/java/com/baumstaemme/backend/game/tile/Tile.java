package com.baumstaemme.backend.game.tile;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "tiles")
@Data
@NoArgsConstructor
public class Tile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int x;
    private int y;

    @Enumerated(EnumType.STRING)
    private TileType type;

    //@OneToOne(mappedBy = "tile", cascade = CascadeType.ALL)
    //private Tree tree;
}
