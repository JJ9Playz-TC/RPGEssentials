package me.jj9playz.rpgessentials.events;

import me.jj9playz.rpgessentials.RPGEssentials;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerJoin implements Listener {

    RPGEssentials main = JavaPlugin.getPlugin(RPGEssentials.class);

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();

        main.welcomemessage = main.welcomemessage.replaceAll("%player%", player.getDisplayName()); //Replace PlaceHolders
        main.welcomemessage = ChatColor.translateAlternateColorCodes('&', main.welcomemessage);

        main.globalwelcomemessage = main.globalwelcomemessage.replaceAll("%player%", player.getDisplayName()); //Replace Placeholders
        main.globalwelcomemessage = ChatColor.translateAlternateColorCodes('&', main.globalwelcomemessage);

        e.setJoinMessage(main.globalwelcomemessage);
        player.sendMessage(main.welcomemessage);

        //main.pclass(player);
    }
}
