package fr.fallenvaders.minecraft.mini_events;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.fallenvaders.minecraft.mini_events.events.bowspleef.BowSpleefActions;
import fr.fallenvaders.minecraft.mini_events.events.spleef.SpleefActions;

// Liste des événement disponible
//
// Ce sont aussi les événements qui seront marqués quand la commande affichant 
// la liste des évenement sera éxécutée.

public enum GameName {
    NONE("NONE", "Aucun"),
    SPLEEF("SPLEEF", "Spleef", Material.DIAMOND_SHOVEL, 0, true, ChatColor.BLUE, true, 0, "pelle"),
    ENCLUME("ENCLUME", "Enclume", Material.ANVIL, 0, false, ChatColor.DARK_GRAY, false, 2, "-"),
    BOWSPLEEF("BOWSPLEEF", "BowSpleef", Material.BOW, 0, true, ChatColor.DARK_RED, true, 2, "arc"),
    LAINE("LAINE", "Laine", Material.WHITE_WOOL, 0, false, ChatColor.WHITE, true, 2, "-"),
    TNT_RUN("TNT_RUN", "TNT Run", Material.TNT, 0, false, ChatColor.RED, true, 2, "-"),
    DAC("DAC", "Dés à Coudre", Material.BUCKET, 0, false, ChatColor.GRAY, true, 2, "-"),
    SMASH("SMASH", "Smash", Material.BLAZE_POWDER, 0, true, ChatColor.GOLD, true, 2, "baton");

    private String realName; //Vrai nom de l'événement côté config.
    private String visualName; //Nom de l'événement côté joueur.

    //** Utilisé pour le /event teleport et son menu:
    private Material itemMaterial; //Item associé à l'événement.
    private int damage; //Damage de l'item en question.
    private boolean enchant; //Si l'item est enchanté ou pas.
    private ChatColor visualColor; //La couleur du nom de l'événement.

    //** Paramètres de la partie:
    private boolean invulnerable; //Si le joueur sera invulnérable durant l'événement.
    private int gamemode; //Gamemode en début de partie (GameState == PLAYING)

    private String itemName; //Item de début de partie donné au joueur.
    // PS: Il peut y en avoir plusieur, plus d'infos dans la méthode en-dessous.

    private GameName(final String realName, final String visualName, final Material itemMaterial, final int damage,
                     final boolean enchant, final ChatColor visualColor, final boolean invulnerable, final int gamemode,
                     final String itemName) {
        this.realName = realName;
        this.visualName = visualName;
        this.itemMaterial = itemMaterial;
        this.damage = damage;
        this.enchant = enchant;
        this.visualColor = visualColor;
        this.invulnerable = invulnerable;
        this.gamemode = gamemode;
        this.itemName = itemName;

    }

    private GameName(final String realName, final String visualName) {
        this.realName = realName;
        this.visualName = visualName;
    }

    //PLUS TARD
		/*public static GameName getByName(final String name) {
		        GameName gameType = null;
		        int i = 0;
		        while (gameType == null && i < values().length) {
		            if (values()[i].getName().equals(name)) {
		                gameType = values()[i];
		            }
		            else {
		                ++i;
		            }
		        }
		        return gameType;
		    }*/

    public String getRealName() {
        return this.realName;
    }

    public String getVisualName() {
        return this.visualName;
    }

    public static ItemStack getVisualItem(GameName gameName) {
        int damage = gameName.getItemDamage();
        if (damage < 0)
            damage = 0;

        ItemStack item = new ItemStack(gameName.getItemMaterial(), 1, (short) damage);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§f§lEvent: §r" + gameName.getEventColoredPrefix().toUpperCase());
        if (gameName.getItemEnchant()) {
            itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(itemMeta);
        return item;
    }

    private Material getItemMaterial() {
        return this.itemMaterial;
    }

    private int getItemDamage() {
        return this.damage;
    }

    private boolean getItemEnchant() {
        return this.enchant;
    }

    private ChatColor getTextColor() {
        return this.visualColor;
    }

    public String getEventColoredPrefix() {
        String eventPrefix = (this.getTextColor() + this.getVisualName());
        return eventPrefix;
    }

    public boolean getVulnerable() {
        return this.invulnerable;
    }

    public GameMode getGamemode() {
        if (this.gamemode == 0)
            return GameMode.SURVIVAL;
        else if (this.gamemode == 1)
            return GameMode.CREATIVE;
        else if (this.gamemode == 2)
            return GameMode.ADVENTURE;
        else if (this.gamemode == 3)
            return GameMode.SPECTATOR;
        else
            return null;
    }

    public void getSpecificItem(Player player) {
        if (this.itemName.equals("pelle")) {
            player.getInventory().addItem(SpleefActions.getSpleefShovel());
        } else if (this.itemName.equals("arc")) {
            player.getInventory().addItem(BowSpleefActions.getFireBow());
            player.getInventory().addItem(new ItemStack(Material.ARROW, 1));
        } else {
            return;
        }
    }
}
