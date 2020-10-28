package me.jj9playz.rpgessentials.events;

import me.jj9playz.rpgessentials.RPGEssentials;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerQuit implements Listener {

    RPGEssentials main = JavaPlugin.getPlugin(RPGEssentials.class);

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        Player player = e.getPlayer();



        main.globalquitmessage = main.globalquitmessage.replaceAll("%player%", player.getName());
        main.globalquitmessage = ChatColor.translateAlternateColorCodes('&', main.globalquitmessage);

        e.setQuitMessage(main.globalquitmessage);
    }
}
