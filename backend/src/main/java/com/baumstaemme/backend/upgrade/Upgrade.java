package com.baumstaemme.backend.upgrade;


import com.baumstaemme.backend.tree.Tree;
import com.fasterxml.jackson.annotation.JsonBackReference;
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

    private long duration;
    private long startTime;
    private long endTime;

    @Enumerated(EnumType.STRING)
    private UpgradeStatus status;

    @ManyToOne//(cascade = CascadeType.ALL)
    @JoinColumn(name = "village_id")
    @JsonBackReference
    private Tree tree;


}
