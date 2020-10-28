package me.jj9playz.rpgessentials.customRecipies.amber;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class amberArmorRecipies implements Listener {



    public void chestRecipe(){

        ItemStack amberChest = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
        ItemMeta ac_meta = amberChest.getItemMeta();

        ac_meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Amber Chestplate");
        ArrayList<String> ac_lore = new ArrayList<>();
        ac_lore.add(ChatColor.LIGHT_PURPLE + "A Chestplate made of Amber");
        ac_lore.add(ChatColor.DARK_RED + "Effects");
        ac_lore.add(ChatColor.GRAY + "  - " + ChatColor.WHITE + "Healing I");
        ac_lore.add(ChatColor.GRAY + "  - " + ChatColor.WHITE + "Protection IV");
        ac_lore.add(ChatColor.GRAY + "  - " + ChatColor.WHITE + "Self Mending I");
        ac_meta.setLore(ac_lore);
        ac_meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
        ac_meta.addEnchant(Enchantment.MENDING, 4, true);
        amberChest.setItemMeta(ac_meta);

        ShapedRecipe cr = new ShapedRecipe(amberChest);

        cr.shape("@ @", "@@@", "@@@");
        cr.setIngredient('@', Material.ORANGE_GLAZED_TERRACOTTA);
    }

}
