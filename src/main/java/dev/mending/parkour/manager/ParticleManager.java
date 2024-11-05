package dev.mending.parkour.manager;

import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class ParticleManager {

    public static void spawn(Player player, ConfigurationSection config) {
        if (config.getBoolean("particles.enabled")) {
            if (config.getBoolean("particles.hideForOthers")) {
                player.spawnParticle(Particle.valueOf(config.getString("particles.type")), player.getLocation(), config.getInt("particles.count"), config.getDouble("particles.offset.x"), config.getDouble("particles.offset.y"), config.getDouble("particles.offset.z"));
            } else {
                player.getLocation().getWorld().spawnParticle(Particle.valueOf(config.getString("particles.type")), player.getLocation(), config.getInt("particles.count"), config.getDouble("particles.offset.x"), config.getDouble("particles.offset.y"), config.getDouble("particles.offset.z"));
            }
        }
    }

}
