package io.github.novinity.boxmines;

import io.github.novinity.boxmines.commands.CommandManager;
import io.github.novinity.boxmines.utils.PositionData;
import io.github.novinity.boxmines.utils.ReloadTimers;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class BoxMines extends JavaPlugin {

    private static BoxMines instance;

    public static BoxMines getInstance() {
        return instance;
    }

    public static boolean worldeditEnabled = true;

    @Override
    public void onEnable() {
        // Plugin startup logic

        if (getServer().getPluginManager().getPlugin("WorldEdit") == null) {
            getLogger().log(Level.WARNING, "WorldEdit not detected!");
            worldeditEnabled = false;
        }

        instance = this;

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        ReloadTimers.reloadTimers();

        getCommand("boxmines").setExecutor(new CommandManager());
        getCommand("boxmines").setTabCompleter(new CommandManager());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (PositionData.getPositionData(player) != null)
            PositionData.removePositionData(player);
    }
}
