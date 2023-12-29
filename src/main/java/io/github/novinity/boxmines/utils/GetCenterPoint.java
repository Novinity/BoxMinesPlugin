package io.github.novinity.boxmines.utils;

import org.bukkit.Location;
import org.bukkit.World;

public class GetCenterPoint {
    public static Location getCenterPoint(Location pos1, Location pos2, World world) {
        Integer x1 = pos1.getBlockX();
        Integer y1 = pos1.getBlockY();
        Integer z1 = pos1.getBlockZ();

        Integer x2 = pos2.getBlockX();
        Integer y2 = pos2.getBlockY();
        Integer z2 = pos2.getBlockZ();

        Integer x = (x1+x2)/2;
        Integer y = (y1+y2)/2;
        Integer z = (z1+z2)/2;

        return new Location(world, x, y, z);
    }
}
