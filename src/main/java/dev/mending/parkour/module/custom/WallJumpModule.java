package dev.mending.parkour.module.custom;

import dev.mending.parkour.Parkour;
import dev.mending.parkour.manager.ParticleManager;
import dev.mending.parkour.manager.SoundManager;
import dev.mending.parkour.manager.StateManager;
import dev.mending.parkour.module.Module;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class WallJumpModule extends Module {

    private double horizontalStrength;
    private double verticalStrength;
    private int stamina;

    public WallJumpModule(@NotNull Parkour plugin) {
        super(plugin, "wallJump");
    }

    @Override
    public void onLoad() {
        this.horizontalStrength = getConfig().getDouble("strength.horizontal");
        this.verticalStrength = getConfig().getDouble("strength.vertical");
        this.stamina = getConfig().getInt("stamina");
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {

        Player player = event.getPlayer();

        if (bypass(player)) { return; }

        if (StateManager.isOnWall(player)) {

            if (!player.isSneaking()) {

                if (!plugin.getPlayerManager().isClimbing(player) && plugin.getStaminaManager().hasEnough(player, stamina)) {

                    Vector climbVelocity = new Vector(0, 0, 0);
                    plugin.getPlayerManager().markClimbing(player);
                    player.setVelocity(climbVelocity);
                    player.setGravity(false);
                }

            } else {

                if (plugin.getPlayerManager().isClimbing(player) && plugin.getStaminaManager().hasEnough(player, stamina)) {

                    assert plugin.getPlayerManager().isClimbing(player);

                    Vector direction = player.getLocation().getDirection().normalize();
                    player.setVelocity(direction.multiply(horizontalStrength).setY(verticalStrength));
                    plugin.getStaminaManager().consume(player, stamina);

                    plugin.getPlayerManager().resetDoubleJump(player);
                    player.setAllowFlight(true);

                    ParticleManager.spawn(player, getConfig());
                    SoundManager.play(player, getConfig());
                }

                plugin.getPlayerManager().resetClimbing(player);
                player.setGravity(true);

            }
        }
    }


    @EventHandler
    public void onMove(PlayerMoveEvent e) {

        if (plugin.getPlayerManager().isClimbing(e.getPlayer())) {
            if (!StateManager.isOnWall(e.getPlayer()) || e.getPlayer().isOnGround()) {
                plugin.getPlayerManager().resetClimbing(e.getPlayer());
                e.getPlayer().setGravity(true);
            }
        }
    }

}
