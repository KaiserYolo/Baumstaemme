package com.baumstaemme.backend.game.tree;

import com.baumstaemme.backend.game.player.Player;
import com.baumstaemme.backend.game.upgrade.Upgrade;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.awt.*;
import java.util.List;

@Entity
@Table(name = "TREES")
@Data
public class Tree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Maybe Sequence
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Player owner;

    private Point position;

    // Resources
    private int leaves = 1000;
    private int leavesProduction = 10;
    //private long lastProducedTimestamp = System.currentTimeMillis();


    // Stats/Buildings
    private int trunk = 0;
    private int bark = 0;
    private int branches = 0;
    private int root = 0;

    @OneToMany(mappedBy = "tree", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Upgrade> upgradeQueue;


    // Units
    //private int stationaryUnits;
    //private int movingUnits;
    //private int visitingUnits;
}