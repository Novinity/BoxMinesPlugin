package io.github.novinity.boxmines.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.time.temporal.ValueRange;

public class RegionChecker {

    public static boolean inRegion(Player p, Location loc1, Location loc2) {
        Integer minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
        Integer minY = Math.min(loc1.getBlockY(), loc2.getBlockY());
        Integer minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());

        Integer maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
        Integer maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());
        Integer maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

        if (
                ValueRange.of(minX, maxX).isValidIntValue(p.getLocation().getBlockX()) &&
                ValueRange.of(minY, maxY).isValidIntValue(p.getLocation().getBlockY()) &&
                ValueRange.of(minZ, maxZ).isValidIntValue(p.getLocation().getBlockZ())
        ) {
            return true;
        }

        return false;
    }
}
