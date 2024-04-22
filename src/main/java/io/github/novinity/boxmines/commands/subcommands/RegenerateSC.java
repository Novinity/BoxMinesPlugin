package io.github.novinity.boxmines.commands.subcommands;

import io.github.novinity.boxmines.commands.SubCommand;
import io.github.novinity.boxmines.utils.RegenMine;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RegenerateSC extends SubCommand {
    @Override
    public String getName() {
        return "regenerate";
    }

    @Override
    public String getDescription() {
        return "Regenerate a mine";
    }

    @Override
    public String getSyntax() {
        return "/bm regenerate <minename>";
    }

    @Override
    public String getRequiredPermission() {
        return "boxmines.regenerate";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("boxmines.regenerate")) {
            player.sendMessage(ChatColor.RED + "No permission!");
            return;
        }

        if (args.length < 2 || args[1].isEmpty()) {
            player.sendMessage(ChatColor.RED + "You need to provide a mine to regenerate!");
            return;
        }

        boolean success = RegenMine.regenMine(args[1]);
        if (success) {
            player.sendMessage(ChatColor.GREEN + "Regenerated " + ChatColor.GOLD + args[1]);
        } else {
            player.sendMessage(ChatColor.RED + "Something went wrong! Make sure you have blocks set for the mine and that the mine exists.");
        }
    }
}
