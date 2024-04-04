package com.github.oobila.bukkit.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ABCommon {

    private static final Set<Listener> LISTENERS = new HashSet<>();

    public static void register(Listener listener, Plugin plugin) {
        if (!LISTENERS.contains(listener)) {
            LISTENERS.add(listener);
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
            Bukkit.getLogger().log(Level.INFO, "Registering listener {0} with plugin {1}", new String[]{listener.getClass().getSimpleName(), plugin.getName()});
        }
    }

}
