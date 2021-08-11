package fr.fallenvaders.minecraft.core.mail_box.inventory.inventories.creation;

import fr.fallenvaders.minecraft.core.mail_box.inventory.builders.ConfirmationInventoryBuilder;
import fr.fallenvaders.minecraft.core.mail_box.inventory.builders.InventoryBuilder;
import fr.fallenvaders.minecraft.core.mail_box.utils.LangManager;
import fr.minuskube.inv.content.InventoryContents;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class ResetConfirmationInventory extends ConfirmationInventoryBuilder {
	
	public static final String SUB_ID = "reset_creation_inventory";
	private static final String RESET = LangManager.getValue("string_reset_creation");
	
	public ResetConfirmationInventory(InventoryBuilder parent) {
		super("reset_creation_inventory", "§l" + RESET);
		super.setParent(parent);
	}

	@Override
	public void onUpdate(Player player, InventoryContents contents) {
	}

	@Override
	public Consumer<InventoryClickEvent> onConfirmation(Player player, InventoryContents contents) {
		return e -> {
			CreationInventory inv = CreationInventory.newInventory(player.getUniqueId());
			inv.setParent(getParent().getParent());
			inv.openInventory(player);

		};
	}

	@Override
	public Consumer<InventoryClickEvent> onAnnulation(Player player, InventoryContents contents) {
		return null;
	}

}
