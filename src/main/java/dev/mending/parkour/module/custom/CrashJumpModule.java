package dev.mending.parkour.module.custom;

import dev.mending.parkour.Parkour;
import dev.mending.parkour.manager.ParticleManager;
import dev.mending.parkour.manager.SoundManager;
import dev.mending.parkour.manager.StateManager;
import dev.mending.parkour.module.Module;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class CrashJumpModule extends Module {

    private double strength;
    private int stamina;

    public CrashJumpModule(@NotNull Parkour plugin) {
        super(plugin, "crashJump");
    }

    @Override
    public void onLoad() {
        this.strength = getConfig().getDouble("strength");
        this.stamina = getConfig().getInt("stamina");
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {

        Player player = e.getPlayer();

        if (bypass(player)) { return; }

        if (plugin.getPlayerManager().hasDoubleJumped(player) && !player.isOnGround()) {

            if (plugin.getMainConfig().isEnabled("wallJump")) {
                if (StateManager.isOnWall(player)) {
                    return;
                }
            }

            if (plugin.getStaminaManager().hasEnough(player, stamina)) {
                Vector crashVelocity = new Vector(0, -strength, 0);
                player.setVelocity(crashVelocity);

                plugin.getPlayerManager().resetDoubleJump(player);
                plugin.getStaminaManager().consume(player, stamina);

                ParticleManager.spawn(player, getConfig());
                SoundManager.play(player, getConfig());
            }
        }
    }

}
