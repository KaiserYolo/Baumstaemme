package com.baumstaemme.backend.game.tree;

import com.baumstaemme.backend.game.player.PlayerUtil;

public class TreeUtil {

    public static TreeDto createResponseDto(Tree tree, Long playerId) {
        if (tree == null) {
            return null;
        }
        TreeDto treeDto = new TreeDto();
        treeDto.setId(tree.getId());
        treeDto.setName(tree.getName());
        treeDto.setOwner(PlayerUtil.createResponseDto(tree.getOwner()));
        treeDto.setLeaves(tree.getLeaves());                                    //TODO: Wenn man die map will net kompletten Baum übergeben
        treeDto.setLeavesProduction(tree.getLeavesProduction());
        return treeDto;
    }
}
