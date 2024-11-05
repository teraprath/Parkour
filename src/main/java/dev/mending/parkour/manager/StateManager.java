package dev.mending.parkour.manager;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class StateManager {

    public static boolean isOnWall(Player player) {

        if (player.isOnGround()) {
            return false;
        }

        Block playerBlock = player.getLocation().getBlock();
        Block[] adjacentBlocks = {
                playerBlock.getRelative(1, 0, 0),
                playerBlock.getRelative(-1, 0, 0),
                playerBlock.getRelative(0, 0, 1),
                playerBlock.getRelative(0, 0, -1)
        };

        for (Block block : adjacentBlocks) {
            if (block.getType().isSolid()) {
                return true;
            }
        }
        return false;
    }

}
