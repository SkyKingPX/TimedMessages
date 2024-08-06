package de.skyking_px.timedMessages.additions;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class tmCommandTabCompletion implements TabCompleter {


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 1) {

            String[] arg1 = {"settmmsg", "setperiod", "reloadConfig"};

        }
        return null;
    }
}
