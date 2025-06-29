package com.baumstaemme.backend.game.upgrade;

import java.util.ArrayList;
import java.util.List;

public class UpgradeUtil {

    public static UpgradeDto createResponseDto(Upgrade upgrade) {
        if (upgrade == null) {
            return null;
        }
        UpgradeDto upgradeDto = new UpgradeDto();
        upgradeDto.setId(upgrade.getId());
        upgradeDto.setBuilding(upgrade.getBuilding());
        upgradeDto.setTargetLevel(upgrade.getTargetLevel());
        upgradeDto.setDuration(upgrade.getDuration());
        upgradeDto.setStartTime(upgrade.getStartTime());
        upgradeDto.setEndTime(upgrade.getEndTime());
        upgradeDto.setStatus(upgrade.getStatus());
        return upgradeDto;
    }

    public static List<UpgradeDto> createResponseDto(List<Upgrade> upgrades) {
        List<UpgradeDto> upgradeDtos = new ArrayList<>();
        for (Upgrade upgrade : upgrades) {
            upgradeDtos.add(createResponseDto(upgrade));
        }
        return upgradeDtos;
    }
}
