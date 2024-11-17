package io.github.novinity.boxmines.utils;

import io.github.novinity.boxmines.BoxMines;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.logging.Level;

public class RegenMine {
    public static boolean regenMine(String mineName) {
        try {
            Location pos1 = BoxMines.getInstance().getConfig().getLocation("mines." + mineName + ".pos1");
            Location pos2 = BoxMines.getInstance().getConfig().getLocation("mines." + mineName + ".pos2");

            HashMap<Material, Integer> blocks = new HashMap<Material, Integer>();

            for (String blockName : BoxMines.getInstance().getConfig().getConfigurationSection("mines." + mineName + ".blocks").getKeys(false)) {
                for (Material m : Material.values()) {
                    if (m.isBlock() && m.name().equals(blockName.toUpperCase())) {
                        blocks.put(m, BoxMines.getInstance().getConfig().getInt("mines."+mineName+".blocks."+blockName));
                        break;
                    }
                }
            }

            if (blocks.isEmpty()) {
                BoxMines.getInstance().getLogger().log(Level.SEVERE, "Couldn't get block types in RegenMine");
                return false;
            }

            List<Block> blocksInArea = SelectArea.select(pos1, pos2, pos1.getWorld());

            for (Block block : blocksInArea) {
                boolean choseOne = false;
                Material highest = null;
                int highestPercentage = 0;

                for (Map.Entry<Material, Integer> mi : blocks.entrySet()) {
                    if (mi.getValue() <= 0) continue;

                    Random r = new Random();
                    boolean chosen = (r.nextInt(100-1) + 1) <= mi.getValue();
                    if (chosen) {
                        choseOne = true;
                        block.setType(mi.getKey());
                        break;
                    }
                    if (highestPercentage < mi.getValue()) {
                        highestPercentage = mi.getValue();
                        highest = mi.getKey();
                    }
                }
                if (!choseOne && highest != null) {
                    block.setType(highest);
                }
            }

            for (Player p : BoxMines.getInstance().getServer().getOnlinePlayers()) {
                if (RegionChecker.inRegion(p.getPlayer(), pos1, pos2)) {
                    TPPlayerToMineTop.tpPlayerToMineTop(p, pos1, pos2);
                }
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public static boolean clearMine(String mineName) {
        try {
            Location pos1 = BoxMines.getInstance().getConfig().getLocation("mines." + mineName + ".pos1");
            Location pos2 = BoxMines.getInstance().getConfig().getLocation("mines." + mineName + ".pos2");

            List<Block> blocksInArea = SelectArea.select(pos1, pos2, pos1.getWorld());

            for (Block block : blocksInArea) {
                block.setType(Material.AIR);
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
