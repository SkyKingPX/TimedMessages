package mc.timed_messages.timedmessages.commands;

import mc.timed_messages.timedmessages.TimedMessages;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class setperiodCommand implements CommandExecutor {
    private final TimedMessages plugin;

    public setperiodCommand(TimedMessages plugin) {
        this.plugin = plugin;
    }

    private FileConfiguration configFile() {
        return plugin.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("tm.setperiod")) {
            if (args.length == 0) {

                System.out.println("You have not typed any arguments! Type /help for help.");

             } else {

                if (args.length == 1) {

                //long ConfigPeriod;
                //ConfigPeriod = configFile().set();
                //configFile().save(String.valueOf(ConfigPeriod));
                }
            {
            }
            return true;
        }

        } else {
            sender.sendMessage(ChatColor.RED + "You have no permission to run this Command!");
            return true;
        }
        return false;
    }

}
