package com.baumstaemme.backend.game.unit;

import com.baumstaemme.backend.game.tree.Tree;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "unit_queue")
@Data
public class UnitQueueEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tree_id")
    private Tree tree;

    private UnitType unitType;

    private int amount;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
