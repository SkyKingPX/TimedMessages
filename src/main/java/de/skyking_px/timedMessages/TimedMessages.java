package de.skyking_px.timedMessages;


import de.skyking_px.timedMessages.additions.tmCommandTabCompletion;
import org.bstats.bukkit.Metrics;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public final class TimedMessages extends JavaPlugin implements Listener {

    //Version control
    double plugin_version = 1.0;

    public void loadConfiguration() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }

    FileConfiguration config = this.getConfig();

    @Override
    public void onEnable() {

        // Setup config - Code by TechnicJelle

        if(getDataFolder().mkdirs()) getLogger().info("Created plugin config directory");
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            try {
                getLogger().info("Creating config file");
                this.getConfig().options().copyDefaults(true);
                Files.copy(Objects.requireNonNull(getResource("config.yml")), configFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //bStats Metrics

        int pluginId = 18759;
        Metrics metrics = new Metrics(this, pluginId);

        //register Commands, Tab Completion and Events

        this.getCommand("tm").setExecutor(this);
        this.getCommand("tm").setTabCompleter(new tmCommandTabCompletion());

        this.getServer().getPluginManager().registerEvents(this, this);

        // Config Defaults

        this.getConfig().addDefault("messages", "This is a default message. For now you can only change it in the config.yml.");
        this.getConfig().addDefault("period", 600);

        loadConfiguration();

        long period = getConfig().getLong("period");
        if (period < 1){
            this.getLogger().info("Period variable is not or defined wrong. Please set a valid period with '/tm setperiod'.");
        } else if (period > 0) {
            /*BukkitRunnable runnable = new BukkitRunnable() {
                int var;
                @Override
                public void run() {
                    String msgpath = "messages." + var;
                    String message = config.getString(msgpath);
                    BukkitRunnable.runTaskTimer(this, Bukkit.broadcastMessage(message), 20L * period);
                }
            };*/
            /*for (Object messages : this.getConfig().getList("messages")).add(this.getConfig().getStringList("messages" + ChatColor.translateAlternateColorCodes('&', msg)));
            {
                BukkitScheduler msgScheduler = Bukkit.getScheduler();
                msgScheduler.runTaskTimer(this, Bukkit.broadcastMessage(messages.get(new Random().nextInt(messages.size())), 20L * period));
            }*/
        }

        this.getLogger().info( "\n[]=====[Enabling TimedMessages]=====[]\n" +
                "| Information:\n" +
                "|   Name: TimedMessages\n" +
                "|   Developer: SkyKing_PX\n" +
                "|   Version: " + plugin_version + "\n" +
                "| Support:\n" +
                "|   Discord: SkyKing_PX\n" +
                "|      Server: https://bit.ly/sk_px-dc\n" +
                "|   GitHub: https://bit.ly/sk_px-gh\n" +
                "[]==================================[]\n");


    }

    // isInt check
    public boolean isInt(String str) {
        try {
            Integer.parseInt(str);
        } catch (Throwable e) {
            return false;
        }
        return true;
    }
    @EventHandler
    public void PeriodJoinWarning(PlayerJoinEvent joinEvent) {
        Player target = joinEvent.getPlayer();

        long period = getConfig().getLong("period");
        if (period < 1){
            if (target.hasPermission("tm.notify"))
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c[&r&3Timed&r&2Messages&r&c]&r&c Period variable is not or defined wrong. Please set a valid period with '/tm setperiod'."));
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("tm")) {

            int configVersion = (int) this.getConfig().get("config-version");

            if (configVersion == 1){
                if (!sender.hasPermission("tm.reloadconfig")) {

                    sender.sendMessage(ChatColor.RED + "You have not the permission to run this command!");
                    return true;

                } else if (sender.hasPermission("tm.reloadconfig")) {
                    if (args.length == 0) {

                        sender.sendMessage(ChatColor.AQUA + "Usage: /tm reloadconfig | settmmsg | setperiod");
                        return true;

                    }
                    if (args.length > 0) {

                        if (args[0].equalsIgnoreCase("reloadconfig")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aReloading Config!"));
                            loadConfiguration();
                            this.reloadConfig();
                            return true;
                        } else sender.sendMessage(ChatColor.AQUA + "Usage: /tm reloadconfig | settmmsg | setperiod");

                    }

                }


                if (!sender.hasPermission("tm.settimedmessage")) {

                    sender.sendMessage(ChatColor.RED + "You have not the permission to run this command!");
                    return true;

                } else if (sender.hasPermission("tm.settimedmessage")) {

                    if (args.length == 0) {

                        sender.sendMessage(ChatColor.AQUA + "Usage: /tm reloadconfig | settmmsg | setperiod");
                        return true;

                    }
                    if (args.length > 0) {

                        if (args[0].equalsIgnoreCase("settmmsg")) {

                            if (!args[1].isEmpty()) {

                                StringJoiner m = new StringJoiner(" ");
                                for (int i = 1; i < args.length; i++)
                                    m.add(args[i]);
                                this.getConfig().addDefault("messages", m);
                                this.getConfig().;
                                this.saveConfig();
                                sender.sendMessage(ChatColor.GREEN + "Data saved! Run /tm reloadconfig to reload the plugin.");
                                return true;


                            } else {
                                sender.sendMessage(ChatColor.AQUA + "Usage: /tm settmmsg <message>");
                            }

                        } else sender.sendMessage(ChatColor.AQUA + "Usage: /tm reloadconfig | settmmsg | setperiod");
                    }

                /* if (!sender.hasPermission("tm.remtmmsg")) {

                    sender.sendMessage(ChatColor.RED + "You have not the permission to run this command!");
                    return true;

                } else if (sender.hasPermission("tm.remtmmsg")) {
                    if (args.length == 0) {

                        sender.sendMessage(ChatColor.AQUA + "Usage: /tm reloadconfig | settmmsg | setperiod");
                        return true;

                    }
                    if (args.length > 0) {

                        if (args[0].equalsIgnoreCase("remtmmsg")) {

                            String targetMessage = "messages." + Integer.parseInt(args[1]);
                            this.getConfig().addDefault("messages", "");

                        }else {

                            sender.sendMessage(ChatColor.AQUA + "Usage: /tm remtmmsg <Number of the message>");

                        }

                    }
                } */
                    if (!sender.hasPermission("tm.setperiod")) {

                        sender.sendMessage(ChatColor.RED + "You have not the permission to run this command!");
                        return true;

                    } else if (sender.hasPermission("tm.setperiod")) {

                        if (args.length == 0) {

                            sender.sendMessage(ChatColor.AQUA + "Usage: /tm reloadconfig | settmmsg | setperiod");
                            return true;

                        }
                        if (args.length > 0) {

                            if (args[0].equalsIgnoreCase("setperiod")) {
                                if (!args[1].isEmpty()) {
                                    if (isInt(args[1])) {

                                        this.getConfig().getString("period", args[1]);
                                        saveConfig();
                                        sender.sendMessage(ChatColor.GREEN + "Data saved! Run /tm reloadconfig to reload the plugin.");
                                        return true;

                                    } else {
                                        sender.sendMessage(ChatColor.RED + "A Number is required here!");
                                        return true;
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.AQUA + "Usage: /tm setperiod <period in seconds>");
                                }

                            } else
                                sender.sendMessage(ChatColor.AQUA + "Usage: /tm reloadconfig | settmmsg | setperiod");
                        }

                    }
                }
            } else sender.sendMessage(ChatColor.RED + "Seems like your config is broken or the Config version isn't supported yet.");
        }
        return false;
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic

        this.getLogger().info( "\n[]=====[Disabling TimedMessages]=====[]\n" +
                "| Information:\n" +
                "|   Name: TimedMessages\n" +
                "|   Developer: SkyKing_PX\n" +
                "|   Version: " + plugin_version + "\n" +
                "| Support:\n" +
                "|   Discord: SkyKing_PX\n" +
                "|      Server: https://bit.ly/sk_px-dc\n" +
                "|   GitHub: https://bit.ly/sk_px-gh\n" +
                "[]===================================[]\n");
    }

}


