package dev.mending.parkour.manager;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

public class PlayerManager {


    private final HashSet<UUID> doubleJumpedPlayers;
    private final HashSet<UUID> climbingPlayers;

    public PlayerManager() {
        this.doubleJumpedPlayers = new HashSet<>();
        this.climbingPlayers = new HashSet<>();
    }

    public void markDoubleJump(Player player) {
        doubleJumpedPlayers.add(player.getUniqueId());
    }

    public void resetDoubleJump(Player player) {
        doubleJumpedPlayers.remove(player.getUniqueId());
    }

    public boolean hasDoubleJumped(Player player) {
        return doubleJumpedPlayers.contains(player.getUniqueId());
    }

    public void markClimbing(Player player) {
        climbingPlayers.add(player.getUniqueId());
    }

    public void resetClimbing(Player player) {
        climbingPlayers.remove(player.getUniqueId());
    }

    public boolean isClimbing(Player player) {
        return climbingPlayers.contains(player.getUniqueId());
    }

}
