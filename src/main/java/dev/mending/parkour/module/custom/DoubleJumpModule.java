package dev.mending.parkour.module.custom;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import dev.mending.parkour.Parkour;
import dev.mending.parkour.manager.ParticleManager;
import dev.mending.parkour.manager.SoundManager;
import dev.mending.parkour.module.Module;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class DoubleJumpModule extends Module implements Listener {

    private double horizontalStrength;
    private double verticalStrength;
    private int stamina;

    public DoubleJumpModule(@NotNull Parkour plugin) {
        super(plugin, "doubleJump");
    }

    @Override
    public void onLoad() {
        this.horizontalStrength = getConfig().getDouble("strength.horizontal");
        this.verticalStrength = getConfig().getDouble("strength.vertical");
        this.stamina = getConfig().getInt("stamina");
    }

    @EventHandler
    public void onJump(PlayerJumpEvent e) {

        Player player = e.getPlayer();

        if (bypass(player)) { return; }

        if (player.isOnGround()) {

            if (player.isSneaking() && plugin.getMainConfig().isEnabled("bulletJump")) {
                return;
            }

            player.setAllowFlight(true);
            plugin.getPlayerManager().resetDoubleJump(player);
        }
    }

    @EventHandler
    public void onFlight(PlayerToggleFlightEvent e) {

        Player player = e.getPlayer();

        if (bypass(player)) { return; }

        e.setCancelled(true);

        if (player.getAllowFlight() && !plugin.getPlayerManager().hasDoubleJumped(player) && !plugin.getPlayerManager().isClimbing(player)) {

            if (plugin.getStaminaManager().hasEnough(player, stamina)) {

                Vector direction = player.getLocation().getDirection().normalize();
                player.setVelocity(direction.multiply(horizontalStrength).setY(verticalStrength));

                plugin.getPlayerManager().markDoubleJump(player);
                player.setAllowFlight(false);

                plugin.getStaminaManager().consume(player, stamina);

                ParticleManager.spawn(player, getConfig());
                SoundManager.play(player, getConfig());
            }
        }
    }

}
