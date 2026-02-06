package dev.Fjc.betterGod;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import static dev.Fjc.betterGod.GodInstance.plugin;

public class Util {

    public static String stringify(Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }

    public static Component nbt(String line) {
        return PlainTextComponentSerializer.plainText().deserialize(line);
    }

    public static void info(String text) {
        plugin.getLogger().info(text);
    }

    public static void warn(String warning) {
        plugin.getLogger().warning(warning);
    }

    public static void severe(String severe) {
        plugin.getLogger().severe(severe);
    }
}
