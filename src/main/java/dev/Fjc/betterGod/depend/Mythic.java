package dev.Fjc.betterGod.depend;

import dev.Fjc.betterGod.BetterGod;
import dev.Fjc.betterGod.Util;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class Mythic {

    private static final BetterGod plugin = BetterGod.getInstance();
    private static MythicBukkit mythic;

    static boolean valid = false;

    /**
     * Verify that Mythic is actually present in the instance. This should prevent any
     * plugin crashes if the server doesn't have Mythic or if there's a version mismatch.
     * @return Whether this Mythic instance is valid
     */
    public static boolean verify() {
        Class<?> clazz;
        try {
            clazz = Class.forName("io.lumine.mythic.bukkit.MythicBukkit");
        } catch (ClassNotFoundException e) {
            Util.severe("Mythic API could not be located!");
            Util.severe(e.toString());
            clazz = null;
        }

        if (clazz != null) {
            mythic = MythicBukkit.inst();
            valid = true;
            Util.info("Mythic was successfully loaded.");
        } else mythic = null;
        return clazz != null;
    }

    /**
     * Checks if the given entity is an instance of a MythicMob.
     * @param entity The entity
     * @return Whether this mob is a mythic mob
     */
    public static boolean isMythicMob(Entity entity) {
        return valid && mythic.getMobManager().isMythicMob(entity);
    }

    public static boolean isMythicMobInRange(Location location) {
        for (Entity entity : location.getNearbyEntities(45, 45, 45)) {
            return isMythicMob(entity);
        }
        return false;
    }


}
