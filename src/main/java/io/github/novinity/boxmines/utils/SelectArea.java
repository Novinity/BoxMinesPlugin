package io.github.novinity.boxmines.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SelectArea {
    public static List<Block> select(Location loc1, Location loc2, World w) {
        List<Block> blocks = new ArrayList<Block>();

        int x1 = loc1.getBlockX();
        int y1 = loc1.getBlockY();
        int z1 = loc1.getBlockZ();

        int x2 = loc2.getBlockX();
        int y2 = loc2.getBlockY();
        int z2 = loc2.getBlockZ();

        int xMin, yMin, zMin;
        int xMax, yMax, zMax;
        int x, y, z;

        if (x1> x2) {
            xMin = x2;
            xMax = x1;
        } else {
            xMin = x1;
            xMax = x2;
        }

        if(y1 > y2){
            yMin = y2;
            yMax = y1;
        } else {
            yMin = y1;
            yMax = y2;
        }

        if(z1 > z2){
            zMin = z2;
            zMax = z1;
        } else {
            zMin = z1;
            zMax = z2;
        }

        for(x = xMin; x <= xMax; x ++){
            for(y = yMin; y <= yMax; y ++){
                for(z = zMin; z <= zMax; z ++){
                    Block b = new Location(w, x, y, z).getBlock();
                    blocks.add(b);
                }
            }
        }

        return blocks;
    }
}
