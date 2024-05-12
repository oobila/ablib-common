package com.github.oobila.bukkit.common;

import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ServerContext {
    private static final String GET_SERVER_CONTEXT_METHOD_NAME = "getServerContext";

    private final Map<Class<?>, TypeMap<?>> data = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <S, T> T store(Class<S> type, String name, T object) throws ServerContextException {
        data.putIfAbsent(type, new TypeMap<>(type));
        if (data.get(type).containsKey(name)) {
            throw new ServerContextException(String.format("[%s] already exists for class: %s", name, type.getName()));
        }
        ((TypeMap<S>) data.get(type)).put(name, (S) object);
        return object;
    }

    public <T> T store(String name, T object) throws ServerContextException {
        return store(object.getClass(), name, object);
    }

    public <T> T get(Class<T> type, String name) {
        try {
            return type.cast(data.get(type).get(name));
        } catch (NullPointerException | ClassCastException e) {
            return null;
        }
    }

    public <T> T remove(Class<T> type, String name) {
        try {
            return type.cast(data.get(type).remove(name));
        } catch (NullPointerException | ClassCastException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public <T> Collection<T> get(Class<T> type) {
        return (Collection<T>) data.get(type).values();
    }

    public static ServerContext fromABCore() throws CannotFindABCoreException {
        try {
            Plugin plugin = ABCommon.getABCore();
            return (ServerContext) plugin.getClass().getMethod(GET_SERVER_CONTEXT_METHOD_NAME).invoke(plugin);
        } catch (Exception e) {
            throw new CannotFindABCoreException(e);
        }
    }

}
