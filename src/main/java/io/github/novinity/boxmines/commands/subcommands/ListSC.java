package io.github.novinity.boxmines.commands.subcommands;

import io.github.novinity.boxmines.BoxMines;
import io.github.novinity.boxmines.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Set;

public class ListSC extends SubCommand {
    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getDescription() {
        return "Return a list of added mines";
    }

    @Override
    public String getSyntax() {
        return "/bm list";
    }

    @Override
    public String getRequiredPermission() {
        return "boxmines.list";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("boxmines.list")) {
            player.sendMessage(ChatColor.RED + "No permission!");
            return;
        }

        player.sendMessage(ChatColor.AQUA + "Mines:");
        Set<String> keys = null;
        try {
            keys = Objects.requireNonNull(BoxMines.getInstance().getConfig().getConfigurationSection("mines")).getKeys(false);
        } catch (NullPointerException e) {
            player.sendMessage(ChatColor.GOLD + "None!");
            return;
        }

        if (keys.toArray().length == 0) {
            player.sendMessage(ChatColor.GOLD + "None!");
        } else {
            for (String key : keys) {
                player.sendMessage(ChatColor.GOLD + key);
            }
        }
    }
}
