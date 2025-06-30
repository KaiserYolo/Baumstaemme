package com.baumstaemme.backend.game.unit;


import com.baumstaemme.backend.game.tree.Tree;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "units")
@Data
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tree_id")
    private Tree tree;

    private UnitType unitType;

    private int count = 0;

}
