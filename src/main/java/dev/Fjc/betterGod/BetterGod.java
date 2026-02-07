package dev.Fjc.betterGod;

import dev.Fjc.betterGod.command.Debug;
import dev.Fjc.betterGod.command.GodCommand;
import dev.Fjc.betterGod.depend.Mythic;
import dev.Fjc.betterGod.listener.DamageIntercept;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetterGod extends JavaPlugin {

    private static BetterGod instance;

    @Override
    public void onEnable() {
        instance = this;
        if (!Mythic.verify()) {
            Util.severe("Failed to verify a Mythic instance. Is MythicMobs present?");
        }
        registerEvent(new DamageIntercept());

        registerCmd(new GodCommand(), "god");
        registerCmd(new Debug(), "god-debug");

        Util.info("All libraries, listeners, and events loaded.");
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static BetterGod getInstance() {
        return instance;
    }

    private void registerEvent(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
        Util.info("Registered event listener " + listener);
    }

    private void registerCmd(CommandExecutor executor, String command) {
        var instance = this.getServer().getPluginCommand(command);
        if (instance != null) instance.setExecutor(executor);
        else Util.warn("Something went wrong while trying to register command " + command);

        Util.info("Registered command " + command);
    }
}
