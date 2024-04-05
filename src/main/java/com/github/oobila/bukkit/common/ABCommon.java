package com.github.oobila.bukkit.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

import java.util.logging.Level;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ABCommon {

    public static void register(Listener listener, Plugin plugin) {
        for (Plugin p : Bukkit.getServer().getPluginManager().getPlugins()) {
            for (RegisteredListener registeredListener : HandlerList.getRegisteredListeners(p)) {
                if (registeredListener.getListener().getClass().equals(listener.getClass())) {
                    Bukkit.getLogger().log(Level.INFO, "Not Registering listener {0} with plugin {1} as it has already been registered",
                            new String[]{listener.getClass().getSimpleName(), plugin.getName()});
                    return;
                }
            }
        }

        Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        Bukkit.getLogger().log(Level.INFO, "Registering listener {0} with plugin {1}", new String[]{listener.getClass().getSimpleName(), plugin.getName()});
    }

}
