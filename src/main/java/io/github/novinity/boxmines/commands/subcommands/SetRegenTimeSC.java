package io.github.novinity.boxmines.commands.subcommands;

import io.github.novinity.boxmines.BoxMines;
import io.github.novinity.boxmines.commands.SubCommand;
import io.github.novinity.boxmines.utils.RegenIntervals;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import javax.swing.*;

public class SetRegenTimeSC extends SubCommand {
    @Override
    public String getName() {
        return "setregentime";
    }

    @Override
    public String getDescription() {
        return "Set the regen interval for a mine";
    }

    @Override
    public String getSyntax() {
        return "/bm setregentime <minename> <timeinseconds>";
    }

    @Override
    public String getRequiredPermission() {
        return "boxmines.setregentime";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("boxmines.setregentime")) {
            player.sendMessage(ChatColor.RED + "No permission!");
            return;
        }

        // args[1] is the minename
        // args[2] is in time in seconds

        String mineName;
        Integer timeInSeconds;

        if (args.length < 2 || args[1].isEmpty()) {
            player.sendMessage(ChatColor.RED + "You must provide a mine to set the regen interval for!");
            return;
        }
        mineName = args[1];

        if (args.length < 3 || args[2].isEmpty()) {
            player.sendMessage(ChatColor.RED + "You must provide a interval (in seconds)!");
            return;
        }
        try {
            timeInSeconds = Integer.valueOf(args[2]);
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "That is not a valid amount of time! The time must be a number.");
            return;
        }

        if (BoxMines.getInstance().getConfig().get("mines."+mineName) == null) {
            player.sendMessage(ChatColor.RED + "That mine does not exist!");
            return;
        }

        if (timeInSeconds <= 0) {
            RegenIntervals.destroyRegenInterval(mineName);
            player.sendMessage(ChatColor.AQUA + mineName + ChatColor.GREEN + " will no longer automatically regenerate.");
        } else {
            RegenIntervals.createRegenInterval(mineName, timeInSeconds);
            player.sendMessage(ChatColor.AQUA + mineName + ChatColor.GREEN + " will now automatically regenerate every " +
                    ChatColor.GOLD + timeInSeconds.toString() + ChatColor.GREEN + " seconds.");
        }

        BoxMines.getInstance().getConfig().set("mines."+mineName+".regenInterval", timeInSeconds);
        BoxMines.getInstance().saveConfig();
    }
}
