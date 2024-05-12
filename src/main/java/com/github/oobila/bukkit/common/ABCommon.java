package com.github.oobila.bukkit.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.logging.Level;

@SuppressWarnings("unused")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ABCommon {

    private static final String ABCORE_PLUGIN_NAME = "ABCore";
    private static final String NAMESPACE = "ab_common";
    private static Plugin plugin;

    public static <T> void register(T object, Plugin plugin) {
        //only used for registering listeners
        Class<?> type = object.getClass();
        if (Listener.class.isAssignableFrom(type)) {
            Bukkit.getPluginManager().registerEvents((Listener) object, plugin);
        }
    }

    public static BukkitTask runTask(Runnable task) {
        return Bukkit.getScheduler().runTask(plugin, task);
    }

    public static BukkitTask runTaskAsync(Runnable task) {
        return Bukkit.getScheduler().runTaskAsynchronously(plugin, task);
    }

    public static BukkitTask runTaskLater(Runnable task, long ticks) {
        return Bukkit.getScheduler().runTaskLater(plugin, task, ticks);
    }

    public static BukkitTask runContinuousTask(Runnable task, long ticks) {
        return Bukkit.getScheduler().runTaskTimer(plugin, task, ticks, ticks);
    }

    public static void cancelTask(int taskId) {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    public static void log(Level level, String message, Object... params) {
        Bukkit.getLogger().log(level, message, params);
    }

    public static void log(Level level, Throwable t) {
        Bukkit.getLogger().log(level, t.getMessage(), t);
    }

    @SuppressWarnings("UnstableApiUsage")
    public static NamespacedKey key(String name) {
        return new NamespacedKey(NAMESPACE, name);
    }

    public static void checkForUpdate(Plugin plugin, int spigotId) {
        ABCommon.plugin = plugin;
        if(spigotId != 0){
            new UpdateChecker(plugin, spigotId);
        }
    }

}