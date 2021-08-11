package fr.fallenvaders.minecraft.core.mail_box.listeners.utils.hookers;

import fr.fallenvaders.minecraft.core.mail_box.inventory.builders.InventoryBuilder;
import fr.fallenvaders.minecraft.core.mail_box.inventory.inventories.utils.IdentifiersList;
import fr.fallenvaders.minecraft.core.mail_box.listeners.utils.ChatHooker;
import fr.fallenvaders.minecraft.core.mail_box.utils.LangManager;
import fr.fallenvaders.minecraft.core.mail_box.utils.MessageLevel;
import fr.fallenvaders.minecraft.core.mail_box.utils.MessageUtils;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class CH_Player extends ChatHooker {
	
	public static final String ID = "MailBox_Player_ChatHooker";
	
	public CH_Player(IdentifiersList identifiersList, InventoryBuilder parentInv) {
		super(ID, LangManager.getValue("information_ch_player_selection_start") );

		this.setExecution(event -> {
			Player ePlayer = event.getPlayer();
			String eMessage = event.getMessage();
			event.setCancelled(true);

			if (eMessage.equals("#stop")) {
				parentInv.openInventory(ePlayer);
				this.stop();
				return;
			}

			List<String> splitedMsg = Arrays.asList(eMessage.split(","));
			
			if(splitedMsg.size() == 1 || splitedMsg.size() > 1 && ePlayer.hasPermission("mailbox.send.announce") ) {
				String wrongName = identifiersList.addAllIdentifiers(splitedMsg);
	
				if (wrongName == null) {
					MessageUtils.sendMessage(ePlayer, MessageLevel.INFO, LangManager.getValue("information_chat_selection_recipients", identifiersList.getPreviewString()) );
					parentInv.openInventory(ePlayer);
					this.stop();
	
				} else if (wrongName.equals(ePlayer.getName()) ) {
					MessageUtils.sendMessage(ePlayer, MessageLevel.ERROR, LangManager.getValue("string_player_not_yourself") );
					
				} else {
					MessageUtils.sendMessage(ePlayer, MessageLevel.ERROR, LangManager.getValue("string_player_not_found", wrongName) );
					
	
				}
				
			} else {
				MessageUtils.sendMessage(ePlayer, MessageLevel.ERROR, LangManager.getValue("string_permission_needed") );
				
			}
		});
	}

}
