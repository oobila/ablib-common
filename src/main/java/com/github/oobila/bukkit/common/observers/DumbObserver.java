package com.github.oobila.bukkit.common.observers;

import java.util.List;

public interface DumbObserver<T> {

    List<DumbObserver<T>> getObservers();

    default boolean addObserver(DumbObserver<T> advancedObserver) {
        return getObservers().add(advancedObserver);
    }

    default void observe(ObservationType type, T t) {
        getObservers().forEach(observer -> {
            switch (type) {
                case CREATE -> onCreate(t);
                case READ -> onRead(t);
                case UPDATE -> onUpdate(t);
                case DELETE -> onDelete(t);
                case LOAD -> onLoad(t);
                case UNLOAD -> onUnload(t);
                case INTERACT -> onInteract(t);
                case LEFT_CLICK -> onLeftClick(t);
                case LEFT_CLICK_SHIFT -> onShiftLeftClick(t);
                case RIGHT_CLICK -> onRightClick(t);
                case RIGHT_CLICK_SHIFT -> onShiftRightClick(t);
                case DAMAGE -> onDamage(t);
            }
        });
    }

    default void onCreate(T t) {}
    default void onRead(T t) {}
    default void onUpdate(T t) {}
    default void onDelete(T t) {}
    default void onLoad(T t) {}
    default void onUnload(T t) {}
    default void onInteract(T t) {}
    default void onLeftClick(T t) {}
    default void onShiftLeftClick(T t) {}
    default void onRightClick(T t) {}
    default void onShiftRightClick(T t) {}
    default void onDamage(T t) {}

    enum ObservationType {
        CREATE,
        READ,
        UPDATE,
        DELETE,
        LOAD,
        UNLOAD,
        INTERACT,
        LEFT_CLICK,
        LEFT_CLICK_SHIFT,
        RIGHT_CLICK,
        RIGHT_CLICK_SHIFT,
        DAMAGE
    }

}
