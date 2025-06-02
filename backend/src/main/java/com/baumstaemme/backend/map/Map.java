package com.baumstaemme.backend.map;

import com.baumstaemme.backend.tile.Tile;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "map")
public class Map {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int length;
    private int height;

    @OneToMany
    private List<Tile> tiles;
}
