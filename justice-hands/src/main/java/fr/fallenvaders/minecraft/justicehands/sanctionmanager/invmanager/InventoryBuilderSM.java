package fr.fallenvaders.minecraft.justicehands.sanctionmanager.invmanager;

import java.util.UUID;

import fr.minuskube.inv.InventoryManager;
import fr.minuskube.inv.SmartInventory;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.java.JavaPlugin;

import fr.fallenvaders.minecraft.justicehands.GeneralUtils;
import fr.fallenvaders.minecraft.justicehands.JusticeHandsPlugin;
import fr.fallenvaders.minecraft.justicehands.sanctionmanager.objects.Categorie;

public class InventoryBuilderSM {

	// Ouverture de l'inventaire des catégories
	public static void openMainMenu(Player moderator, UUID targetUUID, FileConfiguration config) {
		
		// Récupération des informations de la config du plugin
		String mainMenuPath = "justicehands.sanctionmanager.MainMenu";
		int menuLines = config.getInt(mainMenuPath + ".lines");
		int targetHeadLine = config.getInt(mainMenuPath + ".playerhead-line");
		int targetHeadColum = config.getInt(mainMenuPath + ".playerhead-colum");


		// Création de l'inventaire
		InventoryManager inventoryManager = new InventoryManager(JavaPlugin.getPlugin(JusticeHandsPlugin.class));
		inventoryManager.init();

		SmartInventory.Builder builder = SmartInventory.builder();
		builder.title("§7[§9" + Bukkit.getPlayer(targetUUID).getName() + "§7]" + "§7> §cMenu principal");
		builder.provider(new MainInventorySM(targetHeadLine, targetHeadColum));
		builder.size(menuLines, 9);
		builder.id(targetUUID.toString());
		builder.type(InventoryType.CHEST);
		builder.manager(inventoryManager);
		SmartInventory inventory = builder.build();
		inventory.open(moderator);
	}

	// Ouverture de l'inventaire des sanction de la catégorie voulue
	public static void openCategoryMenu(Categorie categorie, Player moderator, Player target) {
		
		// Création de l'inventaire
		InventoryManager inventoryManager = new InventoryManager(JavaPlugin.getPlugin(JusticeHandsPlugin.class));
		inventoryManager.init();

		SmartInventory.Builder builder = SmartInventory.builder();
		builder.title(GeneralUtils.getPrefix("SM") + "§7> §c" + categorie.getName());
		builder.provider(new CategoryInventorySM(categorie));
		builder.size(6, 9);
		builder.id(target.getUniqueId().toString());
		builder.type(InventoryType.CHEST);
		builder.manager(inventoryManager);
		SmartInventory inventory = builder.build();
		inventory.open(moderator);
	}
}
