package com.github.oobila.bukkit.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.logging.Level;

@SuppressWarnings("unused")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ABCommon {

    private static final String ABCORE_PLUGIN_NAME = "ABCore";
    private static final String GET_SERVER_CONTEXT_METHOD_NAME = "getServerContext";

    public static <T> void store(Class<?> type, String name, T object) throws CannotFindABCoreException, ServerContextException {
        getABCoreServerContext().store(type, name, object);
    }

    public static <T> void store(String name, T object) throws CannotFindABCoreException, ServerContextException {
        getABCoreServerContext().store(name, object);
    }

    public static <T> T get(Class<T> type, String name) throws CannotFindABCoreException {
        return getABCoreServerContext().get(type, name);
    }

    public static <T> T remove(Class<T> type, String name) throws CannotFindABCoreException {
        return getABCoreServerContext().remove(type, name);
    }

    public static <T> Collection<T> get(Class<T> type) throws CannotFindABCoreException {
        return getABCoreServerContext().get(type);
    }

    public static <T> void register(T object, Plugin plugin) throws CannotFindABCoreException, ServerContextException {
        //only used for registering listeners
        Class<?> type = object.getClass();
        if (Listener.class.isAssignableFrom(type)) {
            Bukkit.getPluginManager().registerEvents((Listener) object, plugin);
            store(Listener.class, object.getClass().getName(), object);
        } else {
            store(type, object.getClass().getName(), object);
        }
    }

    public static void log(Level level, String message, Object... params) {
        Bukkit.getLogger().log(level, message, params);
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

    public void checkForUpdate(Plugin plugin, int spigotId) {
        if(spigotId != 0){
            new UpdateChecker(plugin, spigotId);
        }
    }

    private static ServerContext getABCoreServerContext() throws CannotFindABCoreException {
        try {
            Plugin plugin = getABCore();
            return (ServerContext) plugin.getClass().getMethod(GET_SERVER_CONTEXT_METHOD_NAME).invoke(plugin);
        } catch (Exception e) {
            throw new CannotFindABCoreException(e);
        }
    }

    private static Plugin getABCore() throws CannotFindABCoreException {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(ABCORE_PLUGIN_NAME);
        if (plugin == null) {
            throw new CannotFindABCoreException();
        }
        return plugin;
    }

}