package dev.Fjc.betterGod.command;

import dev.Fjc.betterGod.Util;
import dev.Fjc.betterGod.depend.Mythic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Debug implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!sender.isOp()) {
            sender.sendMessage(Util.nbt("No permission for this."));
            return true;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(Util.nbt("You must be a player to use this command."));
            return true;
        }
        player.sendMessage(Util.nbt(Mythic.nearbyMythicMobs(player.getLocation(), 30).toString()));

        return false;
    }
}
