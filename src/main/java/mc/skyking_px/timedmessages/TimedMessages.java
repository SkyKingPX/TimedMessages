package mc.skyking_px.timedmessages;


import mc.skyking_px.timedmessages.additions.tmCommandTabCompletion;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static com.sun.org.apache.xml.internal.serializer.utils.Utils.messages;

public final class TimedMessages extends JavaPlugin {

    //Version control
    double plugin_version = 1.0;

    FileConfiguration config = this.getConfig();


    @Override
    public void onEnable() {

        //bStats Metrics

        Metrics metrics = new Metrics(this, 18759);

        //Save config

        this.saveDefaultConfig();

        //register Commands and Tab Completion

        this.getCommand("tm").setExecutor(new TimedMessages());
        this.getCommand("tm").setTabCompleter(new tmCommandTabCompletion());

        // Setup config - TechnicJelle

        /*if(getDataFolder().mkdirs()) getLogger().info("Created plugin config directory");
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            try {
                getLogger().info("Creating config file");
                Files.copy(Objects.requireNonNull(getResource("config.yml")), configFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        this.getConfig();

        long period = getConfig().getLong("period");
        if (period < 1){
            System.out.println("Period variable is not defined. Please set a period with '/tm setperiod'.");
        } else if (period > 0) {
            BukkitRunnable runnable = new BukkitRunnable() {
                int var;
                @Override
                public void run() {

                    String message = config.getString(messages.);
                    BukkitRunnable.runTaskTimer(this, Bukkit.broadcastMessage(message), 20L * period);
                }
            };

            //for (String msg : this.getConfig().getStringList("messages")){

            //    messages.add(this.getConfig().getStringList("messages" + ChatColor.translateAlternateColorCodes('&', msg )));
            //    BukkitScheduler msgScheduler = Bukkit.getScheduler();
            //    msgScheduler.runTaskTimer(this, Bukkit.broadcastMessage(messages.get(new Random().nextInt(messages.size())), 20L * period));
            }

        System.out.println( "\n[]=====[Enabling TimedMessages]=====[]\n" +
                "| Information:\n" +
                "|   Name: TimedMessages\n" +
                "|   Developer: SkyKing_PX\n" +
                "|   Version: " + plugin_version + "\n" +
                "| Support:\n" +
                "|   Discord: SkyKing_PX\n" +
                "|      Server: https://bit.ly/3Ne5O6A\n" +
                "|   GitHub: https://bit.ly/3ZZ8cCF\n" +
                "[]==================================[]\n");


    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("tm")) {
            if (sender.hasPermission("tm.basic")){

                List<String> arguments = new ArrayList<>();
                arguments.add("settmmsg");
                arguments.add("setperiod");
                arguments.add("reloadConfig");

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
                            this.getConfig();
                            this.saveConfig();
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

                                String messages = args[1];
                                this.getConfig().set(messages, "messages");
                                this.saveConfig();
                                sender.sendMessage(ChatColor.GREEN + "Data stored! Run /tm reloadconfig to reload the plugin.");
                                return true;


                            } else {
                                sender.sendMessage(ChatColor.AQUA + "Usage: /tm settmmsg <message in double quotation marks>");
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
                        this.getConfig().set(messages.targetMessage ,null);

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
                            String period;
                            if (!args[1].isEmpty()) {

                                this.getConfig().getString("period", args[1]);
                                saveConfig();
                                sender.sendMessage(ChatColor.GREEN + "Data stored! Make sure to use a Integer here. Run /tm reloadconfig to reload the plugin.");
                                return true;

                            } else {
                                sender.sendMessage(ChatColor.AQUA + "Usage: /tm setperiod <period in seconds>");
                            }

                        } else sender.sendMessage(ChatColor.AQUA + "Usage: /tm reloadconfig | settmmsg | setperiod");
                    }

                }
            }
        }
        }return false;
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic

        System.out.println( "\n[]=====[Disabling TimedMessages]=====[]\n" +
                "| Information:\n" +
                "|   Name: TimedMessages\n" +
                "|   Developer: SkyKing_PX\n" +
                "|   Version: " + plugin_version + "\n" +
                "| Support:\n" +
                "|   Discord: SkyKing_PX\n" +
                "|      Server: https://bit.ly/3Ne5O6A\n" +
                "|   GitHub: https://bit.ly/3ZZ8cCF\n" +
                "[]===================================[]\n");
    }

}


