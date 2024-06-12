package com.github.oobila.bukkit.common.observers;

import java.util.List;

public interface DumbObservable<T> {

    List<DumbObserver<T>> getObservers();

    default boolean addObserver(DumbObserver<T> dumbOvserver) {
        return getObservers().add(dumbOvserver);
    }

    default void observe(ObservationType type, T t) {
        getObservers().forEach(observer -> {
            switch (type) {
                case CREATE -> observer.onCreate(t);
                case READ -> observer.onRead(t);
                case UPDATE -> observer.onUpdate(t);
                case DELETE -> observer.onDelete(t);
                case LOAD -> observer.onLoad(t);
                case UNLOAD -> observer.onUnload(t);
                case INTERACT -> observer.onInteract(t);
                case LEFT_CLICK -> observer.onLeftClick(t);
                case LEFT_CLICK_SHIFT -> observer.onShiftLeftClick(t);
                case RIGHT_CLICK -> observer.onRightClick(t);
                case RIGHT_CLICK_SHIFT -> observer.onShiftRightClick(t);
                case DAMAGE -> observer.onDamage(t);
            }
        });
    }

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
