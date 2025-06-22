package com.baumstaemme.backend.tile;


import jakarta.persistence.*;
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

    private int xCoordinate;
    private int yCoordinate;

    @Enumerated(EnumType.STRING)
    private TileType type;

    //@OneToOne(mappedBy = "tile", cascade = CascadeType.ALL)
    //private Tree tree;

    public Tile(int xCoordinate, int yCoordinate, TileType type) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.type = type;
    }
}
