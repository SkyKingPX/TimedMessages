package mc.timed_messages.timedmessages.commands;

import mc.timed_messages.timedmessages.TimedMessages;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class settimedmessageCommand implements CommandExecutor {

    private final TimedMessages plugin;

    public settimedmessageCommand(TimedMessages plugin) {
        this.plugin = plugin;
    }

    private FileConfiguration configFile() {
        return plugin.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender.hasPermission("tm.settimedmessage")) {

        if (command.getName().equalsIgnoreCase("settmmsg") || command.getName().equalsIgnoreCase("settimedmessage")) {

            if (sender instanceof Player p || sender instanceof ConsoleCommandSender c) {

                if (args.length == 0 || args.length > 1) {

                    System.out.println("You have not typed the right amount of arguments! Type /help for help.");
                    return true;
                } else if (args.length == 1) {



                } else {



                }

            }else {
                System.out.println(ChatColor.RED + "This Command can only be runned by a player or the console! Type /help for more info.");
            }

        }

        } else {

            sender.sendMessage(ChatColor.RED + "You have no permission to run this Command!");
            return true;

        }

        return false;
    }
    
}

