package dev.mending.parkour.task;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

public class StaminaTask extends BukkitRunnable {

    private final Player player;
    private final int regenerationDelay;
    private final int regenerationAmount;
    private int lastStamina;
    private int delay;

    public StaminaTask(@Nonnull Player player, @Nonnegative int regenerationDelay, @Nonnegative int regenerationAmount) {
        this.player = player;
        this.lastStamina = player.getFoodLevel();
        this.regenerationDelay = regenerationDelay;
        this.regenerationAmount = regenerationAmount;
        this.delay = regenerationDelay;
    }

    @Override
    public void run() {
        if (player.getFoodLevel() < lastStamina) {
            delay = regenerationDelay;
            lastStamina = player.getFoodLevel();
        } else {
            delay--;
            if (delay <= 0) {
                regenerate();
                delay = 0;
            }
        }
    }

    private void regenerate() {
        if (player.getFoodLevel() < 20) {
            player.setFoodLevel(player.getFoodLevel() + regenerationAmount);
            lastStamina = player.getFoodLevel();
        }
    }
}

