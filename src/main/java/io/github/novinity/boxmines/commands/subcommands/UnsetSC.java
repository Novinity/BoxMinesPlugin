package io.github.novinity.boxmines.commands.subcommands;

import io.github.novinity.boxmines.BoxMines;
import io.github.novinity.boxmines.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Set;

public class UnsetSC extends SubCommand {
    @Override
    public String getName() {
        return "unset";
    }

    @Override
    public String getDescription() {
        return "Unset a block for a mine";
    }

    @Override
    public String getSyntax() {
        return "/bm unset <minename> <block>";
    }

    @Override
    public String getRequiredPermission() {
        return "boxmines.unset";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("boxmines.unset")) {
            player.sendMessage(ChatColor.RED + "No permission!");
            return;
        }

        // args[1] is mineName
        // args[2] is block

        String mineName;
        String blockName;

        if (args.length < 2 || args[1].isEmpty()) {
            player.sendMessage(ChatColor.RED + "You must provide a mine to unset the block for!");
            return;
        }
        mineName = args[1];

        if (args.length < 3 || args[2].isEmpty()) {
            player.sendMessage(ChatColor.RED + "You must provide a block to unset!");
            return;
        }
        blockName = args[2];

        boolean validBlock = false;
        for (Material m : Material.values()) {
            if (m.isBlock() && m.name().equals(args[2].toUpperCase())) {
                validBlock = true;
                break;
            }
        }
        if (!validBlock) {
            player.sendMessage(ChatColor.RED + "That is not a valid block!");
            return;
        }

        if (BoxMines.getInstance().getConfig().get("mines."+mineName) == null) {
            player.sendMessage(ChatColor.RED + "That mine does not exist!");
            return;
        }

        BoxMines.getInstance().getConfig().set("mines."+mineName+".blocks."+blockName.toLowerCase(), null);
        BoxMines.getInstance().saveConfig();

        player.sendMessage(ChatColor.GREEN + "Successfully unset " + ChatColor.YELLOW + blockName.toLowerCase() + ChatColor.GREEN + " on mine " +
                ChatColor.GOLD + mineName);
    }
}
