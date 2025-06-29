package com.baumstaemme.backend.game.upgrade;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Upgrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private UpgradeType building;
    private int targetLevel;
    private int cost;

    private long duration;
    private long startTime;
    private long endTime;

    @Enumerated(EnumType.STRING)
    private UpgradeStatus status;
}
