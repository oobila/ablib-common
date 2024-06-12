package com.github.oobila.bukkit.common.observers;

public interface DumbObserver<T> {

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

}
