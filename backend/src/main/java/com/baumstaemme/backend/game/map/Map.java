package com.baumstaemme.backend.game.map;

import com.baumstaemme.backend.game.tile.Tile;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "MAP")
public class   Map {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int size;

    @OneToMany
    private List<Tile> tiles;
}
