package dev.mending.parkour.listener;

import dev.mending.parkour.Parkour;
import dev.mending.parkour.task.StaminaTask;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StaminaListener implements Listener {

    private final Parkour plugin;
    private final Map<UUID, BukkitTask> taskMap = new HashMap<>();

    public StaminaListener(Parkour plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        if (plugin.getMainConfig().getConfig().getBoolean("stamina.disableHunger")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        this.taskMap.put(player.getUniqueId(), new StaminaTask(player, plugin.getMainConfig().getConfig().getInt("stamina.regeneration.delay") * 2, plugin.getMainConfig().getConfig().getInt("stamina.regeneration.amount")).runTaskTimerAsynchronously(plugin, 0L, 10));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        final Player player = e.getPlayer();
        this.taskMap.remove(player.getUniqueId()).cancel();
    }

}
