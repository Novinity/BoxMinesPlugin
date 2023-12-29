package io.github.novinity.boxmines;

import io.github.novinity.boxmines.commands.CommandManager;
import io.github.novinity.boxmines.utils.RegenIntervals;
import io.github.novinity.boxmines.utils.ReloadTimers;
import io.github.novinity.boxmines.utils.UpdateChecker;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.Set;
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

        // TODO Add resource id
        Integer resourceId = 865856342;
        new UpdateChecker(this, resourceId).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                getLogger().info("There is not a new update available.");
            } else {
                getLogger().info("There is a new update available! Current Version: " + this.getDescription().getVersion() + " - New version: " + version);
                getLogger().info("Download here: " + "https://api.spigotmc.org/legacy/update.php?resource=" + resourceId + "/~");
            }
        });

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
