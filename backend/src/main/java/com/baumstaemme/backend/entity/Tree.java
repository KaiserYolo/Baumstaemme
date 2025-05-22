package com.baumstaemme.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
//import jakarta.persistence.ManyToOne;

@Entity
@Data
public class Tree {

    // VARIABLES

    @Id
    @GeneratedValue
    private Long id;

    private String name; // Name for the specific Tree, can be changed by the owner

    //@ManyToOne
    //private User owner; // Could/Should add an extra Entity for Users instead of an ID
    private long owner; // userId

    // Stats/Buildings
    private int trunk;
    private int bark;
    private int branches;
    private int root;
    //@OneToMany
    //private List<Buildings> buildings;

    // Resources
    private int leaves;

    // Units
    private int stationaryUnits;
    private int movingUnits;
    private int visitingUnits;
    //@OneToMany
    //private List<Unit> units;


    // CONSTRUCTORS

    public Tree() {
        this(0);
    }

    public Tree(long owner) {
        this("Herbert", owner, 1, 0, 0, 0, 100, 0, 0, 0);
    }

    public Tree(String name, long owner,
                int trunk, int bark,
                int branches, int root,
                int leaves, int stationaryUnits,
                int movingUnits, int visitingUnits
    ) {
        this.name = name;    // Maybe give or generate random Tree names
        this.owner = owner;
        this.trunk = trunk;
        this.bark = bark;
        this.branches = branches;
        this.root = root;
        this.leaves = leaves;
        this.stationaryUnits = stationaryUnits;
        this.movingUnits = movingUnits;
        this.visitingUnits = visitingUnits;
    }


    // GETTER & SETTER

    public void setTrunk(int trunk) {
        if (trunk < 0) {
            throw new IllegalArgumentException("Trunk must be a positive number");
        } else {
            this.trunk = trunk;
        }
    }

    public void setBark(int bark) {
        if (bark < 0) {
            throw new IllegalArgumentException("Bark must be a positive number");
        } else {
            this.bark = bark;
        }
    }

    public void setBranches(int branches) {
        if (branches < 0) {
            throw new IllegalArgumentException("Branches must be a positive number");
        } else {
            this.branches = branches;
        }
    }

    public void setRoot(int root) {
        if (root < 0) {
            throw new IllegalArgumentException("Root must be a positive integer");
        } else {
            this.root = root;
        }
    }

    public void setLeaves(int leaves) {
        if (leaves < 0) {
            throw new IllegalArgumentException("Leaves must be a positive integer");
            //this.leaves = 0;
        } else {
            this.leaves = leaves;
        }
    }

    public void setStationaryUnits(int stationaryUnits) {
        if (stationaryUnits < 0) {
            throw new IllegalArgumentException("StationaryUnits must be a positive integer");
            //this.stationaryUnits = 0;
        } else {
            this.stationaryUnits = stationaryUnits;
        }
    }

    public void setMovingUnits(int movingUnits) {
        if (movingUnits < 0) {
            throw new IllegalArgumentException("MovingUnits must be a positive integer");
        } else {
            this.movingUnits = movingUnits;
        }
    }

    public void setVisitingUnits(int visitingUnits) {
        if (visitingUnits < 0) {
            throw new IllegalArgumentException("VisitingUnits must be a positive integer");
        } else {
            this.visitingUnits = visitingUnits;
        }
    }


    // METHODS

    public void updateResources() {
        //TODO
    }

    public void trainUnits() {
        //TODO
    }

    public void defendAttack(int incomingUnits) {
        //TODO
    }
}
