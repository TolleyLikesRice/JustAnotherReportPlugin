package tolley.jarp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Main extends JavaPlugin {
    InventoryListener InvListener = new InventoryListener();
    FileConfiguration config;
    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        config = this.getConfig();
        getLogger().info("|+++++++++++++++++++++++++++++++++++++|");
        getLogger().info("| JustAnotherReportPlugin by Tolley   |");
        getLogger().info("| Version 1.0.0                       |");
        getLogger().info("| ENABLED                             |");
        getLogger().info("|+++++++++++++++++++++++++++++++++++++|");
        this.getCommand("report").setExecutor(new CommandReport());
        getServer().getPluginManager().registerEvents(InvListener, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("|+++++++++++++++++++++++++++++++++++++|");
        getLogger().info("| JustAnotherReportPlugin by Tolley   |");
        getLogger().info("| Version 1.0.0                       |");
        getLogger().info("| DISABLED                            |");
        getLogger().info("|+++++++++++++++++++++++++++++++++++++|");
    }

    public class CommandReport implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 0) {
                        player.sendMessage(ChatColor.RED + "[JARP] " + ChatColor.WHITE + "Please specify a player to report!");
                    return true;
                } else if (args[0].equalsIgnoreCase("info")) {
                    player.sendMessage(ChatColor.RED + "[JARP] " + ChatColor.WHITE + "JustAnotherReportPlugin by Tolley - Version 1.0.0");
                    return true;
                } else if (args[0].equalsIgnoreCase("help")) {

                    player.sendMessage(ChatColor.RED + "[JARP] " + ChatColor.WHITE + "Commands\n" + ChatColor.RED + "[JARP] " + ChatColor.WHITE + "/report <username> - Reports a person\n" + ChatColor.RED + "[JARP] " + ChatColor.WHITE + "/report info - Shows plugin info");
                    return true;
                } else {
                    if (!player.hasPermission("report.report")) {
                        player.sendMessage(ChatColor.RED + "[JARP] " + ChatColor.WHITE + "You do not have permission to report people!");
                        return true;
                    }
                    try {
                        URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + args[0]);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("GET");
                        int status = con.getResponseCode();
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(con.getInputStream()));
                        String inputLine;
                        StringBuffer content = new StringBuffer();
                        while ((inputLine = in.readLine()) != null) {
                            content.append(inputLine);
                        }
                        in.close();
                        con.disconnect();
                        JSONObject uuidJSON = (JSONObject) new JSONParser().parse(content.toString());
                        String uuid = (String) uuidJSON.get("id");
                        String reporteeName = (String) uuidJSON.get("name");
                        InvListener.getReportInfo(reporteeName, uuid, player, config.getString("webhook"));
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                        player.sendMessage(ChatColor.RED + "[JARP] " + ChatColor.WHITE + "I could not find that player");
                        return true;
                    }

                        Inventory inv = Bukkit.createInventory(null, 9, "Report");
                        ItemStack pick = new ItemStack(Material.DIAMOND_PICKAXE);
                        ItemMeta pickItemMeta = pick.getItemMeta();
                        pickItemMeta.setDisplayName(ChatColor.RED + "Grief outside of SMP");
                        pick.setItemMeta(pickItemMeta);

                        ItemStack yellowWool = new ItemStack(Material.PUMPKIN);
                        ItemMeta yellowWoolMeta = yellowWool.getItemMeta();
                        yellowWoolMeta.setDisplayName(ChatColor.YELLOW + "Building unwanted symbols");
                        yellowWool.setItemMeta(yellowWoolMeta);

                        ItemStack barrier = new ItemStack(Material.BARRIER);
                        ItemMeta barrierMeta = barrier.getItemMeta();
                        barrierMeta.setDisplayName(ChatColor.RED + "Hacking");
                        barrier.setItemMeta(barrierMeta);

                        ItemStack sword = new ItemStack(Material.WOODEN_SWORD);
                        ItemMeta swordMeta = sword.getItemMeta();
                        swordMeta.setDisplayName(ChatColor.RED + "Bullying or Hate Speech");
                        sword.setItemMeta(swordMeta);

                        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                        ItemMeta headMeta = head.getItemMeta();
                        headMeta.setDisplayName(ChatColor.RED + "Inappropriate Skin or Username");
                        head.setItemMeta(headMeta);
                        ItemStack cobble = new ItemStack(Material.COBBLESTONE);
                        ItemMeta cobbleMeta = cobble.getItemMeta();
                        cobbleMeta.setDisplayName(ChatColor.GRAY + "Other");
                        cobble.setItemMeta(cobbleMeta);

                    ItemStack shield = new ItemStack(Material.SHIELD);
                    ItemMeta shieldMeta = shield.getItemMeta();
                    shieldMeta.setDisplayName(ChatColor.DARK_RED + "Staff Abusing Permissions");
                    shield.setItemMeta(shieldMeta);

                        inv.addItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
                        inv.setItem(1, shield);
                        inv.setItem(8, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
                        inv.setItem(2, pick);
                        inv.setItem(3, barrier);
                        inv.setItem(4, yellowWool);
                        inv.setItem(5, head);
                        inv.setItem(6, sword);
                        inv.setItem(7, cobble);
                        player.openInventory(inv);



                        return true;

                }
            } else {
                getLogger().info(ChatColor.RED + "[JARP] " + ChatColor.WHITE + "You cannot use JARP from console!");
                return true;
            }
        }
    }
}

