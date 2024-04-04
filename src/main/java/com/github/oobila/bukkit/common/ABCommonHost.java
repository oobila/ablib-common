package com.github.oobila.bukkit.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Synchronized;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class ABCommonHost implements Listener {

    private static Plugin host;
    private static final Set<Plugin> hostCandidates = new HashSet<>();

    @Synchronized
    static Plugin getHost(Plugin pl) {
        hostCandidates.add(pl);

        //look in cache
        if (host != null) {
            return host;
        }

        //search other plugins for host
        for (Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins()) {
            for (RegisteredListener registeredListener : HandlerList.getRegisteredListeners(plugin)) {
                if (registeredListener.getListener().getClass().equals(ABCommonHost.class)) {
                    host = plugin;
                    return host;
                }
            }
        }

        //assume the role of host
        host = pl;
        Bukkit.getServer().getPluginManager().registerEvents(new ABCommonHost(), pl);
        Bukkit.getLogger().log(Level.INFO, "Plugin [{0}] is assuming the role of ABCommon host", pl.getName());
        return pl;
    }

    @Synchronized
    static void removeCandidate(Plugin plugin) {
        if (hostCandidates.contains(plugin)) {
            hostCandidates.remove(plugin);
            if (getHost(plugin).equals(plugin) && !hostCandidates.isEmpty()) {
                migrateHost(plugin, hostCandidates.iterator().next());
            }
        }
    }

    @Synchronized
    static void migrateHost(Plugin fromPlugin, Plugin toPlugin) {
        //TODO
    }

}
