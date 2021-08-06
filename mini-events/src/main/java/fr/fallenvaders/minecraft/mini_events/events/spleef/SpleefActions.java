package fr.fallenvaders.minecraft.mini_events.events.spleef;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.fallenvaders.minecraft.mini_events.MiniEventsPlugin;

public class SpleefActions {

	// Message spécifique à l'événement SPLEEF (Le joueur est tombé à l'eau)
	public static void EventElimationMessageWater(Player players, Player playerOnWater, MiniEventsPlugin main) {
		players.sendMessage("§f[" + main.getGameName().getEventColoredPrefix().toUpperCase() + "§f] §c" + playerOnWater.getName()
				+ " §7est tombé à l'eau !");
	}
	
	// Méthode permettant d'obtenir la pelle de l'évent SPLEEF
	public static ItemStack getSpleefShovel() {
		ItemStack it = new ItemStack(Material.DIAMOND_SHOVEL, 1, (short) 0);
		ItemMeta itM = it.getItemMeta();
		itM.setDisplayName("§bLe Spleefator");
		itM.addEnchant(Enchantment.DIG_SPEED, 10, true);
		itM.setUnbreakable(true);
		it.setItemMeta(itM);
		return it;
	}
}
