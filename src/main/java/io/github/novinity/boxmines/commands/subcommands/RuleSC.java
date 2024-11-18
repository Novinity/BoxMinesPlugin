package io.github.novinity.boxmines.commands.subcommands;

import io.github.novinity.boxmines.BoxMines;
import io.github.novinity.boxmines.commands.SubCommand;
import io.github.novinity.boxmines.utils.RegenIntervals;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.logging.Level;

public class RuleSC extends SubCommand {
    @Override
    public String getName() {
        return "rule";
    }

    @Override
    public String getDescription() {
        return "Set a rule for a mine";
    }

    @Override
    public String getSyntax() {
        return "/bm rule <mineName> set <ruleName> <value>";
    }

    @Override
    public String getRequiredPermission() {
        return "boxmines.rule";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("boxmines.rule")) {
            player.sendMessage(ChatColor.RED + "No permission!");
            return;
        }

        if (args.length == 1) {
            ArrayList<String> rules = new ArrayList<>() {{
               add("announceRegen");
               add("regenTime");
               add("resetWhenEmpty");
            }};
            player.sendMessage("List of rules:");
            String f = "";
            int i = 0;
            for (String rule : rules) {
                if (i == rules.toArray().length - 1)
                    f += rule;
                else
                    f += rule + ", ";
                i++;
            }
            player.sendMessage(f);
            return;
        }
        if (args.length < 5) {
            player.sendMessage(ChatColor.RED + "Missing arguments!");
            return;
        }

        String mineName = args[1];
        String toDo = args[2];
        String rule = args[3];
        String value = args[4];

        if (value.isEmpty()) {
            player.sendMessage(ChatColor.RED + "Empty value!");
            return;
        }

        boolean didSomething = false;
        switch (rule.toLowerCase()) {
            case "announceregen":
                didSomething = true;
                try {
                    if (!value.toLowerCase().equals("true") && !value.toLowerCase().equals("false")) {
                        player.sendMessage(ChatColor.RED + "Value options are 'true' or 'false'.");
                        return;
                    }

                    Boolean curVal = BoxMines.getInstance().getConfig().getBoolean("mines."+mineName+".announceRegen");
                    if (curVal && value.toLowerCase().equals("true")) {
                        player.sendMessage(ChatColor.GOLD + mineName + ChatColor.GREEN + " is already announcing regeneration!");
                        return;
                    } else if (!curVal && value.toLowerCase().equals("false")) {
                        player.sendMessage(ChatColor.GOLD + mineName + ChatColor.GREEN + " is already not announcing regeneration!");
                        return;
                    }

                    String val = value.toLowerCase().equals("false") ? "no longer" : "now";

                    BoxMines.getInstance().getConfig().set("mines."+args[1]+".announceRegen", value.toLowerCase().equals("true"));
                    player.sendMessage(ChatColor.GOLD + args[1] + ChatColor.GREEN + " is " + val + " announcing regeneration");
                } catch (Exception e) {
                    BoxMines.getInstance().getConfig().set("mines."+args[1]+".announceRegen", true);
                    player.sendMessage(ChatColor.GOLD + args[1] + ChatColor.GREEN + " is now announcing regeneration");
                }
                break;
            case "regentime":
                didSomething = true;
                Integer timeInSeconds;

                try {
                    timeInSeconds = Integer.valueOf(value);
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "That is not a valid amount of time! The time must be a number.");
                    return;
                }

                if (BoxMines.getInstance().getConfig().get("mines."+mineName) == null) {
                    player.sendMessage(ChatColor.RED + "That mine does not exist!");
                    return;
                }

                if (timeInSeconds <= 0) {
                    RegenIntervals.destroyRegenInterval(mineName);
                    player.sendMessage(ChatColor.AQUA + mineName + ChatColor.GREEN + " will no longer automatically regenerate.");
                } else {
                    RegenIntervals.createRegenInterval(mineName, timeInSeconds);
                    player.sendMessage(ChatColor.AQUA + mineName + ChatColor.GREEN + " will now automatically regenerate every " +
                            ChatColor.GOLD + timeInSeconds.toString() + ChatColor.GREEN + " seconds.");
                }

                BoxMines.getInstance().getConfig().set("mines."+mineName+".regenInterval", timeInSeconds);
                break;
            case "resetwhenempty":
                didSomething = true;
                if (!value.toLowerCase().equals("true") && !value.toLowerCase().equals("false")) {
                    player.sendMessage(ChatColor.RED + "Value options are 'true' or 'false'.");
                    return;
                }

                Boolean curVal = BoxMines.getInstance().getConfig().getBoolean("mines."+mineName+".resetWhenEmpty");
                if (curVal && value.toLowerCase().equals("true")) {
                    player.sendMessage(ChatColor.GOLD + mineName + ChatColor.GREEN + " is already resetting when empty!");
                    return;
                } else if (!curVal && value.toLowerCase().equals("false")) {
                    player.sendMessage(ChatColor.GOLD + mineName + ChatColor.GREEN + " is already not resetting when empty!");
                    return;
                }

                BoxMines.getInstance().getConfig().set("mines."+mineName+".resetWhenEmpty", value.toLowerCase().equals("true"));

                if (value.toLowerCase().equals("true")) {
                    RegenIntervals.createClearInterval(mineName, 1);
                } else {
                    RegenIntervals.destroyClearInterval(mineName);
                }

                break;
            default:
                player.sendMessage(ChatColor.RED + "Rule does not exist.");
                return;
        }

        BoxMines.getInstance().saveConfig();

        if (didSomething)
            player.sendMessage(ChatColor.GREEN + "Successfully set rule " + ChatColor.GOLD + rule + ChatColor.GREEN + " on " +
                    ChatColor.GOLD + mineName + ChatColor.GREEN + " to " + ChatColor.GOLD + value + ChatColor.GREEN + ".");
    }
}
