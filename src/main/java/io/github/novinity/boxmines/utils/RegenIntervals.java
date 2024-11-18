package io.github.novinity.boxmines.utils;

import io.github.novinity.boxmines.BoxMines;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class RegenIntervals {
    private static HashMap<String, Integer> regenTaskIds = new HashMap<String, Integer>();
    private static HashMap<String, Integer> clearTaskIds = new HashMap<String, Integer>();

    public static boolean createRegenInterval(String mineName, Integer intervalInSeconds) {
        try {
            if (regenTaskIds.containsKey(mineName)) {
                destroyRegenInterval(mineName);
            }
            int id = BoxMines.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(BoxMines.getInstance(), new Runnable() {
                @Override
                public void run() {
                    RegenMine.regenMine(mineName);
                    try {
                        if (BoxMines.getInstance().getConfig().getBoolean("mines."+mineName+".announceRegen")) {
                            for (Player p : BoxMines.getInstance().getServer().getOnlinePlayers()) {
                                p.sendMessage(ChatColor.AQUA + mineName + ChatColor.GOLD + " has been regenerated!");
                            }
                        }
                    } catch (Exception e) {
                        // nothing
                    }
                }
            }, intervalInSeconds * 20L, intervalInSeconds * 20L);

            regenTaskIds.put(mineName, id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean destroyRegenInterval(String mineName) {
        try {
            BoxMines.getInstance().getServer().getScheduler().cancelTask(regenTaskIds.get(mineName));
            regenTaskIds.remove(mineName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean destroyAllIntervals() {
        try {
            for (Map.Entry<String, Integer> entry : regenTaskIds.entrySet()) {
                BoxMines.getInstance().getServer().getScheduler().cancelTask(entry.getValue());
                regenTaskIds.remove(entry.getKey());
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean createClearInterval(String mineName, Integer intervalInSeconds) {
        try {
            if (clearTaskIds.containsKey(mineName)) {
                destroyClearInterval(mineName);
            }
            int id = BoxMines.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(BoxMines.getInstance(), new Runnable() {
                @Override
                public void run() {
                    try {
                        if (BoxMines.getInstance().getConfig().get("mines."+mineName+".resetWhenEmpty") != null
                                && BoxMines.getInstance().getConfig().getBoolean("mines."+mineName+".resetWhenEmpty")) {
                            Location pos1 = BoxMines.getInstance().getConfig().getLocation("mines."+mineName+".pos1");
                            Location pos2 = BoxMines.getInstance().getConfig().getLocation("mines."+mineName+".pos2");

                            List<Block> blocksInArea = SelectArea.select(pos1,
                                    pos2, pos1.getWorld());

                            boolean foundAnything = false;

                            for (Block block : blocksInArea) {
                                if (block.getType() != Material.AIR) {
                                    foundAnything = true;
                                    break;
                                }
                            }

                            if (!foundAnything) {
                                RegenMine.regenMine(mineName);
                                try {
                                    if (BoxMines.getInstance().getConfig().getBoolean("mines."+mineName+".announceRegen")) {
                                        for (Player p : BoxMines.getInstance().getServer().getOnlinePlayers()) {
                                            p.sendMessage(ChatColor.AQUA + mineName + ChatColor.GOLD + " has been regenerated!");
                                        }
                                    }
                                } catch (Exception e) {
                                    // nothing
                                }
                            }
                        }
                    } catch (Exception e) {
                        // nothing
                    }
                }
            }, intervalInSeconds * 20L, intervalInSeconds * 20L);

            clearTaskIds.put(mineName, id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean destroyClearInterval(String mineName) {
        try {
            BoxMines.getInstance().getServer().getScheduler().cancelTask(clearTaskIds.get(mineName));
            clearTaskIds.remove(mineName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
