package dev.mending.parkour.listener;

import dev.mending.parkour.Parkour;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

    private final Parkour plugin;

    public DamageListener(Parkour plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {

        if (e.getEntity() instanceof Player player) {

            if (plugin.getMainConfig().getDisabledWorlds().contains(player.getWorld().getName())) {
                return;
            }

            if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                e.setCancelled(true);
            }

            if (e.getCause().equals(EntityDamageEvent.DamageCause.STARVATION)) {
                if (plugin.getMainConfig().getConfig().getBoolean("stamina.enabled")) {
                    e.setCancelled(true);
                }
            }
        }
    }

}
