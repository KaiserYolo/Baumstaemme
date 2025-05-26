package com.baumstaemme.backend.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


enum TileType {
    EMPTY,
    TREE,
    EVENT,
    RESOURCE
}

@Entity @Data @Table(name = "tiles")
public class Tile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int x;
    private int y;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TileType type;

    @OneToOne(mappedBy = "tile", cascade = CascadeType.ALL)
    private Tree tree;


    public Tile() {
        this(0, 0);
    }

    public Tile(int x, int y) {
        this(x, y, TileType.EMPTY, null);
    }

    public Tile(int x, int y, TileType type, Tree tree) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.tree = tree;
    }
}
