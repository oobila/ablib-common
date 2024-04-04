package com.github.oobila.bukkit.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ABCommon {

    public static Plugin getHost(Plugin plugin) {
        return ABCommonHost.getHost(plugin);
    }

    public static void register(CommonListener listener, Plugin plugin) {
        Plugin host = ABCommonHost.getHost(plugin);
        Bukkit.getServer().getPluginManager().registerEvents(listener, host);
    }


}
