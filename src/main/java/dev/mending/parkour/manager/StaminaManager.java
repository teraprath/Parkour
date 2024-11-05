package dev.mending.parkour.manager;

import dev.mending.parkour.Parkour;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StaminaManager {

    private final Parkour plugin;

    @Getter
    private final Map<UUID, BukkitTask> staminaMap = new HashMap<>();

    public StaminaManager(Parkour plugin) {
        this.plugin = plugin;
    }

    public boolean hasEnough(@Nonnull Player player, @Nonnegative int stamina) {

        if (!plugin.getMainConfig().getConfig().getBoolean("stamina.enabled")) {
            return true;
        }

        return player.getFoodLevel() >= stamina;
    }

    public void consume(@Nonnull Player player, @Nonnegative int stamina) {

        if (!plugin.getMainConfig().getConfig().getBoolean("stamina.enabled")) {
            return;
        }

        player.setFoodLevel(player.getFoodLevel() - stamina);
    }

}
