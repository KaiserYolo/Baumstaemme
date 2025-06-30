package com.baumstaemme.backend.game.tree;

import com.baumstaemme.backend.game.player.Player;
import com.baumstaemme.backend.game.upgrade.Upgrade;
import com.baumstaemme.backend.game.upgrade.UpgradeType;
import jakarta.persistence.*;
import lombok.Data;

import java.awt.*;

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


    // Stats/Buildings
    private int trunk = 0;
    private int bark = 0;
    private int branches = 0;
    private int root = 0;

    //@OneToMany(cascade = CascadeType.ALL)
    //private List<Upgrade> upgradeQueue;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Upgrade upgrade;


    // Units
    //private int stationaryUnits;
    //private int movingUnits;
    //private int visitingUnits;


    public int getBuildingLevel(UpgradeType building) {
        switch (building) {
            case TRUNK -> {
                return trunk;
            }
            case BARK -> {
                return bark;
            }
            case BRANCHES -> {
                return  branches;
            }
            case ROOT -> {
                return root;
            }
            default -> { // meh
                return 0;
            }
        }
    }

    public void addBuildingLevel(UpgradeType building) {
        switch (building) {
            case TRUNK -> trunk++;
            case BARK -> bark++;
            case BRANCHES -> branches++;
            case ROOT -> root++;
        }
    }
}