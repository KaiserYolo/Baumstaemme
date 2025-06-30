package com.baumstaemme.backend.game.report;

import com.baumstaemme.backend.game.player.Player;
import com.baumstaemme.backend.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
@Data
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Player player;

    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    private String title;

    private String content;

    private Boolean read = false;

    private LocalDateTime createdAt;
}
