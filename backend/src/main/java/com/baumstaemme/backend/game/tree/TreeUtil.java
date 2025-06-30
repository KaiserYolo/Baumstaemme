package com.baumstaemme.backend.game.tree;

import com.baumstaemme.backend.game.upgrade.Upgrade;
import com.baumstaemme.backend.game.upgrade.UpgradeDto;
import com.baumstaemme.backend.game.upgrade.UpgradeType;
import com.baumstaemme.backend.game.upgrade.UpgradeUtil;

import java.util.ArrayList;
import java.util.List;

public class TreeUtil {


    public static List<UpgradeDto> createUpgradeInfoDto(Tree tree) { //TODO: Funktion ist echt schlecht, sollte generell ein Building Entity erstellen
        if (tree == null) {
            return null;
        }

        Upgrade upgrade = tree.getUpgrade();
        if (upgrade != null) {
            tree.addBuildingLevel(upgrade.getBuilding());
        }

        List<UpgradeDto> upgradeInfo = new ArrayList<>();

        UpgradeDto trunkUpgradeInfo = new UpgradeDto();
        trunkUpgradeInfo.setBuilding(UpgradeType.TRUNK);
        int targetLevel = tree.getTrunk() + 1;
        trunkUpgradeInfo.setTargetLevel(targetLevel);
        trunkUpgradeInfo.setCost(UpgradeType.TRUNK.getCost(targetLevel));
        trunkUpgradeInfo.setDuration(UpgradeType.TRUNK.getDuration(targetLevel));
        upgradeInfo.add(trunkUpgradeInfo);

        UpgradeDto barkUpgradeInfo = new UpgradeDto();
        barkUpgradeInfo.setBuilding(UpgradeType.BARK);
        targetLevel = tree.getBark() + 1;
        barkUpgradeInfo.setTargetLevel(targetLevel);
        barkUpgradeInfo.setCost(UpgradeType.BARK.getCost(targetLevel));
        barkUpgradeInfo.setDuration(UpgradeType.BARK.getDuration(targetLevel));
        upgradeInfo.add(barkUpgradeInfo);

        UpgradeDto branchesUpgradeInfo = new UpgradeDto();
        branchesUpgradeInfo.setBuilding(UpgradeType.BRANCHES);
        targetLevel = tree.getBranches() + 1;
        branchesUpgradeInfo.setTargetLevel(targetLevel);
        branchesUpgradeInfo.setCost(UpgradeType.BRANCHES.getCost(targetLevel));
        branchesUpgradeInfo.setDuration(UpgradeType.BRANCHES.getDuration(targetLevel));
        upgradeInfo.add(branchesUpgradeInfo);

        UpgradeDto rootUpgradeInfo = new UpgradeDto();
        rootUpgradeInfo.setBuilding(UpgradeType.ROOT);
        targetLevel = tree.getRoot() + 1;
        rootUpgradeInfo.setTargetLevel(targetLevel);
        rootUpgradeInfo.setCost(UpgradeType.ROOT.getCost(targetLevel));
        rootUpgradeInfo.setDuration(UpgradeType.ROOT.getDuration(targetLevel));
        upgradeInfo.add(rootUpgradeInfo);

        return upgradeInfo;
    }


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
        treeDto.setDtoType(TreeDto.DtoType.PUBLIC);

        if (playerId != null && playerId.equals(ownerId)) {
            treeDto.setDtoType(TreeDto.DtoType.PRIVATE);
            treeDto.setLeaves(tree.getLeaves());
            treeDto.setLeafProduction(tree.getLeavesProduction());
            treeDto.setTrunk(tree.getTrunk());
            treeDto.setBark(tree.getBark());
            treeDto.setBranches(tree.getBranches());
            treeDto.setRoot(tree.getRoot());

            treeDto.setUpgradeInfo(createUpgradeInfoDto(tree));

            Upgrade upgrade = tree.getUpgrade();
            if (upgrade != null) {
                UpgradeDto upgradeDto = UpgradeUtil.createResponseDto(upgrade);
                treeDto.setUpgrade(upgradeDto);
            }
        }
        return treeDto;
    }
}
