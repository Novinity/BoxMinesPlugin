package io.github.novinity.boxmines.commands;


import io.github.novinity.boxmines.BoxMines;
import io.github.novinity.boxmines.commands.subcommands.*;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CommandManager implements TabCompleter, CommandExecutor {

    public ArrayList<SubCommand> subCommands = new ArrayList<>();

    public CommandManager() {
        subCommands.add(new AddSC());
        subCommands.add(new RemoveSC());
        subCommands.add(new ListSC());
        subCommands.add(new SetRegenTimeSC());
        subCommands.add(new SetSC());
        subCommands.add(new UnsetSC());
        subCommands.add(new RegenerateSC());
        subCommands.add(new TPSC());
        subCommands.add(new ReloadSC());
        subCommands.add(new SetAnnounceRegenSC());
    }

    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            ArrayList<String> possibilities = new ArrayList<String>() {{
                add("add");
                add("remove");
                add("list");
                add("set");
                add("unset");
                add("tp");
                add("regenerate");
                add("setregentime");
                add("setannounceregen");
                add("reload");
            }};
            return new ArrayList<String>() {{
                for (String possibility : possibilities) {
                    if (possibility.startsWith(args[0].toLowerCase())) {
                        add(possibility);
                    }
                }
            }};
        } else if (args.length == 2) {
            if (args[0].equals("remove") || args[0].equals("setregentime") || args[0].equals("set") || args[0].equals("unset") || args[0].equals("regenerate") ||
                    args[0].equals("tp") || args[0].equals("setannounceregen")) {
                try {
                    Set<String> keys = Objects.requireNonNull(BoxMines.getInstance().getConfig().getConfigurationSection("mines")).getKeys(false);
                    return new ArrayList<String>() {{
                        for (String key : keys) {
                            if (key.startsWith(args[1])) {
                                add(key);
                            }
                        }
                    }};
                } catch (Exception e) {
                    return new ArrayList<String>();
                }
            }
        } else if (args.length == 3) {
            if (args[0].equals("add") || args[0].equals("set")) {
                return new ArrayList<String>() {{
                    for (Material m : Material.values()) {
                        if (m.isBlock() && m.name().startsWith(args[2].toUpperCase())) {
                            add(m.name().toLowerCase());
                        }
                    }
                }};
            } else if (args[0].equals("unset")) {
                try {
                    Set<String> keys = Objects.requireNonNull(BoxMines.getInstance().getConfig().getConfigurationSection("mines."+args[1]+".blocks")).getKeys(false);
                    return new ArrayList<String>() {{
                        for (String key : keys) {
                            if (key.startsWith(args[1])) {
                                add(key);
                            }
                        }
                    }};
                } catch (Exception e) {
                    return new ArrayList<String>();
                }
            }
        }

        return null;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender instanceof Player p) {
            if (args.length > 0) {
                for (int i = 0; i < getSubCommands().size(); i++) {
                    if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                        if (!getSubCommands().get(i).getRequiredPermission().isEmpty() || p.hasPermission(getSubCommands().get(i).getRequiredPermission())) {
                            getSubCommands().get(i).perform(p, args);
                        }
                    }
                }
            } else if (args.length == 0) {
                p.sendMessage("------------------");
                for (int i = 0; i < getSubCommands().size(); i++) {
                    p.sendMessage(getSubCommands().get(i).getSyntax() + " - " + getSubCommands().get(i).getDescription());
                }
                p.sendMessage("------------------");
            }
        }

        return true;
    }
}
