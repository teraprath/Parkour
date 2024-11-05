package dev.mending.parkour.manager;

import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class SoundManager {

    public static void play(Player player, ConfigurationSection config) {
        if (config.getBoolean("sound.enabled")) {
            player.playSound(player.getLocation(), Sound.valueOf(config.getString("sound.type")), (float) config.getDouble("sound.volume"), (float) config.getDouble("sound.pitch"));
        }
    }

}
