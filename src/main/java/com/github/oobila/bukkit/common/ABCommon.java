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

    public static <T> void register(T object, Plugin plugin) throws CannotFindABCoreException, ServerContextException {
        //only used for registering listeners
        Class<?> type = object.getClass();
        if (Listener.class.isAssignableFrom(type)) {
            Bukkit.getPluginManager().registerEvents((Listener) object, plugin);
            ServerContext.fromABCore().store(Listener.class, object.getClass().getName(), object);
        } else {
            ServerContext.fromABCore().store(type, object.getClass().getName(), object);
        }
    }

    public static BukkitTask runTask(Runnable task) throws CannotFindABCoreException {
        return Bukkit.getScheduler().runTask(getABCore(), task);
    }

    public static BukkitTask runTaskAsync(Runnable task) throws CannotFindABCoreException {
        return Bukkit.getScheduler().runTaskAsynchronously(getABCore(), task);
    }

    public static BukkitTask runTaskLater(Runnable task, long ticks) throws CannotFindABCoreException {
        return Bukkit.getScheduler().runTaskLater(getABCore(), task, ticks);
    }

    public static BukkitTask runContinuousTask(Runnable task, long ticks) throws CannotFindABCoreException {
        return Bukkit.getScheduler().runTaskTimer(getABCore(), task, ticks, ticks);
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

    public static void checkRequiredVersion(Plugin plugin, String requiredCoreVersion) throws CannotFindABCoreException {
        if(requiredCoreVersion != null && !requiredCoreVersion.isEmpty()){
            Plugin abCore = getABCore();
            Version coreVersion = new Version(abCore.getDescription().getVersion());
            if(coreVersion.compareTo(new Version(requiredCoreVersion)) < 0){
                log(Level.SEVERE, "This version of {0} plugin requires ABCore v{1}", plugin.getName(), requiredCoreVersion);
                Bukkit.shutdown();
            }
        }
    }

    public static void checkForUpdate(Plugin plugin, int spigotId) {
        if(spigotId != 0){
            new UpdateChecker(plugin, spigotId);
        }
    }

    public static Plugin getABCore() throws CannotFindABCoreException {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(ABCORE_PLUGIN_NAME);
        if (plugin == null) {
            throw new CannotFindABCoreException();
        }
        return plugin;
    }

}