package me.jj9playz.rpgessentials.events;

import me.jj9playz.rpgessentials.RPGEssentials;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Date;

public class BanGuiEvents implements Listener {

    RPGEssentials main = JavaPlugin.getPlugin(RPGEssentials.class);

    boolean chatReason = false;

    public String banReason;

    public Player whoisban;

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        String msg = e.getMessage();
        if(chatReason = true){
            if(msg.startsWith("/")){
                player.sendMessage(ChatColor.RED + "You Can not run a command while banning a player");
            }else{
                chatReason = false;
                banReason = msg;
                main.banreson = msg;
                e.setCancelled(true);
                BukkitRunnable task = new BukkitRunnable() {
                    @Override
                    public void run() {
                        main.openDetailsGui(player, whoisban);
                    }
                };
                // Run the task on this plugin in 3 seconds (60 ticks)
                task.runTaskLater(main, 1);


            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) throws InterruptedException {
        Player player = (Player) e.getWhoClicked();


        if(e.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Ban GUI")){
            if(e.getCurrentItem().getType() == Material.PLAYER_HEAD){
                Player whotoban = player.getServer().getPlayer(e.getCurrentItem().getItemMeta().getDisplayName());

                whoisban = whotoban;
                main.openDetailsGui(player, whotoban);

                e.setCancelled(true);
            }
        }
        if(e.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Details for Ban")){
            switch(e.getCurrentItem().getType()){
                case BOOK:
                    player.closeInventory();
                    chatReason = true;
                    player.sendMessage(main.prefix + ChatColor.GRAY + " Please give a reason for the ban");

                    break;
                case GREEN_BANNER:
                    main.openConfirmMenu(player, whoisban);

            }
        }
        if(e.getView().getTitle().equalsIgnoreCase(ChatColor.GOLD + "" + ChatColor.BOLD + "Confirm Ban")){
            String bumper = org.apache.commons.lang.StringUtils.repeat("\n", 35);
            switch(e.getCurrentItem().getType()){
                case GREEN_BANNER:
                    if(!whoisban.hasPermission("rpge.ban.avoid") || !whoisban.isOp()) {
                        Bukkit.getBanList(BanList.Type.NAME).addBan(whoisban.getName(), ChatColor.RED + "You have been banned by : " + player.getDisplayName() + "\n for the Following Reason/s: " + banReason + ChatColor.RESET + "\n", new Date(System.currentTimeMillis() + 60 * 60 * 1000), player.getDisplayName());
                        whoisban.kickPlayer(ChatColor.RED + "You have been banned from the server for: " + banReason);
                        player.sendMessage(main.prefix + ChatColor.RED + "You Have Banned : " + whoisban.getDisplayName() + "For: " + banReason);
                        player.closeInventory();
                    }else{
                        player.sendMessage(main.prefix + ChatColor.RED + "You Can Not Ban This player.");
                        whoisban.sendMessage(main.prefix + ChatColor.RED + player.getDisplayName() + "Tried to ban you. You Dodged");
                    }
                case RED_BANNER:
                    main.openBanGui(player);

            }
            e.setCancelled(true);
        }

    }
}
