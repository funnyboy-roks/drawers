package com.funnyboyroks.drawers.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class DrawersCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public DrawersCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage("§eUsage: /drawers <nearby|remove|infinite>");
            return true;
        }

        String sub = args[0].toLowerCase();
        switch (sub) {
            case "nearby":
                handleNearby(sender, args);
                break;
            case "remove":
                handleRemove(sender, args);
                break;
            case "infinite":
                handleInfinite(sender, args);
                break;
            default:
                sender.sendMessage("§cUnknown subcommand.");
        }

        return true;
    }

    private void handleNearby(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return;
        }
        if (!sender.hasPermission("drawers.command.drawers.nearby")) {
            sender.sendMessage("§cYou don't have permission.");
            return;
        }

        Player player = (Player) sender;
        double radius = 10; // default
        if (args.length >= 2) {
            try {
                radius = Double.parseDouble(args[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage("§cInvalid radius.");
                return;
            }
        }

        // TODO: loop through all drawers and find nearby ones
        sender.sendMessage("§aSearching for drawers within " + radius + " blocks...");
    }

    private void handleRemove(CommandSender sender, String[] args) {
        if (!sender.hasPermission("drawers.command.drawers.remove")) {
            sender.sendMessage("§cYou don't have permission.");
            return;
        }

        // TODO: Implement radius/global removal & confirmation
        sender.sendMessage("§eThis feature is under development.");
    }

    private void handleInfinite(CommandSender sender, String[] args) {
        if (!sender.hasPermission("drawers.command.drawers.infinite")) {
            sender.sendMessage("§cYou don't have permission.");
            return;
        }

        // TODO: Implement infinite toggle
        sender.sendMessage("§eThis feature is under development.");
    }
}
