package io.github.novinity.boxmines.commands.subcommands;

//import com.sk89q.worldedit.WorldEdit;
//import com.sk89q.worldedit.bukkit.BukkitAdapter;
//import com.sk89q.worldedit.math.BlockVector3;
//import com.sk89q.worldedit.regions.Region;
import io.github.novinity.boxmines.BoxMines;
import io.github.novinity.boxmines.commands.SubCommand;
import io.github.novinity.boxmines.utils.PositionData;
import io.github.novinity.boxmines.utils.PositionalDataObject;
import io.github.novinity.boxmines.utils.Vector3;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class AddWESC extends SubCommand {
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

        boolean useWE = true;
        BoxMines.getInstance().getLogger().log(Level.ALL, args[0]);
        BoxMines.getInstance().getLogger().log(Level.ALL, args[1]);
        BoxMines.getInstance().getLogger().log(Level.ALL, args[2]);
        if (args.length == 3 && args[2].equalsIgnoreCase("-i")) {
            useWE = false;
        }

        Vector3 pos1 = null;
        Vector3 pos2 = null;

        if (useWE) {
            try {
                com.sk89q.worldedit.regions.Region selectedRegion = null;
                selectedRegion = com.sk89q.worldedit.WorldEdit.getInstance().getSessionManager()
                        .get(com.sk89q.worldedit.bukkit.BukkitAdapter.adapt(player))
                        .getSelection(com.sk89q.worldedit.bukkit.BukkitAdapter.adapt(player.getWorld()));

                pos1 = new Vector3(selectedRegion.getMinimumPoint().getX(), selectedRegion.getMinimumPoint().getY(), selectedRegion.getMinimumPoint().getZ());
                pos2 = new Vector3(selectedRegion.getMaximumPoint().getX(), selectedRegion.getMaximumPoint().getY(), selectedRegion.getMaximumPoint().getZ());
            } catch (Exception e) {
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
        } else {
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
    }
}
