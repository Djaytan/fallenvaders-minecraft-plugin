package fr.fallenvaders.minecraft.justice_hands.criminalrecords.invmanager;

import java.util.List;
import java.util.UUID;

import fr.minuskube.inv.InventoryManager;
import fr.minuskube.inv.SmartInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.java.JavaPlugin;

import fr.fallenvaders.minecraft.justicehands.GeneralUtils;
import fr.fallenvaders.minecraft.justicehands.JusticeHandsPlugin;
import fr.fallenvaders.minecraft.justicehands.criminalrecords.objects.CJSanction;

public class InventoryBuilderCR {
	
	// Création de l'inventaire affichant les sanctions d'un joueur:
	public static void openMainMenu(Player player, UUID targetUUID) {
		// Joueur cible du modérateur
		Player target = Bukkit.getPlayer(targetUUID);
		
		// Récupération de la liste des sanctions du joueur
		List<CJSanction> playerAllSanctionList = JusticeHandsPlugin.getSqlSM().getPlayerSanctions(player);
		
		// Création de l'inventaire
		InventoryManager inventoryManager = new InventoryManager(JavaPlugin.getPlugin(JusticeHandsPlugin.class));
		inventoryManager.init();

		SmartInventory.Builder builder = SmartInventory.builder();
		builder.title(GeneralUtils.getPrefix("CR") + "§c" + target.getName());
		builder.provider(new InventoryCR(playerAllSanctionList));
		builder.id(targetUUID.toString());
		builder.size(6, 9);
		builder.type(InventoryType.CHEST);
		builder.manager(inventoryManager);
		SmartInventory inventory = builder.build();
		inventory.open(player);
	}
}
