package io.github.novinity.boxmines.commands.subcommands;

import io.github.novinity.boxmines.commands.SubCommand;
import io.github.novinity.boxmines.utils.RegenMine;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ClearSC extends SubCommand {
    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "Clear a mine";
    }

    @Override
    public String getSyntax() {
        return "/bm clear <minename>";
    }

    @Override
    public String getRequiredPermission() {
        return "boxmines.clear";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("boxmines.clear")) {
            player.sendMessage(ChatColor.RED + "No permission!");
            return;
        }

        if (args.length < 2 || args[1].isEmpty()) {
            player.sendMessage(ChatColor.RED + "You need to provide a mine to clear!");
            return;
        }

        boolean success = RegenMine.clearMine(args[1]);
        if (success) {
            player.sendMessage(ChatColor.GREEN + "Cleared " + ChatColor.GOLD + args[1]);
        } else {
            player.sendMessage(ChatColor.RED + "Something went wrong!");
        }
    }
}
