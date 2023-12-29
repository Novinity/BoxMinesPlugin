package io.github.novinity.boxmines.utils;

import io.github.novinity.boxmines.BoxMines;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class RegenIntervals {
    private static HashMap<String, Integer> taskIds = new HashMap<String, Integer>();

    public static boolean createRegenInterval(String mineName, Integer intervalInSeconds, boolean sendMessage) {
        try {
            if (taskIds.containsKey(mineName)) {
                destroyRegenInterval(mineName);
            }
            int id = BoxMines.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(BoxMines.getInstance(), new Runnable() {
                @Override
                public void run() {
                    RegenMine.regenMine(mineName);
                    if (sendMessage) {
                        for (Player p : BoxMines.getInstance().getServer().getOnlinePlayers()) {
                            p.sendMessage(ChatColor.AQUA + mineName + ChatColor.GOLD + " has been regenerated!");
                        }
                    }
                }
            }, intervalInSeconds * 20L, intervalInSeconds * 20L);

            taskIds.put(mineName, id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean destroyRegenInterval(String mineName) {
        try {
            BoxMines.getInstance().getServer().getScheduler().cancelTask(taskIds.get(mineName));
            taskIds.remove(mineName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean destroyAllIntervals() {
        try {
            for (Map.Entry<String, Integer> entry : taskIds.entrySet()) {
                BoxMines.getInstance().getServer().getScheduler().cancelTask(entry.getValue());
                taskIds.remove(entry.getKey());
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
