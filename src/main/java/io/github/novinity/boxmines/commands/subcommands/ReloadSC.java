package io.github.novinity.boxmines.commands.subcommands;

import io.github.novinity.boxmines.BoxMines;
import io.github.novinity.boxmines.commands.SubCommand;
import io.github.novinity.boxmines.utils.ReloadTimers;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ReloadSC extends SubCommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reload the plugin";
    }

    @Override
    public String getSyntax() {
        return "/bm reload";
    }

    @Override
    public String getRequiredPermission() {
        return "boxmines.reload";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("boxmines.reload")) {
            player.sendMessage(ChatColor.RED + "No permission!");
            return;
        }

        player.sendMessage(ChatColor.RED + "Reloading Box Mines...");

        BoxMines.getInstance().reloadConfig();

        BoxMines.getInstance().saveDefaultConfig();
        BoxMines.getInstance().getConfig().options().copyDefaults();
        BoxMines.getInstance().saveConfig();

        ReloadTimers.reloadTimers();

        player.sendMessage(ChatColor.GREEN + "Box Mines Reloaded!");
    }
}
