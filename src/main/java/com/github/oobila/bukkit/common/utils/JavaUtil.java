package com.github.oobila.bukkit.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.stream.Collector;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JavaUtil {

    public static <T> Collector<T, ?, T> toSingleton() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException();
                    }
                    return list.get(0);
                }
        );
    }

    public static <T> Collector<T, ?, T> toNullableSingleton() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        return null;
                    }
                    return list.get(0);
                }
        );
    }

}
