package me.jj9playz.rpgessentials.enchants;

import jdk.nashorn.internal.runtime.ECMAException;
import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CustomEnchants {

    public static  final Enchantment POISONTIPPED = new EnchantmentWrapper("poisonedtip","Poisned Tip", 1);

    public static void register(){
        boolean registered = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(POISONTIPPED);

        if(!registered){
            registerEnchanments(POISONTIPPED);
        }

    }
    public static void registerEnchanments(Enchantment enchantment){
        boolean registered = true;
        try{
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);

            Enchantment.registerEnchantment(enchantment);

        }catch(Exception e){
            registered = false;
            e.printStackTrace();
        }
    }

}
