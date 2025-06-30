package com.baumstaemme.backend.game.movement;

import com.baumstaemme.backend.game.tree.Tree;
import com.baumstaemme.backend.game.unit.Unit;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "movement")
@Data
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_id") // ?
    private Tree origin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_id") // ?
    private Tree target;

    @Enumerated(EnumType.STRING)
    private MovementType movementType;

    private LocalDateTime startTime;
    private LocalDateTime arrivalTime;

    @Enumerated(EnumType.STRING)
    private MovementStatus movementStatus;

    @OneToMany
    private List<MovementUnit> units;
}
