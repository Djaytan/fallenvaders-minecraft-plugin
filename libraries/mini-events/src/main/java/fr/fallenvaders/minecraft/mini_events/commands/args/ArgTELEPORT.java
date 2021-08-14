package fr.fallenvaders.minecraft.mini_events.commands.args;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import fr.fallenvaders.minecraft.mini_events.GameName;

public class ArgTELEPORT {
    public static void ArgTeleport(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9, "§0Menu des événements");
        List<GameName> gameNames = new ArrayList<>(Arrays.asList(GameName.values()));
        gameNames.remove(GameName.NONE);

        for (int i = 0; i < gameNames.size(); i++) {
            GameName gameName = gameNames.get(i);
            inv.setItem(i, GameName.getVisualItem(gameName));
        }

        player.openInventory(inv);

    }

	/*private static ItemStack getSpecificItem(Material material, int damage, boolean enchant, String customName) {
		if (damage < 0)
			damage = 0;
		ItemStack item = new ItemStack(material, 1, (short) damage);
		ItemMeta itemMeta = item.getItemMeta();
		if (customName != null)
			itemMeta.setDisplayName(customName);
		if (enchant) {
			itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
			itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		item.setItemMeta(itemMeta);
		return item;
	}*/

	/*private static ItemStack getBasicItem(Material material, int damage, String customName) {
		ItemStack item = new ItemStack(material, 1, (short) damage);
		ItemMeta itemMeta = item.getItemMeta();
		if (customName != null)
			itemMeta.setDisplayName(customName);
		item.setItemMeta(itemMeta);
		return item;
	}*/
}
