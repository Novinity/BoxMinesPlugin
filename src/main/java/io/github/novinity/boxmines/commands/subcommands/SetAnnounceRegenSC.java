package io.github.novinity.boxmines.commands.subcommands;

import io.github.novinity.boxmines.BoxMines;
import io.github.novinity.boxmines.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SetAnnounceRegenSC extends SubCommand {
    @Override
    public String getName() {
        return "setannounceregen";
    }

    @Override
    public String getDescription() {
        return "Toggle whether the mine will announce it's regeneration or not";
    }

    @Override
    public String getSyntax() {
        return "/bm setannounceregen <mineName>";
    }

    @Override
    public String getRequiredPermission() {
        return "boxmines.setannounceregen";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length < 2 || args[1].isEmpty()) {
            player.sendMessage(ChatColor.RED + "You must provide a mine to toggle for!");
            return;
        }

        try {
            Boolean curVal = BoxMines.getInstance().getConfig().getBoolean("mines."+args[1]+".announceRegen");

            String val = curVal ? "no longer" : "now";

            BoxMines.getInstance().getConfig().set("mines."+args[1]+".announceRegen", !curVal);
            player.sendMessage(ChatColor.GOLD + args[1] + ChatColor.GREEN + " is " + val + " announcing regeneration");
        } catch (Exception e) {
            BoxMines.getInstance().getConfig().set("mines."+args[1]+".announceRegen", true);
            player.sendMessage(ChatColor.GOLD + args[1] + ChatColor.GREEN + " is now announcing regeneration");
        }
    }
}
