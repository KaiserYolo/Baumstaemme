package com.baumstaemme.backend.game;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class GameDto {

    private Long id;
    private String name;
    private Date created;
    @Enumerated(EnumType.STRING)
    private GameStatus status;

    private Long mapId;

    private List<Long> playerIdList;

    private int MapSize;
}
