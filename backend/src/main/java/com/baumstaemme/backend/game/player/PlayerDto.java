package com.baumstaemme.backend.game.player;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PlayerDto {

    private Long id;
    private String name;

    private Long ownerId;

    private List<Long> treeIdList;
}
