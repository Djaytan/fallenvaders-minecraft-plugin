package fr.fallenvaders.minecraft.core.mail_box.listeners.utils.hookers;

import fr.fallenvaders.minecraft.core.mail_box.inventory.builders.InventoryBuilder;
import fr.fallenvaders.minecraft.core.mail_box.listeners.utils.ChatHooker;
import org.bukkit.entity.Player;

import java.util.List;

public class CH_LetterContent extends ChatHooker {

	public CH_LetterContent(String id, String startMsg, List<String> content, InventoryBuilder parentInv, Boolean doAdd) {
		super(id, startMsg);

		this.setExecution(event -> {
			Player ePlayer = event.getPlayer();
			String eMessage = event.getMessage();
			event.setCancelled(true);

			if (eMessage.equals("#stop")) {
				parentInv.openInventory(ePlayer);
				this.stop();
				return;
			}
			
			if(doAdd) {
				
			}
			
			if(doAdd || content.isEmpty()) {
				content.add(eMessage);
				
			} else {
				content.set(content.size()-1, eMessage);
			}

			this.stop();
			parentInv.openInventory(ePlayer);

		});
	}
}
