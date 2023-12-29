package io.github.novinity.boxmines.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TPPlayerToMineTop {
    public static boolean tpPlayerToMineTop(Player player, Location pos1, Location pos2) {
        try {
            Integer highestY = 0;
            if (pos1.getBlockY() > pos2.getBlockY()) {
                highestY = pos1.getBlockY();
            } else {
                highestY = pos2.getBlockY();
            }

            player.teleport(new Location(player.getWorld(), player.getLocation().getX(), highestY+1, player.getLocation().getZ()));
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
