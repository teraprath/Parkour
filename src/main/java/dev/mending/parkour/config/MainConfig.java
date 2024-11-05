package dev.mending.parkour.config;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MainConfig extends ConfigHandler {

    private final List<String> disabledWorlds;

    public MainConfig(@NotNull JavaPlugin plugin) {
        super(plugin, "config");
        this.disabledWorlds = new ArrayList<>();
    }

    @Override
    public void onLoad() {

        this.disabledWorlds.clear();
        this.disabledWorlds.addAll(getConfig().getStringList("disabledWorlds"));

        plugin.getLogger().info("Configuration has been loaded!");
    }

    public boolean isEnabled(String moduleId) {
        return getConfig().getBoolean("modules." + moduleId + ".enabled");
    }

}
