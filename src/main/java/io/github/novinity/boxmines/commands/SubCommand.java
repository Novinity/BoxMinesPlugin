package io.github.novinity.boxmines.commands;


import org.bukkit.entity.Player;

public abstract class SubCommand {
    public abstract String getName();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract String getRequiredPermission();

    public abstract void perform(Player player, String[] args);
}

