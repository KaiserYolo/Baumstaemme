//TODO Builder

package com.baumstaemme.backend.tree;

import com.baumstaemme.backend.tile.Tile;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;


@Entity @Data @Table(name = "trees")
public class Tree {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // Maybe Sequence
    private Long id;
    @NotBlank @Size(max = 20)
    private String name; // Name for the specific Tree, can be changed by the owner
    @PositiveOrZero
    private Long ownerId; // ownerId for now, later User entity
    @NotNull @OneToOne @JoinColumn(name = "tile_id") // Fremdschlüssel für die Tabelle
    private Tile tile;


    // Stats/Buildings
    @Positive
    private int trunk;
    @Positive
    private int bark;
    @Positive
    private int branches;
    @Positive
    private int root;

    // Resources
    @PositiveOrZero
    private int leaves;

    // Units
    @PositiveOrZero
    private int stationaryUnits;
    @PositiveOrZero
    private int movingUnits;
    @PositiveOrZero
    private int visitingUnits;


    // CONSTRUCTORS
    public Tree() {
        this("Baum", 0, 1, 1, 1, 1, 200, 0, 0, 0);
    }

    public Tree(String name, long owner,
                int trunk, int bark,
                int branches, int root,
                int leaves, int stationaryUnits,
                int movingUnits, int visitingUnits
    ) {
        this.name = name;    // Maybe give or generate random Tree names
        this.ownerId = owner;
        this.trunk = trunk;
        this.bark = bark;
        this.branches = branches;
        this.root = root;
        this.leaves = leaves;
        this.stationaryUnits = stationaryUnits;
        this.movingUnits = movingUnits;
        this.visitingUnits = visitingUnits;
    }
}