package dev.mending.parkour.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.io.File;

public abstract class ConfigHandler {

    protected final JavaPlugin plugin;
    private final String fileName;
    private File file;
    private FileConfiguration config;

    public ConfigHandler(@Nonnull JavaPlugin plugin, @Nonnull String fileName) {
        this.plugin = plugin;
        this.fileName = fileName + ".yml";
    }

    public void init() {
        this.file = new File(plugin.getDataFolder(), this.fileName);
        reload();
    }

    public void reload() {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource(this.fileName, false);
        }
        this.config = YamlConfiguration.loadConfiguration(file);
        onLoad();
    }

    public abstract void onLoad();

    public FileConfiguration getConfig() {
        return config;
    }
}
