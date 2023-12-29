package io.github.novinity.boxmines;

import io.github.novinity.boxmines.commands.CommandManager;
import io.github.novinity.boxmines.utils.ReloadTimers;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class BoxMines extends JavaPlugin {

    private static BoxMines instance;

    public static BoxMines getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        if (getServer().getPluginManager().getPlugin("WorldEdit") == null) {
            getLogger().log(Level.SEVERE, "WorldEdit not detected! This plugin will likely be unable to load without it.");
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
}
