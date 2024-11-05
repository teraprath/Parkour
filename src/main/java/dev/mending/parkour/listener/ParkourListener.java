package dev.mending.parkour.listener;

import dev.mending.parkour.Parkour;
import dev.mending.parkour.task.StaminaTask;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ParkourListener implements Listener {

    private final Parkour plugin;

    public ParkourListener(Parkour plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        final Player player = e.getPlayer();

        if (staminaEnabled()) {
            plugin.getStaminaManager().getStaminaMap().put(player.getUniqueId(), new StaminaTask(player, plugin.getMainConfig().getConfig().getInt("stamina.regeneration.delay") * 2, plugin.getMainConfig().getConfig().getInt("stamina.regeneration.amount")).runTaskTimerAsynchronously(plugin, 0L, 10));
        }

        if (player.hasPermission("*") || player.isOp()) {
            if (plugin.getMainConfig().isCheckForUpdates()) {
                plugin.getUpdateChecker().checkForUpdate(player);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {

        final Player player = e.getPlayer();

        if (staminaEnabled()) {
            plugin.getStaminaManager().getStaminaMap().remove(player.getUniqueId()).cancel();
        }
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
                if (staminaEnabled()) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        if (staminaEnabled() && plugin.getMainConfig().getConfig().getBoolean("stamina.disableHunger")) {
            e.setCancelled(true);
        }
    }

    private boolean staminaEnabled() {
        return plugin.getMainConfig().getConfig().getBoolean("stamina.enabled");
    }

}
