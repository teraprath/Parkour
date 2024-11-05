package dev.mending.parkour.manager;

import dev.mending.parkour.Parkour;
import org.bukkit.entity.Player;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

public class StaminaManager {

    private final Parkour plugin;

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
