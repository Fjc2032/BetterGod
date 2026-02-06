package dev.Fjc.betterGod;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple interface to provide access to global fields
 */
public class GodInstance {

    public static BetterGod plugin = BetterGod.getInstance();

    public static Map<Player, Boolean> b = new HashMap<>();
}
