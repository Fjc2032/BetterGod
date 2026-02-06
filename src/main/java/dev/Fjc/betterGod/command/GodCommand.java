package dev.Fjc.betterGod.command;

import dev.Fjc.betterGod.GodInstance;
import dev.Fjc.betterGod.Util;
import dev.Fjc.betterGod.data.Invincibility;
import dev.Fjc.betterGod.data.PermissionKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static dev.Fjc.betterGod.GodInstance.plugin;

public class GodCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        String noPerm = "You do not have permission to run this command.";

        if (args.length == 0) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("You must specify a player.");
                return true;
            }
            // If no args are provided, elevate to the highest possible level
            Invincibility instance = new Invincibility(player);

            // Map values are boolean, so we can simply check if the value is true
            if (GodInstance.b.containsKey(player) && GodInstance.b.get(player)) {
                instance
                        .removeInvincibility()
                        .announceToPlayer("You are no longer invincible.");
                return true;
            }
            List<Invincibility.Context> contexts = new ArrayList<>();
            String message = "You are now invincible with context ";
            switch (level(player)) {
                case 0 -> {
                    contexts.add(Invincibility.Context.ENV);
                    instance.setInvincible(Invincibility.Context.ENV)
                            .announceToPlayer(message + contexts);
                }
                case 1 -> {
                    contexts.add(Invincibility.Context.COMBAT);
                    contexts.add(Invincibility.Context.ENV);
                    instance.setInvincible(Invincibility.Context.COMBAT, Invincibility.Context.ENV)
                            .announceToPlayer(message + contexts);
                }
                case 2 -> {
                    contexts.add(Invincibility.Context.FULL);
                    instance.setInvincible(Invincibility.Context.FULL)
                            .announceToPlayer(message + contexts);
                }
                case 3 -> {
                    contexts.addAll(List.of(Invincibility.Context.values()));
                    instance.setInvincible()
                            .announceToPlayer(message + contexts);
                }
                default -> sender.sendMessage(Util.nbt(noPerm));
            }
        }
        if (args.length == 1) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("You must specify a player.");
                return true;
            }
            else set(player, args[0]);
            return true;

        }
        if (args.length == 2) {
            Player player = plugin.getServer().getPlayer(args[1]);
            if (player != null) set(player, args[0]);
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) return Arrays.stream(Invincibility.Context.values())
                .map(Enum::toString)
                .toList();

        if (args.length == 2) return plugin.getServer().getOnlinePlayers().stream()
                .map(Player::getName)
                .toList();

        return List.of();
    }

    private int level(Player player) {
        for (PermissionKey key : PermissionKey.values()) {
            if (key.getPermission() == null) continue;
            if (player.hasPermission(key.getPermission())) return key.ordinal();
        }
        return 0;
    }

    private void set(@NotNull Player player, String arg) {
        Invincibility instance = new Invincibility(player);
        Arrays.asList(Invincibility.Context.values()).forEach(
                action -> {
                    if (arg.toUpperCase().contains(action.toString())) {
                        if (action.getLevel() >= level(player)) instance.setInvincible(action);
                    }
                }
        );
    }

}
