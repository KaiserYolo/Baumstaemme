package com.baumstaemme.backend.game.player;

import com.baumstaemme.backend.game.tree.Tree;

import java.util.ArrayList;
import java.util.List;

public class PlayerUtil {

    public static PlayerDto createResponseDto(Player player) {
        if (player == null) {
            return null;
        }
        PlayerDto playerDto = new PlayerDto();
        playerDto.setId(player.getId());
        playerDto.setName(player.getUser().getUsername());
        playerDto.setOwnerId(player.getUser().getId());

        List<Tree> trees = player.getTrees();
        List<Long> treeIdList = new ArrayList<>();
        for (Tree tree : trees) {
            treeIdList.add(tree.getId());
        }
        playerDto.setTreeIdList(treeIdList);

        return playerDto;
    }
}
