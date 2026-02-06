package dev.Fjc.betterGod.data;

import org.bukkit.permissions.Permission;

import java.util.Map;

public enum PermissionKey {

    PARTIAL(new Permission("bg.partial")),
    NORMAL(new Permission("bg.normal")),
    FULL(new Permission("bg.full")),
    ADMIN(new Permission("bg.*", "", Map.of(
            "partial", true,
            "normal", true,
            "full", true
    )));

    private final Permission permission;

    PermissionKey() {
        this.permission = null;
    }

    PermissionKey(Permission permission) {
        this.permission = permission;
    }

    public Permission getPermission() {
        return permission;
    }
}
