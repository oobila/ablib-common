package com.github.oobila.bukkit.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.Collection;

@SuppressWarnings("unused")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ABCommon {

    private static final String ABCORE_PLUGIN_NAME = "ABCore";
    private static final String GET_SERVER_CONTEXT_METHOD_NAME = "getServerContext";

    public static <T> void store(Class<?> type, String name, T object) throws CannotFindABCoreException {
        getABCoreServerContext().store(type, name, object);
    }

    public static <T> void store(String name, T object) throws CannotFindABCoreException {
        getABCoreServerContext().store(name, object);
    }

    public static <T> T get(Class<T> type, String name) throws CannotFindABCoreException {
        return getABCoreServerContext().get(type, name);
    }

    public static <T> Collection<T> get(Class<T> type) throws CannotFindABCoreException {
        return getABCoreServerContext().get(type);
    }

    public static <T> void register(Plugin plugin, T object) throws CannotFindABCoreException {
        //only used for registering listeners
        Class<?> type = object.getClass();
        if (Listener.class.isAssignableFrom(type)) {
            Bukkit.getPluginManager().registerEvents((Listener) object, plugin);
            store(Listener.class, object.getClass().getName(), object);
        } else {
            store(type, object.getClass().getName(), object);
        }
    }

    private static ServerContext getABCoreServerContext() throws CannotFindABCoreException {
        try {
            Plugin plugin = Bukkit.getPluginManager().getPlugin(ABCORE_PLUGIN_NAME);
            if (plugin != null) {
                return (ServerContext) plugin.getClass().getMethod(GET_SERVER_CONTEXT_METHOD_NAME).invoke(plugin);
            }
            throw new CannotFindABCoreException();
        } catch (Exception e) {
            throw new CannotFindABCoreException(e);
        }
    }

}