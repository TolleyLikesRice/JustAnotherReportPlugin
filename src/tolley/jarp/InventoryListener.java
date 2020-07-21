package tolley.jarp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;

public class InventoryListener implements Listener {
    String webhookURL;
    String reporteeName;
    String uuid;
    Player player;

    public void getReportInfo(String reportee, String reporteeUUID, Player reporterPlayer, String discordWebhookURL) {
        reporteeName = reportee;
        uuid = reporteeUUID;
        player = reporterPlayer;
        webhookURL = discordWebhookURL;
    }

    public void sendDiscordWebhook(String reason) {
        try {
            String jsonInputString = "{\"embeds\":[{\"color\":16713524,\"timestamp\":\"" + Instant.now().toString() + "\",\"footer\":{\"text\":\"Reported by " + player.getName() + "\"},\"thumbnail\":{\"url\":\"https://crafatar.com/avatars/" + uuid + "\"},\"image\":{\"url\":\"https://crafatar.com/renders/body/" + uuid + "\"},\"author\":{\"name\":\"JustAnotherReportPlugin by Tolley\"},\"fields\":[{\"name\":\"Player\",\"value\":\"" + reporteeName + "\"},{\"name\":\"Reason\",\"value\":\"" + reason + "\"},{\"name\":\"UUID\",\"value\":\"" + uuid + "\"}]}]}";
            URL url = new URL(webhookURL);
            HttpURLConnection con2 = (HttpURLConnection) url.openConnection();
            con2.setRequestMethod("POST");
            con2.setRequestProperty("Content-Type", "application/json");
            con2.setRequestProperty("User-Agent", "PostmanRuntime/7.24.1");
            con2.setDoOutput(true);
            try(OutputStream os = con2.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            int status2 = con2.getResponseCode();
            con2.disconnect();
        } catch (IOException e){
            e.printStackTrace();
            player.sendMessage("An error occurred while sending the report");
        }
    }

    @EventHandler
    public void onPlayerClickInventory(InventoryClickEvent e) {
        if (e.getView().getTitle().equals("Report")) {
            if (e.getCurrentItem().getItemMeta() != null) {
                if (e.getCurrentItem().getItemMeta().getDisplayName() != null) {
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "Grief outside of SMP")) {
                        //code
                        Player p = (Player) e.getWhoClicked();
                        e.getWhoClicked().closeInventory();
                        p.sendMessage("Reporting " + reporteeName + " for \"Greifing outside of SMP\"");
                        sendDiscordWebhook("Greifing outside of SMP");
                        e.setCancelled(true);
                    } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Building unwanted symbols")) {
                        //code
                        Player p = (Player) e.getWhoClicked();
                        e.getWhoClicked().closeInventory();
                        p.sendMessage("Reporting " + reporteeName + " for \"Building unwanted symbols\"");
                        sendDiscordWebhook("Building unwanted symbols");
                        e.setCancelled(true);
                    } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "Hacking")) {
                        //code
                        Inventory inv = Bukkit.createInventory(null, 9, "Report: Hacks");
                        Player p = (Player) e.getWhoClicked();
                        e.getWhoClicked().closeInventory();
                        e.setCancelled(true);

                        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
                        ItemMeta swordMeta = sword.getItemMeta();
                        swordMeta.setDisplayName(ChatColor.RED + "Kill Aura");
                        sword.setItemMeta(swordMeta);

                        ItemStack elytra = new ItemStack(Material.ELYTRA);
                        ItemMeta elytraMeta = elytra.getItemMeta();
                        elytraMeta.setDisplayName(ChatColor.RED + "Fly");
                        elytra.setItemMeta(elytraMeta);

                        ItemStack shield = new ItemStack(Material.SHIELD);
                        ItemMeta shieldMeta = shield.getItemMeta();
                        shieldMeta.setDisplayName(ChatColor.RED + "AntiKB");
                        shield.setItemMeta(shieldMeta);

                        ItemStack water = new ItemStack(Material.WATER_BUCKET);
                        ItemMeta waterMeta = water.getItemMeta();
                        waterMeta.setDisplayName(ChatColor.RED + "Jesus");
                        water.setItemMeta(waterMeta);

                        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                        ItemMeta headMeta = head.getItemMeta();
                        headMeta.setDisplayName(ChatColor.YELLOW + "Derp");
                        head.setItemMeta(headMeta);

                        ItemStack barrier = new ItemStack(Material.BARRIER);
                        ItemMeta barrierMeta = barrier.getItemMeta();
                        barrierMeta.setDisplayName(ChatColor.GRAY + "Other Malicious Hacks");
                        barrier.setItemMeta(barrierMeta);

                        ItemStack cobble = new ItemStack(Material.COBBLESTONE);
                        ItemMeta cobbleMeta = cobble.getItemMeta();
                        cobbleMeta.setDisplayName(ChatColor.DARK_GRAY + "Other Non-Malicious Hacks");
                        cobble.setItemMeta(cobbleMeta);

                        inv.addItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
                        inv.setItem(1, sword);
                        inv.setItem(8, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
                        inv.setItem(2, elytra);
                        inv.setItem(3, shield);
                        inv.setItem(4, water);
                        inv.setItem(5, head);
                        inv.setItem(6, barrier);
                        inv.setItem(7, cobble);
                        player.openInventory(inv);
                    } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "Bullying or Hate Speech")) {
                        //code
                        Player p = (Player) e.getWhoClicked();
                        e.getWhoClicked().closeInventory();
                        p.sendMessage("Reporting " + reporteeName + " for \"Bullying or Hate Speech\"");
                        sendDiscordWebhook("Bullying or Hate Speech");
                        e.setCancelled(true);
                    } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "Inappropriate Skin or Username")) {
                        //code
                        Player p = (Player) e.getWhoClicked();
                        e.getWhoClicked().closeInventory();
                        p.sendMessage("Reporting " + reporteeName + " for \"Inappropriate Skin or Username\"");
                        sendDiscordWebhook("Inappropriate Skin or Username");
                        e.setCancelled(true);
                    } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Other")) {
                        //code
                        Player p = (Player) e.getWhoClicked();
                        e.getWhoClicked().closeInventory();
                        p.sendMessage("Reporting " + reporteeName + " for \"Other\"");
                        sendDiscordWebhook("Other");
                        e.setCancelled(true);
                    } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "Staff Abusing Permissions")) {
                        //code
                        Player p = (Player) e.getWhoClicked();
                        e.getWhoClicked().closeInventory();
                        p.sendMessage("Reporting " + reporteeName + " for \"Staff Abusing Permissions\"");
                        sendDiscordWebhook("Staff Abusing Permissions");
                        e.setCancelled(true);
                    } else {
                        e.setCancelled(true);
                    }
                } else {
                    e.setCancelled(true);
                }
            } else {
                e.setCancelled(true);
            }
        } else if (e.getView().getTitle().equals("Report: Hacks")) {
            if (e.getCurrentItem().getItemMeta() != null) {
                if (e.getCurrentItem().getItemMeta().getDisplayName() != null) {
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "Kill Aura")) {
                        //code
                        Player p = (Player) e.getWhoClicked();
                        e.getWhoClicked().closeInventory();
                        p.sendMessage("Reporting " + reporteeName + " for \"Hacking: Kill Aura\"");
                        sendDiscordWebhook("Hacking: Kill Aura");
                        e.setCancelled(true);
                    } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "Fly")) {
                        //code
                        Player p = (Player) e.getWhoClicked();
                        e.getWhoClicked().closeInventory();
                        p.sendMessage("Reporting " + reporteeName + " for \"Hacking: Fly\"");
                        sendDiscordWebhook("Hacking: Fly");
                        e.setCancelled(true);
                    } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "AntiKB")) {
                        //code
                        Player p = (Player) e.getWhoClicked();
                        e.getWhoClicked().closeInventory();
                        p.sendMessage("Reporting " + reporteeName + " for \"Hacking: AntiKB\"");
                        sendDiscordWebhook("Hacking: AntiKB");
                        e.setCancelled(true);
                    } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "Jesus")) {
                        //code
                        Player p = (Player) e.getWhoClicked();
                        e.getWhoClicked().closeInventory();
                        p.sendMessage("Reporting " + reporteeName + " for \"Hacking: Jesus\"");
                        sendDiscordWebhook("Hacking: Jesus");
                        e.setCancelled(true);
                    } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Derp")) {
                        //code
                        Player p = (Player) e.getWhoClicked();
                        e.getWhoClicked().closeInventory();
                        p.sendMessage("Reporting " + reporteeName + " for \"Hacking: Derp\"");
                        sendDiscordWebhook("Hacking: Derp");
                        e.setCancelled(true);
                    } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Other Malicious Hacks")) {
                        //code
                        Player p = (Player) e.getWhoClicked();
                        e.getWhoClicked().closeInventory();
                        p.sendMessage("Reporting " + reporteeName + " for \"Hacking: Other Malicious Hacks\"");
                        sendDiscordWebhook("Hacking: Other Malicious Hacks");
                        e.setCancelled(true);
                    } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_GRAY + "Other Non-Malicious Hacks")) {
                        //code
                        Player p = (Player) e.getWhoClicked();
                        e.getWhoClicked().closeInventory();
                        p.sendMessage("Reporting " + reporteeName + " for \"Hacking: Other Non-Malicious Hacks\"");
                        sendDiscordWebhook("Hacking: Other Non-Malicious Hacks");
                        e.setCancelled(true);
                    } else {
                        e.setCancelled(true);
                    }
                } else {
                    e.setCancelled(true);
                }
            } else {
                e.setCancelled(true);
            }
        }
    }

}

