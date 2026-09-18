package com.github.oobila.bukkit.common;

import com.github.oobila.bukkit.chat.Message;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;
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

    /**
     * Registers {@code object} unless a listener of the same (simple) class name is already
     * registered for {@code representativeEvent}. Useful when several plugins shade the same
     * library listener and each call {@code register}/{@code registerOnce} independently: without
     * this, Bukkit would invoke that listener's handlers once per plugin that registered it. Only
     * one event type needs to be checked, since a listener's handlers are all registered together
     * in a single {@link Bukkit#getPluginManager()}{@code .registerEvents(...)} call. Matching is
     * done by simple class name (rather than {@code Class} identity) because each plugin loads its
     * own shaded copy of the listener class in its own classloader.
     */
    public static <T extends Listener> void registerOnce(T object, Class<? extends Event> representativeEvent, Plugin plugin) {
        if (isAlreadyRegistered(object.getClass(), representativeEvent)) {
            log(Level.INFO, "skipping registration of {} - already registered by another plugin", object.getClass().getSimpleName());
            return;
        }
        register(object, plugin);
    }
    
    // Deliberately a name comparison, not isAssignableFrom/instanceof: each plugin shades its own
    // copy of the listener class into its own classloader, so the "same" class is a different
    // Class object per plugin and would never be assignable to another plugin's copy.
    @SuppressWarnings("java:S1872")
    private static boolean isAlreadyRegistered(Class<? extends Listener> listenerType, Class<? extends Event> representativeEvent) {
        try {
            HandlerList handlerList = (HandlerList) representativeEvent.getMethod("getHandlerList").invoke(null);
            for (RegisteredListener registeredListener : handlerList.getRegisteredListeners()) {
                if (registeredListener.getListener().getClass().getSimpleName().equals(listenerType.getSimpleName())) {
                    return true;
                }
            }
        } catch (ReflectiveOperationException e) {
            log(Level.WARNING, "failed to check for existing registrations of {}", listenerType.getSimpleName());
            log(Level.WARNING, e);
        }
        return false;
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