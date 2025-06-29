package com.baumstaemme.backend.game.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeUtil {

    public static List<TreeDto> createResponseDto(List<Tree> trees, Long playerId) {
        List<TreeDto> treeDtoList = new ArrayList<>();
        for(Tree tree : trees){
            treeDtoList.add(createResponseDto(tree, playerId));
        }
        return treeDtoList;
    }

    public static TreeDto createResponseDto(Tree tree, Long playerId) {
        if (tree == null) {
            return null;
        }
        TreeDto treeDto = new TreeDto();
        treeDto.setId(tree.getId());
        treeDto.setName(tree.getName());
        treeDto.setPosition(tree.getPosition());

        Long ownerId = null;

        if (tree.getOwner() != null) {
            ownerId = tree.getOwner().getId();
            treeDto.setOwnerId(ownerId);
            treeDto.setOwnerName(tree.getOwner().getUser().getUsername());
        }

        if (playerId != null && playerId.equals(ownerId)) {
            treeDto.setLeaves(tree.getLeaves());
            treeDto.setLeafProduction(tree.getLeavesProduction());
            treeDto.setTrunk(tree.getTrunk());
            treeDto.setBark(tree.getBark());
            treeDto.setBranches(tree.getBranches());
            treeDto.setRoot(tree.getRoot());
        }
        return treeDto;
    }
}
