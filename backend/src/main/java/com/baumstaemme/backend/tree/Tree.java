//TODO Builder

package com.baumstaemme.backend.tree;

import com.baumstaemme.backend.player.Player;
import com.baumstaemme.backend.tile.Tile;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "trees")
@Data
@NoArgsConstructor
public class Tree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Maybe Sequence
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "player_id")
    @JsonBackReference
    private Player owner;

    @OneToOne
    @JoinColumn(name = "tile_id") // Fremdschlüssel für die Tabelle
    @JsonBackReference
    private Tile tile;

    //private int posX;
    //private int posY;

    // Resources
    private int leaves = 100;
    private int leavesProduction = 10;
    //private long lastProducedTimestamp = System.currentTimeMillis();


    // Stats/Buildings
    private int trunk = 1;
    private int bark = 1;
    private int branches = 1;
    private int root = 1;


    // Units
    //private int stationaryUnits;
    //private int movingUnits;
    //private int visitingUnits;
}