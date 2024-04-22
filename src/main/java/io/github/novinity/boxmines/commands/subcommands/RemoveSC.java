package io.github.novinity.boxmines.commands.subcommands;

import io.github.novinity.boxmines.BoxMines;
import io.github.novinity.boxmines.commands.SubCommand;
import io.github.novinity.boxmines.utils.RegenIntervals;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RemoveSC extends SubCommand {
    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public String getDescription() {
        return "Remove a mine";
    }

    @Override
    public String getSyntax() {
        return "/bm remove <minename>";
    }

    @Override
    public String getRequiredPermission() {
        return "boxmines.remove";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("boxmines.remove")) {
            player.sendMessage(ChatColor.RED + "No permission!");
            return;
        }

        if (args.length < 2 || args[1].isEmpty()) {
            player.sendMessage(ChatColor.RED + "You must provide the name of the mine you'd like to remove!");
            return;
        }

        if (BoxMines.getInstance().getConfig().get("mines."+args[1]) == null) {
            player.sendMessage(ChatColor.RED + "That mine does not exist!");
            return;
        }

        BoxMines.getInstance().getConfig().set("mines."+args[1], null);
        BoxMines.getInstance().saveConfig();

        RegenIntervals.destroyRegenInterval(args[1]);

        player.sendMessage(ChatColor.GREEN + "Successfully deleted mine " + ChatColor.GOLD + args[1] + "!");
    }
}
