package dev.mending.parkour.module.custom;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import dev.mending.parkour.Parkour;
import dev.mending.parkour.manager.ParticleManager;
import dev.mending.parkour.manager.SoundManager;
import dev.mending.parkour.module.Module;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class BulletJumpModule extends Module {

    private double horizontalStrength;
    private double verticalStrength;
    private int stamina;

    public BulletJumpModule(@NotNull Parkour plugin) {
        super(plugin, "bulletJump");

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

        if (player.isSneaking() && player.isOnGround()) {

            if (plugin.getStaminaManager().hasEnough(player, stamina)) {

                Vector direction = player.getLocation().getDirection().normalize();
                player.setVelocity(direction.multiply(horizontalStrength).setY(verticalStrength));
                plugin.getStaminaManager().consume(player, stamina);

                if (plugin.getMainConfig().isEnabled("doubleJump")) {
                    plugin.getPlayerManager().resetDoubleJump(player);
                    player.setAllowFlight(true);
                }

                ParticleManager.spawn(player, getConfig());
                SoundManager.play(player, getConfig());
            }
        }
    }


}
