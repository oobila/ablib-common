package com.github.oobila.bukkit.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

import static com.github.oobila.bukkit.common.ABCommon.log;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TeleportUtil {

    public static void teleportWithRetry(Plugin plugin, Player player, Location location) {
        teleportWithRetry(plugin, player, location, 5);
    }

    private static void teleportWithRetry(Plugin plugin, Player player, Location location, int retry) {
        if (retry == 0) {
            log(Level.SEVERE, "could not teleport player: {0}", player.getName());
        } else {
            final int newRetry = retry - 1;
            player.teleport(location);
            Bukkit.getScheduler().scheduleSyncDelayedTask(
                    plugin,
                    () -> {
                        if (
                                player.getWorld().getUID() != location.getWorld().getUID() ||
                                        player.getLocation().distance(location) > 2
                        ) {
                            teleportWithRetry(plugin, player, location, newRetry);
                        }
                    },
                    2
            );
        }
    }

}
