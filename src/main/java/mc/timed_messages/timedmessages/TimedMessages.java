package mc.timed_messages.timedmessages;


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

public final class TimedMessages extends JavaPlugin {
    private final TimedMessages plugin;

    public TimedMessages(TimedMessages plugin) {
        this.plugin = plugin;
    }

    private FileConfiguration configFile() {
        return plugin.getConfig();
    }

    //Version control
    double plugin_version = 2.0;

    @Override
    public void onEnable() {
        // Plugin startup logic

        this.saveDefaultConfig();

        //this.getCommand("settimedmessage").setExecutor(new settimedmessageCommand(plugin));
        //this.getCommand("setperiod").setExecutor(new setperiodCommand(plugin));
        this.getCommand("timedmessages").setExecutor(this);

        // Setup config - TechnicJelle

        if(getDataFolder().mkdirs()) getLogger().info("Created plugin config directory");
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            try {
                getLogger().info("Creating config file");
                Files.copy(Objects.requireNonNull(getResource("config.yml")), configFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        //Load config from disk - TechnicJelle
        reloadConfig();

        //Load config values into variables - TechnicJelle
        long period = getConfig().getLong("period");

        System.out.println( "\n[]=====[Enabling TimedMessages]=====[]\n" +
                            "| Information:\n" +
                            "|   Name: TimedMessages\n" +
                            "|   Developer: SkyKing_PX\n" +
                            "|   Version: " + plugin_version + "\n" +
                            "| Support:\n" +
                            "|   Discord: SkyKing_PX#3612\n" +
                            "|   GitHub: https://bit.ly/3ZZ8cCF\n" +
                            "[]==================================[]\n");

        for (String msg : this.getConfig().getStringList("messages")){
        List<String> messages = new ArrayList<>();
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
    public void onDisable() {
        // Plugin shutdown logic

        System.out.println( "\n[]=====[Disabling TimedMessages]=====[]\n" +
                            "| Information:\n" +
                            "|   Name: TimedMessages\n" +
                            "|   Developer: SkyKing_PX\n" +
                            "|   Version: " + plugin_version + "\n" +
                            "| Support:\n" +
                            "|   Discord: SkyKing_PX#3612\n" +
                            "|   GitHub: https://bit.ly/3ZZ8cCF\n" +
                            "[]===================================[]\n");
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("tm")){
            if (!sender.hasPermission("tm.reloadconfig")){

                sender.sendMessage(ChatColor.RED + "You have not the permission to run this command!");
                return true;

            }else if (sender.hasPermission("tm.reloadconfig")){
                if (args.length == 0) {

                    sender.sendMessage(ChatColor.AQUA + "Usage: /tm reloadconfig");
                    return true;

                }
                if (args.length > 0) {

                    if (args[0].equalsIgnoreCase("reloadconfig")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aReloading Config!"));
                        this.saveDefaultConfig();
                        this.reloadConfig();
                        return true;
                    }

                    }

                }

            }
            if (!sender.hasPermission("tm.settimedmessage")) {

                sender.sendMessage(ChatColor.RED + "You have not the permission to run this command!");
                return true;

            } else if (sender.hasPermission("tm.settimedmessage")) {

                if (args.length == 0) {

                    sender.sendMessage(ChatColor.AQUA + "Usage: /tm reloadconfig|settmmsg|setperiod");
                    return true;

                }
                if (args.length > 0) {

                    if (args[0].equalsIgnoreCase("settmmsg")) {
                        String messages = args[1].toString();
                        if (!args[1].isEmpty()){

                            try {
                                this.getConfig().set(messages , "messages");
                                this.saveDefaultConfig();
                            }catch (Exception exception) {

                            }

                            return true;


                        }else {
                            sender.sendMessage(ChatColor.AQUA + "Usage: /tm settmmsg <message>");
                    }

                }
            }
            if (!sender.hasPermission("tm.setperiod")) {

                sender.sendMessage(ChatColor.RED + "You have not the permission to run this command!");
                return true;

            } else if (sender.hasPermission("tm.setperiod")) {

                if (args.length == 0) {

                    sender.sendMessage(ChatColor.AQUA + "Usage: /tm reloadconfig|settmmsg|setperiod");
                    return true;

                }
                if (args.length > 0) {

                    if (args[0].equalsIgnoreCase("setperiod")) {
                        String period;
                        if (!args[1].isEmpty()){

                            Long.parseLong(args[1]);

                            if (args[1].) {

                                period = args[1];
                                this.getConfig().set(period , "period");
                                this.saveDefaultConfig();
                                return true;

                            }else{

                                sender.sendMessage(ChatColor.RED + "The period argument is not acceptable!");
                                return true;

                            }



                        }else {
                            sender.sendMessage(ChatColor.AQUA + "Usage: /tm setperiod <period in seconds>");
                        }

                    }
                }

            }

            }return false;
    }

}
