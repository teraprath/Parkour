package dev.mending.parkour;

import dev.mending.parkour.config.MainConfig;
import dev.mending.parkour.listener.ParkourListener;
import dev.mending.parkour.manager.PlayerManager;
import dev.mending.parkour.manager.StaminaManager;
import dev.mending.parkour.module.custom.BulletJumpModule;
import dev.mending.parkour.module.custom.CrashJumpModule;
import dev.mending.parkour.module.custom.DoubleJumpModule;
import dev.mending.parkour.module.custom.WallJumpModule;
import dev.mending.parkour.utils.UpdateChecker;
import lombok.Getter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Parkour extends JavaPlugin {

    private final UpdateChecker updateChecker = new UpdateChecker(this, "teraprath", "Parkour");
    private final MainConfig mainConfig = new MainConfig(this);
    private final PlayerManager playerManager = new PlayerManager();
    private final StaminaManager staminaManager = new StaminaManager(this);


    @Override
    public void onEnable() {

        final PluginManager pluginManager = getServer().getPluginManager();

        mainConfig.init();

        pluginManager.registerEvents(new ParkourListener(this), this);
        registerModules();

        if (mainConfig.isCheckForUpdates()) {
            updateChecker.checkForUpdate();
        }
    }

    private void registerModules() {
        new DoubleJumpModule(this).register();
        new CrashJumpModule(this).register();
        new BulletJumpModule(this).register();
        new WallJumpModule(this).register();
    }

}
