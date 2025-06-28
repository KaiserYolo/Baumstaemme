package com.baumstaemme.backend.game.tree;

import com.baumstaemme.backend.game.player.PlayerConverter;

public class TreeConverter {

    public static TreeDto toDto(Tree tree) {
        if (tree == null) {
            return null;
        }
        TreeDto treeDto = new TreeDto();
        treeDto.setId(tree.getId());
        treeDto.setName(tree.getName());
        treeDto.setOwner(PlayerConverter.toDto(tree.getOwner()));
        treeDto.setLeaves(tree.getLeaves());
        treeDto.setLeavesProduction(tree.getLeavesProduction());
        return treeDto;
    }
}
