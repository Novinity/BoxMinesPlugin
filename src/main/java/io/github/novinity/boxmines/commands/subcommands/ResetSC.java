package io.github.novinity.boxmines.commands.subcommands;

import io.github.novinity.boxmines.commands.SubCommand;
import io.github.novinity.boxmines.utils.RegenMine;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ResetSC extends SubCommand {
    @Override
    public String getName() {
        return "reset";
    }

    @Override
    public String getDescription() {
        return "reset a mine";
    }

    @Override
    public String getSyntax() {
        return "/bm reset <minename>";
    }

    @Override
    public String getRequiredPermission() {
        return "boxmines.reset";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("boxmines.reset")) {
            player.sendMessage(ChatColor.RED + "No permission!");
            return;
        }

        if (args.length < 2 || args[1].isEmpty()) {
            player.sendMessage(ChatColor.RED + "You need to provide a mine to reset!");
            return;
        }

        boolean success = RegenMine.regenMine(args[1]);
        if (success) {
            player.sendMessage(ChatColor.GREEN + "Reset " + ChatColor.GOLD + args[1]);
        } else {
            player.sendMessage(ChatColor.RED + "Something went wrong! Make sure you have blocks set for the mine and that the mine exists.");
        }
    }
}
