package com.github.oobila.bukkit.common;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ServerContext {

    private final Map<Class<?>, TypeMap<?>> data = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <S, T> void store(Class<S> type, String name, T object) throws ServerContextException {
        data.putIfAbsent(type, new TypeMap<>(type));
        if (data.get(type).containsKey(name)) {
            throw new ServerContextException(String.format("[%s] already exists for class: %s", name, type.getName()));
        }
        ((TypeMap<S>) data.get(type)).put(name, (S) object);
    }

    public <T> void store(String name, T object) throws ServerContextException {
        store(object.getClass(), name, object);
    }

    public <T> T get(Class<T> type, String name) {
        try {
            return type.cast(data.get(type).get(name));
        } catch (NullPointerException | ClassCastException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public <T> Collection<T> get(Class<T> type) {
        return (Collection<T>) data.get(type).values();
    }

}
