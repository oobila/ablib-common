package com.github.oobila.bukkit.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class TypeMap<T> implements Map<String, T> {

    @Getter
    private final Class<T> type;

    @Delegate
    private final Map<String, T> map = new HashMap<>();

}
