package me.jj9playz.rpgessentials;


import me.jj9playz.rpgessentials.commands.BanGUICommand;
import me.jj9playz.rpgessentials.customRecipies.amber.amberArmorRecipies;
import me.jj9playz.rpgessentials.enchants.CustomEnchants;
import me.jj9playz.rpgessentials.events.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


import java.util.ArrayList;


public final class RPGEssentials extends JavaPlugin implements Listener {

    public String welcomemessage = getConfig().getString("Welcome_Message"); //Get Custom Welcome Message


    public String globalwelcomemessage = getConfig().getString("Global_Welcome_Message"); //Get Global Message
    public String globalquitmessage = getConfig().getString("Global_Quit_Message");


    //COMMANDS
    public String cmd1 = "rpge";
    public String cmd2 = "class";
    public String cmd3 = "race";
    public String banreson;
    public String charname;
    public String chardesc;
    public String charrace;
    public String charclass;

    //Class Setters
    boolean Hunter = false;
    boolean Mage = false;
    boolean Archer = false;
    boolean Titan = false;
    boolean Knight = false;
    boolean Citizen = true;

    //Race Setters
    boolean Angel = false;
    boolean Elf = false;
    boolean Human = true;
    boolean Ork = false;


    public ItemStack amber = new ItemStack(Material.ORANGE_GLAZED_TERRACOTTA, 1);
    public ItemMeta amber_meta = amber.getItemMeta();
    public ArrayList<String> amber_lore = new ArrayList<>();

    public ItemStack saphire = new ItemStack(Material.LAPIS_BLOCK, 1);
    public ItemMeta saphire_meta = saphire.getItemMeta();
    public ArrayList<String> saphire_lore = new ArrayList<>();

    public ItemStack firestone = new ItemStack(Material.MAGMA_BLOCK, 1);
    public ItemMeta fs_meta = firestone.getItemMeta();
    public ArrayList<String> fs_lore = new ArrayList<>();

    public ItemStack holystone = new ItemStack(Material.QUARTZ_BLOCK, 1);
    public ItemMeta hs_meta = holystone.getItemMeta();
    public ArrayList<String> hs_lore = new ArrayList<>();

    public String prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Prefix"));


    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[me.jj9playz.RPGEssentials] RPGE Is Now Enabled");

        //Events
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
        getServer().getPluginManager().registerEvents(new BanGuiEvents(), this);
        getServer().getPluginManager().registerEvents(new BlockBreak(), this);


        //commands
        getCommand("bangui").setExecutor(new BanGUICommand());

        //Config
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
            //Custom Config
            //Template.setup();
            //Template.get().addDefault("Weapon_Name", "TEST");
            //Template.get().options().copyDefaults(true);
            //Template.save();

        CustomEnchants.register();


        amberArmorRecipies amberArmor = new amberArmorRecipies();
        amberArmor.chestRecipe();



        //Amber
        amber_meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Amber");
        amber_lore.add(ChatColor.LIGHT_PURPLE + "A Mystical Ore that comes from trees.");
        amber_lore.add(ChatColor.GREEN + "The Stone seems to give off a healing effect. Could work with that");
        amber_meta.setLore(amber_lore);
        amber.setItemMeta(amber_meta);
        //ruby
        saphire_meta.setDisplayName(ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Saphire");
        saphire_lore.add(ChatColor.LIGHT_PURPLE + "A Mystical Stone that comes from the sea.");
        saphire_lore.add(ChatColor.AQUA + "The Stone is cold to the touch. Interesting...");
        saphire_meta.setLore(saphire_lore);
        saphire.setItemMeta(saphire_meta);
        //fs
        fs_meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Firestone");
        fs_lore.add(ChatColor.LIGHT_PURPLE + "A Mystical Stone that came from the depths of hell.");
        fs_lore.add(ChatColor.RED + "The Stone seems to be infused with lava, could be useful.");
        fs_meta.setLore(fs_lore);
        firestone.setItemMeta(fs_meta);
        //hs
        hs_meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Holystone");
        hs_lore.add(ChatColor.LIGHT_PURPLE + "A Mystical Stone that fell from the sky.");
        hs_lore.add(ChatColor.WHITE + "The Stone gives off an aura of Protection. Hmmm...");
        hs_meta.setLore(hs_lore);
        hs_meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        holystone.setItemMeta(hs_meta);

    }


    public void openBanGui(Player player){
        ArrayList<Player> player_list = new ArrayList<>(player.getServer().getOnlinePlayers());

        Inventory banGui = Bukkit.createInventory(player, 45, ChatColor.DARK_RED + "" + ChatColor.BOLD + "Ban GUI");

        for(int i = 0; i < player_list.size();i++){
            ItemStack playerhead = new ItemStack(Material.PLAYER_HEAD,1);
            ItemMeta meta = playerhead.getItemMeta();
            meta.setDisplayName(player_list.get(i).getDisplayName());
            ArrayList<String> lore = new ArrayList<String>();
            lore.add(ChatColor.GRAY + "Player Health:" + player_list.get(i).getHealth());
            lore.add(ChatColor.GRAY + "XP: " + player_list.get(i).getExp());
            meta.setLore(lore);
            playerhead.setItemMeta(meta);

            banGui.addItem(playerhead);
        }

        player.openInventory(banGui);
    }

    public void openDetailsGui(Player player, Player playerToBan){

        Inventory detailsGUI = Bukkit.createInventory(player, 9, ChatColor.DARK_RED + "" + ChatColor.BOLD + "Details for Ban");

        Player banMe = playerToBan;

        //Reason for Ban
        ItemStack reason = new ItemStack(Material.BOOK);
        ItemMeta reason_meta = reason.getItemMeta();
        reason_meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Reason for Ban");
        ArrayList<String> reason_lore = new ArrayList<>();
        reason_lore.add(ChatColor.DARK_GRAY + "Reason: " + banreson);
        reason_meta.setLore(reason_lore);
        reason.setItemMeta(reason_meta);
        detailsGUI.setItem(2, reason);

        //Confirm
        ItemStack confirm = new ItemStack(Material.GREEN_BANNER);
        ItemMeta confirm_meta = reason.getItemMeta();
        confirm_meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Confirm Details");
        confirm.setItemMeta(confirm_meta);
        detailsGUI.setItem(8, confirm);


        player.openInventory(detailsGUI);
    }

    public void openConfirmMenu(Player player, Player playerToBan){
        Inventory confirmGUI = Bukkit.createInventory(player, 9, ChatColor.GOLD + "" + ChatColor.BOLD + "Confirm Ban");

        Player banMe = playerToBan;

        //Ban Options
        ItemStack ban = new ItemStack(Material.GREEN_BANNER);
        ItemMeta ban_meta = ban.getItemMeta();
        ban_meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Confirm");
        ban.setItemMeta(ban_meta);
        confirmGUI.setItem(1, ban);

        //Player they are Banning
        ItemStack player_head = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta ph_meta = player_head.getItemMeta();
        ph_meta.setDisplayName(banMe.getDisplayName());
        ArrayList<String> ph_lore = new ArrayList<>();
        ph_lore.add(ChatColor.DARK_GRAY + "REASON: " + banreson);
        ph_lore.add(ChatColor.DARK_GRAY + "TIME: " + "1 Hour");
        ph_meta.setLore(ph_lore);
        player_head.setItemMeta(ph_meta);
        confirmGUI.setItem(4, player_head);

        //Player they are Banning
        ItemStack cancel = new ItemStack(Material.RED_BANNER);
        ItemMeta cancel_meta = cancel.getItemMeta();
        cancel_meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Cancel");
        cancel.setItemMeta(cancel_meta);
        confirmGUI.setItem(7, cancel);

        //Empty Squares
        ItemStack empty = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta em = empty.getItemMeta();
        em.setDisplayName(" ");
        empty.setItemMeta(em);
        confirmGUI.setItem(0, empty);
        confirmGUI.setItem(2, empty);
        confirmGUI.setItem(3, empty);
        confirmGUI.setItem(5, empty);
        confirmGUI.setItem(6, empty);
        confirmGUI.setItem(8, empty);

        player.openInventory(confirmGUI);

    }



    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[me.jj9playz.RPGEssentials] RPGE Is Now Disabled");

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        //set Prefix Commands
        if(sender instanceof Player) {
            if (cmd.getName().equalsIgnoreCase(cmd1)) {
                String Commands = ChatColor.BLACK + "==========================================\n" +
                        ChatColor.LIGHT_PURPLE + "RPGE Is a Simple RPG Plugin that adds in all the essential commands and functions.\nIt is highly Customizable!\n" +
                        ChatColor.GOLD + ChatColor.BOLD + "COMMANDS:\n" +
                        ChatColor.RESET + ChatColor.AQUA + "/rgpe : " + ChatColor.GRAY + "Lists All Commands || Admin, SetOutputPrefix\n" +
                        ChatColor.RESET + ChatColor.AQUA + "/class : " + ChatColor.GRAY + "Allows user to Set Class || Default, CustomPrefix\n"

                        + ChatColor.BLACK + "\n==========================================\n";
                sender.sendMessage(Commands);
                return true;
            }
            if(cmd.getName().equalsIgnoreCase(cmd2)){
                if(sender.hasPermission("rpge.changeRole") || sender.hasPermission("rpge.*") || sender.isOp()){
                    if(args.length > 0){
                        if(args[0].equalsIgnoreCase("set")){
                            if(args.length > 1){
                                if(args[1].equalsIgnoreCase("hunter") && sender.hasPermission("rpge.hunter")){
                                    Hunter = true;
                                    Mage = false;
                                    Archer = false;
                                    Titan = false;
                                    Knight = false;
                                    Citizen = false;
                                    pclass(player);
                                    sender.sendMessage(ChatColor.GRAY + "Your Class is now : " + ChatColor.GREEN + "Hunter");
                                }
                                if(args[1].equalsIgnoreCase("mage") && sender.hasPermission("rpge.mage")){
                                    Mage = true;
                                    Hunter = false;
                                    Archer = false;
                                    Titan = false;
                                    Knight = false;
                                    Citizen = false;
                                    pclass(player);
                                    sender.sendMessage(ChatColor.GRAY + "Your Class is now : " + ChatColor.LIGHT_PURPLE + "Mage");
                                }
                                if(args[1].equalsIgnoreCase("archer") && sender.hasPermission("rpge.archer")){
                                    Archer = true;
                                    Hunter = false;
                                    Mage = false;
                                    Titan = false;
                                    Knight = false;
                                    Citizen = false;
                                    pclass(player);
                                    sender.sendMessage(ChatColor.GRAY + "Your Class is now : " + ChatColor.DARK_GREEN + "Archer");
                                }
                                if(args[1].equalsIgnoreCase("titan") && sender.hasPermission("rpge.titan")){
                                    Titan = true;
                                    Hunter = false;
                                    Mage = false;
                                    Archer = false;
                                    Knight = false;
                                    Citizen = false;
                                    pclass(player);
                                    sender.sendMessage(ChatColor.GRAY + "Your Class is now : " + ChatColor.AQUA + "Titan");

                                }
                                if(args[1].equalsIgnoreCase("knight") && sender.hasPermission("rpge.knight")){
                                    Knight = true;
                                    Hunter = false;
                                    Mage = false;
                                    Archer = false;
                                    Titan = false;
                                    Citizen = false;
                                    pclass(player);
                                    sender.sendMessage(ChatColor.GRAY + "Your Class is now : " + ChatColor.RED + "Knight");
                                }
                                if(args[1].equalsIgnoreCase("citizen")){
                                    Citizen = true;
                                    Hunter = false;
                                    Mage = false;
                                    Archer = false;
                                    Titan = false;
                                    Knight = false;
                                    pclass(player);
                                    sender.sendMessage(ChatColor.GRAY + "Your Class is now : " + ChatColor.DARK_GRAY + "Citizen");
                                }

                            }
                        }
                    }else{
                        String roles_descriptions = getConfig().getString("roles_descriptions");
                        roles_descriptions = roles_descriptions.replaceAll("%nl%", "\n");
                        roles_descriptions = ChatColor.translateAlternateColorCodes('&', roles_descriptions);

                        sender.sendMessage(roles_descriptions);
                    }
                    return true;
                }
            }
            if(cmd.getName().equalsIgnoreCase(cmd3)){
                if(sender.hasPermission("rpge.changeRace") || sender.hasPermission("rpge.*") || sender.isOp()) {
                    if (args.length > 0) {
                        if (args[0].equalsIgnoreCase("set")) {
                            if (args.length > 1) {
                                if (args[1].equalsIgnoreCase("elf") && sender.hasPermission("rpge.elf")) {
                                    Angel = false;
                                    Elf = true;
                                    Human = false;
                                    Ork = false;
                                    prace(player);
                                    sender.sendMessage(ChatColor.GRAY + "Your Race is now : " + ChatColor.GREEN + "Elf");
                                }
                                if (args[1].equalsIgnoreCase("human") && sender.hasPermission("rpge.human")) {
                                    Angel = false;
                                    Elf = false;
                                    Human = true;
                                    Ork = false;
                                    prace(player);
                                    sender.sendMessage(ChatColor.GRAY + "Your Race is now : " + ChatColor.LIGHT_PURPLE + "Human");
                                }
                                if (args[1].equalsIgnoreCase("ork") && sender.hasPermission("rpge.ork")) {
                                    Angel = false;
                                    Elf = false;
                                    Human = false;
                                    Ork = true;
                                    prace(player);
                                    sender.sendMessage(ChatColor.GRAY + "Your Race is now : " + ChatColor.AQUA + "Ork");

                                }
                                if (args[1].equalsIgnoreCase("angel") && sender.hasPermission("rpge.angel")) {
                                    Angel = true;
                                    Elf = false;
                                    Human = false;
                                    Ork = false;

                                    prace(player);
                                    sender.sendMessage(ChatColor.GRAY + "Your Race is now : " + ChatColor.RED + "Angel");
                                }

                            }
                        }
                    } else {
                        String roles_descriptions = getConfig().getString("roles_descriptions");
                        roles_descriptions = roles_descriptions.replaceAll("%nl%", "\n");
                        roles_descriptions = ChatColor.translateAlternateColorCodes('&', roles_descriptions);

                        sender.sendMessage(roles_descriptions);
                    }
                }
            }

        }else{
            sender.sendMessage(ChatColor.DARK_BLUE + "RPGE" + ChatColor.RED + "You Must Be A Player to Run that Command");
        }



        return false;
    }

    public void pclass(Player player){
        if(Hunter == true){
            for (PotionEffect effect : player.getActivePotionEffects()) {
                player.removePotionEffect(effect.getType());
            }

            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 1, true));

             Mage = false;
            Archer = false;
             Titan = false;
            Knight = false;
             Citizen = false;
        }
        if(Mage == true){


            player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, 2, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 1, true));

             Hunter = false;
             Archer = false;
             Titan = false;
             Knight = false;
             Citizen = false;
        }
        if(Archer == true){


            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, 1, true));

             Hunter = false;
             Mage = false;
             Titan = false;
             Knight = false;
             Citizen = false;
        }
        if(Titan == true){


            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 2, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 2, true));

             Hunter = false;
             Mage = false;
             Archer = false;
             Knight = false;
             Citizen = false;
        }
        if(Knight == true){


            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, 1, true));

             Hunter = false;
             Mage = false;
             Archer = false;
             Titan = false;
             Citizen = false;
        }
        if(Citizen == true){


            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 2, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 1, true));

             Hunter = false;
             Mage = false;
             Archer = false;
             Titan = false;
             Knight = false;
        }
        if(Hunter == false){
            player.removePotionEffect(PotionEffectType.SPEED);
            player.removePotionEffect(PotionEffectType.WEAKNESS);
        }
        if(Mage == false){
            player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
            player.removePotionEffect(PotionEffectType.REGENERATION);
            player.removePotionEffect(PotionEffectType.WEAKNESS);
        }
        if(Archer == false){
            player.removePotionEffect(PotionEffectType.SPEED);
            player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
        }
        if(Titan == false){
            player.removePotionEffect(PotionEffectType.SLOW);
            player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
            player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
        }
        if(Knight == false){
            player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
            player.removePotionEffect(PotionEffectType.REGENERATION);
            player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
        }
        if(Citizen == false){
            player.removePotionEffect(PotionEffectType.REGENERATION);
            player.removePotionEffect(PotionEffectType.WEAKNESS);
        }


    }
    public void prace(Player player){
        if(Elf == true){
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 2));
            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 1));
            Angel = false;
            Human = false;
            Ork = false;
        }
        if(Human == true){
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 2));
            player.addPotionEffect(new PotionEffect(PotionEffectType.UNLUCK, Integer.MAX_VALUE, 1));
            Angel = false;
            Elf = false;
            Ork = false;
        }
        if(Ork == true){
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 3));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1));
            Angel = false;
            Human = false;
            Elf = false;
        }
        if(Angel == true){
            player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, 5));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, 1));
            Elf = false;
            Human = false;
            Ork = false;
        }
        if(Elf == false){
            player.removePotionEffect(PotionEffectType.REGENERATION);
            player.removePotionEffect(PotionEffectType.WEAKNESS);
        }
        if(Human == false){
            player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
            player.removePotionEffect(PotionEffectType.UNLUCK);
        }
        if(Ork == false){
            player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
            player.removePotionEffect(PotionEffectType.SLOW);
        }
        if(Angel == false){
            player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
            player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
            player.removePotionEffect(PotionEffectType.SLOW_FALLING);
        }
    }


}
