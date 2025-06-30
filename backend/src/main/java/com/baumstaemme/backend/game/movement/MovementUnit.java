package com.baumstaemme.backend.game.movement;

import com.baumstaemme.backend.game.unit.UnitType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "movement_units")
@Data
public class MovementUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movement_id")
    private Movement movement;

    private UnitType unitType;

    private int count;
}
