package io.github.novinity.boxmines.commands.subcommands;

import io.github.novinity.boxmines.commands.SubCommand;
import io.github.novinity.boxmines.utils.PositionData;
import io.github.novinity.boxmines.utils.PositionalDataObject;
import io.github.novinity.boxmines.utils.Vector3;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Pos2SC extends SubCommand {
    @Override
    public String getName() {
        return "pos2";
    }

    @Override
    public String getDescription() {
        return "Set the second position for your mine placement";
    }

    @Override
    public String getSyntax() {
        return "/bm pos2";
    }

    @Override
    public String getRequiredPermission() {
        return "boxmines.pos2";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("boxmines.pos2")) {
            player.sendMessage(ChatColor.RED + "No permission!");
            return;
        }

        PositionalDataObject existing = PositionData.getPositionData(player);
        if (existing == null) existing = new PositionalDataObject();

        Block block = player.getLocation().getBlock();

        existing.pos2 = new Vector3(block.getX(), block.getY()-1, block.getZ());
        PositionData.setPositionData(player, existing);

        player.sendMessage(ChatColor.GREEN + "Set position 2 to " + existing.pos2.toString());
    }
}