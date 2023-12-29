package io.github.novinity.boxmines.utils;

import io.github.novinity.boxmines.BoxMines;

import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;

public class ReloadTimers {
    public static boolean reloadTimers() {
        try {
            RegenIntervals.destroyAllIntervals();

            Set<String> keys = Objects.requireNonNull(BoxMines.getInstance().getConfig().getConfigurationSection("mines")).getKeys(false);
            for (String key : keys) {
                if (BoxMines.getInstance().getConfig().get("mines."+key+".regenInterval") != null && BoxMines.getInstance().getConfig().getInt("mines."+key+".regenInterval") > 0) {
                    RegenIntervals.createRegenInterval(key, BoxMines.getInstance().getConfig().getInt("mines."+key+".regenInterval"));
                }
            }
            return true;
        } catch (NullPointerException e) {
            BoxMines.getInstance().getLogger().log(Level.INFO, "No mines created.");
            return false;
        }
    }
}
