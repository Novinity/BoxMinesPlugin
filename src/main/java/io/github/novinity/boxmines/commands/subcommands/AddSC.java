package io.github.novinity.boxmines.commands.subcommands;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import io.github.novinity.boxmines.BoxMines;
import io.github.novinity.boxmines.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.logging.Level;

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
        Region selectedRegion = null;
        BlockVector3 pos1 = null;
        BlockVector3 pos2 = null;

        try {

            selectedRegion = WorldEdit.getInstance().getSessionManager().get(BukkitAdapter.adapt(player)).getSelection(BukkitAdapter.adapt(player.getWorld()));
            pos1 = selectedRegion.getMinimumPoint();
            pos2 = selectedRegion.getMaximumPoint();
        } catch (Exception e) {
            BoxMines.getInstance().getLogger().log(Level.INFO, e.getMessage());
            player.sendMessage(ChatColor.RED + "You do not have a valid area selected!");
            return;
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

        BoxMines.getInstance().getConfig().set("mines."+mineName+".pos1", new Location(player.getWorld(), pos1.getBlockX(), pos1.getBlockY(), pos1.getBlockZ()));
        BoxMines.getInstance().getConfig().set("mines."+mineName+".pos2", new Location(player.getWorld(), pos2.getBlockX(), pos2.getBlockY(), pos2.getBlockZ()));
        BoxMines.getInstance().saveConfig();

        player.sendMessage(ChatColor.GREEN + "Successfully added mine " + ChatColor.GOLD + mineName + ChatColor.GREEN + "!");
    }
}
