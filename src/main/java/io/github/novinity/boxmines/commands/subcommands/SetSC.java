package io.github.novinity.boxmines.commands.subcommands;

import io.github.novinity.boxmines.BoxMines;
import io.github.novinity.boxmines.commands.SubCommand;
import io.github.novinity.boxmines.utils.RegenIntervals;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Set;

public class SetSC extends SubCommand {
    @Override
    public String getName() {
        return "set";
    }

    @Override
    public String getDescription() {
        return "Set a block for a mine";
    }

    @Override
    public String getSyntax() {
        return "/bm set <minename> <block> <percentage>";
    }

    @Override
    public String getRequiredPermission() {
        return "boxmines.set";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("boxmines.set")) {
            player.sendMessage(ChatColor.RED + "No permission!");
            return;
        }

        // args[1] is mineName
        // args[2] is block
        // args[3] is percentage

        String mineName;
        String blockName;
        Integer percentage;

        if (args.length < 2 || args[1].isEmpty()) {
            player.sendMessage(ChatColor.RED + "You must provide a mine to set the blocks for!");
            return;
        }
        mineName = args[1];

        if (args.length < 3 || args[2].isEmpty()) {
            player.sendMessage(ChatColor.RED + "You must provide a block to set!");
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

        if (args.length < 4 || args[3].isEmpty()) {
            player.sendMessage(ChatColor.RED + "You must provide a percentage!");
            return;
        }
        try {
            percentage = Integer.valueOf(args[3]);
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "The percentage must be a number!");
            return;
        }

        if (BoxMines.getInstance().getConfig().get("mines."+mineName) == null) {
            player.sendMessage(ChatColor.RED + "That mine does not exist!");
            return;
        }

        try {
            Set<String> keys = Objects.requireNonNull(BoxMines.getInstance().getConfig().getConfigurationSection("mines."+mineName+".blocks")).getKeys(false);
            Integer val = 0;
            for (String key : keys) {
                if (BoxMines.getInstance().getConfig().get("mines."+mineName+".blocks."+key) != null) {
                    val += BoxMines.getInstance().getConfig().getInt("mines."+mineName+".blocks."+key);
                    if (val > 100) {
                        val -= BoxMines.getInstance().getConfig().getInt("mines."+mineName+".blocks."+key);
                        BoxMines.getInstance().getConfig().set("mines."+mineName+".blocks."+key, null);
                    }
                }
            }
            if (val + percentage > 100) {
                player.sendMessage(ChatColor.RED + "That percentage would exceed 100%! Current percentage is: " + ChatColor.AQUA + val + "%");
                return;
            }

        } catch (Exception e) {
            // Just continue
        }

        BoxMines.getInstance().getConfig().set("mines."+mineName+".blocks."+blockName.toLowerCase(), percentage);
        BoxMines.getInstance().saveConfig();

        player.sendMessage(ChatColor.GREEN + "Successfully set " + ChatColor.YELLOW + blockName.toLowerCase() + ChatColor.GREEN + " to " +
                ChatColor.AQUA + String.valueOf(percentage) + ChatColor.GREEN + " on mine " + ChatColor.GOLD + mineName);
    }
}
