package mc.timed_messages.timedmessages.commands;

import mc.timed_messages.timedmessages.TimedMessages;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class timedmessagesCommand implements CommandExecutor {
    private final TimedMessages plugin;

    public timedmessagesCommand(TimedMessages plugin) {
        this.plugin = plugin;
    }

    private FileConfiguration configFile() {
        return plugin.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("tm.reloadconfig")){

            if (args[0].equalsIgnoreCase("reloadconfig")){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aReloading Config!"));

            }

        }


        return true;
    }
}
