package io.github.novinity.boxmines.commands.subcommands;

import io.github.novinity.boxmines.BoxMines;
import io.github.novinity.boxmines.commands.SubCommand;
import io.github.novinity.boxmines.utils.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.List;

public class AddSC extends SubCommand {
    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "Add a mine";
    }

    @Override
    public String getSyntax() {
        return "/bm add <minename>";
    }

    @Override
    public String getRequiredPermission() {
        return "boxmines.add";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("boxmines.add")) {
            player.sendMessage(ChatColor.RED + "No permission!");
            return;
        }

        Vector3 pos1 = null;
        Vector3 pos2 = null;

        if (!BoxMines.worldeditEnabled) {
            PositionalDataObject obj = PositionData.getPositionData(player);
            if (obj == null) {
                player.sendMessage(ChatColor.RED + "You do not have any area selected!");
                return;
            }
            if (obj.pos1 == null) {
                player.sendMessage(ChatColor.RED + "Position 1 not set!");
                return;
            }
            if (obj.pos2 == null) {
                player.sendMessage(ChatColor.RED + "Position 2 not set!");
                return;
            }
            pos1 = obj.pos1;
            pos2 = obj.pos2;
        }

        if (args.length < 2 || args[1].isEmpty()) {
            player.sendMessage(ChatColor.RED + "You must provide a unique name for this mine!");
            return;
        }

        if (BoxMines.getInstance().getConfig().get("mines." + args[1]) != null) {
            player.sendMessage(ChatColor.RED + "Mine name must be unique! You can find a list of mines by doing /bm list");
            return;
        }

        String mineName = args[1];

        BoxMines.getInstance().getConfig().set("mines."+mineName+".pos1", new Location(player.getWorld(), pos1.getX(), pos1.getY(), pos1.getZ()));
        BoxMines.getInstance().getConfig().set("mines."+mineName+".pos2", new Location(player.getWorld(), pos2.getX(), pos2.getY(), pos2.getZ()));
        BoxMines.getInstance().saveConfig();

        player.sendMessage(ChatColor.GREEN + "Successfully added mine " + ChatColor.GOLD + mineName + ChatColor.GREEN + "!");

        RegenIntervals.createClearInterval(mineName, 1);
    }
}
