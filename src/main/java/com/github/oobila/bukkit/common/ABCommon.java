package com.github.oobila.bukkit.common;

import com.github.oobila.bukkit.chat.Message;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.logging.Level;

@SuppressWarnings("unused")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ABCommon {

    private static final String ABCORE_PLUGIN_NAME = "ABCore";
    private static final String NAMESPACE = "ab";
    private static Plugin plugin;

    public static <T extends Listener> void register(T object, Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(object, plugin);
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

    public static void message(Message message, Player player) {
        if (player != null) {
            message.send(player);
        }
    }

    public static void message(String string, Player player) {
        if (player != null) {
            message(new Message(string), player);
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    public static NamespacedKey key(String name) {
        return new NamespacedKey(NAMESPACE, name);
    }

    @SuppressWarnings("UnstableApiUsage")
    public static NamespacedKey key(String namespace, String name) {
        return new NamespacedKey(namespace, name);
    }

    public static void checkForUpdate(Plugin plugin, int spigotId) {
        ABCommon.plugin = plugin;
        if(spigotId != 0){
            new UpdateChecker(plugin, spigotId);
        }
    }

}