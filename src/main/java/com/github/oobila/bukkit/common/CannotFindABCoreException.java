package com.github.oobila.bukkit.common;

public class CannotFindABCoreException extends Exception {

    public CannotFindABCoreException() {
        super("Cannot find plugin: ABCore");
    }
    public CannotFindABCoreException(Throwable cause) {
        super(cause);
    }
}
