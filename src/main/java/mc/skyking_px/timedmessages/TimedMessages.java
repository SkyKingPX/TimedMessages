package mc.skyking_px.timedmessages;


import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

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

        //register Commands

        this.getCommand("tm").setExecutor(new TimedMessages());

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



        //Load config from disk - TechnicJelle

        //reloadConfig();
        this.getConfig();

        //Load config values into variables - TechnicJelle
        long period = getConfig().getLong("period");

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

        int messagescount = plugin.getConfig().

        int randomNum = ThreadLocalRandom.current().nextInt(1, messagescount + 1);

        for (String msg : this.getConfig().getStringList("messages")){
            ArrayList messages = TimedMessages.getConfig().getString("messages");
            messages.add(ChatColor.translateAlternateColorCodes('&', msg ));
            //messages.add("§l§4[§6Warnung§4] §r§lWenn du die Regeln nicht befolgst kommt es zu einem §cBan!");
            //messages.add("§l§4[§6Warnung§4] §r§lLasse keine AFK-Farmen eingeschaltet, wenn du aus den Chunks gehst oder der Server gestoppt/neugestartet wird!");
            //messages.add("§l§4[§9Tipp§4] §r§lDie Spielregel Inventar behalten ist §caktiviert§r§l!");
            //messages.add("§l§4[§9Tipp§4] §r§lVerzaubere deine Schuhe mit §cSeelenläufer§r§l(§cSoul Speed§r§l), um sich im Nether-Tunnel §cschneller §r§lzu bewegen!");
            //messages.add("§l§4[§9Tipp§4] §r§lVerzaubere deinen Dreizack mit §cSog§r§l(§cRiptide§r§l), um in der Oberwelt schneller zu Reisen!");
            //messages.add("§l§4[§9Tipp§4] §r§lGucke dir die WebMap von jedem Server an:§r§9§n zusammenhub.myftp.org:25564");
            //messages.add("§l§4[§2Werbung§4] §r§lMöchtest du ein Projekt mit deinen Freunden starten? Hoste Deinen kostenlosen Server: https://github.com/SkyKingPX/Minecraft-Server-Projekte/issues/new");

            Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> Bukkit.broadcastMessage(messages.get(new Random().nextInt(messages.size()))), 0L, 20L * period); //standard: 20L * 600L - testing: 20L * 20L
        }
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("tm")){
            if (!sender.hasPermission("tm.reloadconfig")){

                sender.sendMessage(ChatColor.RED + "You have not the permission to run this command!");
                return true;

            }else if (sender.hasPermission("tm.reloadconfig")){
                if (args.length == 0) {

                    sender.sendMessage(ChatColor.AQUA + "Usage: /tm reloadconfig|settmmsg|setperiod|remtmmsg");
                    return true;

                }
                if (args.length > 0) {

                    if (args[0].equalsIgnoreCase("reloadconfig")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aReloading Config!"));
                        this.getConfig();
                        this.saveConfig();
                        this.reloadConfig();
                        return true;
                    }

                }

            }


        if (!sender.hasPermission("tm.settimedmessage")) {

            sender.sendMessage(ChatColor.RED + "You have not the permission to run this command!");
            return true;

        } else if (sender.hasPermission("tm.settimedmessage")) {

            if (args.length == 0) {

                sender.sendMessage(ChatColor.AQUA + "Usage: /tm reloadconfig|settmmsg|setperiod|remtmmsg");
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

                }
            }

            /* if (!sender.hasPermission("tm.remtmmsg")) {

                sender.sendMessage(ChatColor.RED + "You have not the permission to run this command!");
                return true;

            } else if (sender.hasPermission("tm.remtmmsg")) {
                if (args.length == 0) {

                    sender.sendMessage(ChatColor.AQUA + "Usage: /tm reloadconfig|settmmsg|setperiod|remtmmsg");
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

                        sender.sendMessage(ChatColor.AQUA + "Usage: /tm reloadconfig|settmmsg|setperiod|remtmmsg");
                        return true;

                    }
                    if (args.length > 0) {

                        if (args[0].equalsIgnoreCase("setperiod")) {
                            String period;
                            if (!args[1].isEmpty()) {

                                //this.getConfig().set(period, "period");
                                if (config.contains("period")){
                                    config.set("period", args[1]);
                                }else config.addDefault("period", args[1]);
                                config.options().copyDefaults(true);
                                saveConfig();
                                sender.sendMessage(ChatColor.GREEN + "Data stored! Make sure to use a Integer here. Run /tm reloadconfig to reload the plugin.");
                                return true;

                            } else {
                                sender.sendMessage(ChatColor.AQUA + "Usage: /tm setperiod <period in seconds>");
                            }

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


