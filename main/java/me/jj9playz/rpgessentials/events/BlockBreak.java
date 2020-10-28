package me.jj9playz.rpgessentials.events;

import me.jj9playz.rpgessentials.RPGEssentials;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;


public class BlockBreak implements Listener {
    RPGEssentials main = JavaPlugin.getPlugin(RPGEssentials.class);

    ItemStack amber = new ItemStack(Material.ORANGE_GLAZED_TERRACOTTA, 1);
    ItemMeta amber_meta = amber.getItemMeta();
    ArrayList<String> amber_lore = new ArrayList<>();



    @EventHandler
    public void onblockBreak(BlockBreakEvent e) {
        Block block = e.getBlock();

        ItemStack pmh = e.getPlayer().getInventory().getItemInMainHand();

        if(pmh != null) {
            if (block.getType().equals(Material.ORANGE_GLAZED_TERRACOTTA)) {


                e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), main.amber);
                e.setDropItems(false);

            }
            if (block.getType().equals(Material.LAPIS_BLOCK)) {


                e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), main.saphire);
                e.setDropItems(false);

            }
            if (block.getType().equals(Material.MAGMA_BLOCK)) {


                e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), main.firestone);
                e.setDropItems(false);

            }
            if (block.getType().equals(Material.QUARTZ_BLOCK)) {


                e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), main.holystone);
                e.setDropItems(false);

            }
        }
    }

}

