package io.github.novinity.boxmines.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class PositionData {
    public static HashMap<Player, PositionalDataObject> PositionDatas = new HashMap<Player, PositionalDataObject>();

    public static PositionalDataObject getPositionData(Player player) {
        return PositionDatas.get(player);
    }

    public static void setPositionData(Player player, PositionalDataObject data) {
        PositionDatas.put(player, data);
    }

    public static void removePositionData(Player player) {
        PositionDatas.remove(player);
    }
}