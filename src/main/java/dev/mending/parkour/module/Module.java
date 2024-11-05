package dev.mending.parkour.module;


import dev.mending.parkour.Parkour;
import org.bukkit.GameMode;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import javax.annotation.Nonnull;

public abstract class Module implements Listener {

    protected final Parkour plugin;
    private final String id;
    private final String permission;

    public Module(@Nonnull Parkour plugin, @Nonnull String id) {
        this.plugin = plugin;
        this.id = id;
        this.permission = plugin.getMainConfig().getConfig().getString("modules." + id + ".permission");
    }

    public void register() {
        if (plugin.getMainConfig().isEnabled(id)) {
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
            plugin.getLogger().info("Module [" + id + "] enabled");
            onLoad();
        }
    }

    public ConfigurationSection getConfig() {
        return plugin.getMainConfig().getConfig().getConfigurationSection("modules." + id);
    }

    public boolean bypass(@Nonnull Player player) {
        return player.getGameMode().equals(GameMode.CREATIVE) || !player.hasPermission(permission) || plugin.getMainConfig().getDisabledWorlds().contains(player.getWorld().getName());
    }

    public abstract void onLoad();
}
