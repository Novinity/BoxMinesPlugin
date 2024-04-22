package io.github.novinity.boxmines.commands.subcommands;

import io.github.novinity.boxmines.BoxMines;
import io.github.novinity.boxmines.commands.SubCommand;
import io.github.novinity.boxmines.utils.GetCenterPoint;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import static java.lang.Math.sqrt;

public class TPSC extends SubCommand {
    @Override
    public String getName() {
        return "tp";
    }

    @Override
    public String getDescription() {
        return "Teleports you to the top of the provided mine";
    }

    @Override
    public String getSyntax() {
        return "/bm tp <minename>";
    }

    @Override
    public String getRequiredPermission() {
        return "boxmines.tp";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("boxmines.tp")) {
            player.sendMessage(ChatColor.RED + "No permission!");
            return;
        }

        Location pos1 = BoxMines.getInstance().getConfig().getLocation("mines."+args[1]+".pos1");
        Location pos2 = BoxMines.getInstance().getConfig().getLocation("mines."+args[1]+".pos2");

        Integer highestY = 0;
        if (pos1.getBlockY() > pos2.getBlockY()) {
            highestY = pos1.getBlockY();
        } else {
            highestY = pos2.getBlockY();
        }

        Location tpPoint = GetCenterPoint.getCenterPoint(pos1, pos2, pos1.getWorld());
        tpPoint.setY(highestY+1);

        player.teleport(tpPoint);
    }
}
