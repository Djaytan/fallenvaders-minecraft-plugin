package fr.fallenvaders.minecraft.core.mail_box.inventory;

import fr.fallenvaders.minecraft.core.mail_box.data_manager.Data;
import fr.fallenvaders.minecraft.core.mail_box.data_manager.ItemData;
import fr.fallenvaders.minecraft.core.mail_box.data_manager.LetterData;
import fr.fallenvaders.minecraft.core.mail_box.utils.ItemStackBuilder;
import fr.fallenvaders.minecraft.core.mail_box.utils.LangManager;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;

public class MailBoxInventoryHandler {
	
	public static ItemStack generateItemRepresentation(Data data) {
		ItemStack res = null;
		
		if(data instanceof ItemData) {
			res = generateItemDataRepresentation((ItemData) data);
			
		} else if (data instanceof LetterData) {
			res = generateLetterDataRepresentation((LetterData) data);
		}
		
		
		return res;
	}
	
	private static ItemStack generateItemDataRepresentation(ItemData data) {
		return data.getItem();
	}
	
	private static	 ItemStack generateLetterDataRepresentation(LetterData data) {
		SimpleDateFormat sdf =  new SimpleDateFormat(LangManager.getValue("string_date_format"));
		ItemStackBuilder itemGenerator = new ItemStackBuilder(data.getLetterType().getMaterial())
				.setLoreFormat("§f")
				.addAutoFormatingLore("§e§l" + LangManager.getValue("string_object") +":§r§f " + data.getObject(), 35)
				.addAutoFormatingLore("§e§l" + LangManager.getValue("string_author") +":§r§f " + data.getAuthor(), 35)
				.addAutoFormatingLore("§e§l" + LangManager.getValue("string_reception_date") +":§r§f " + sdf.format(data.getCreationDate()), 35 )
				.addLore(" ");
		
		if(!data.getIsRead()) {
			itemGenerator.enchant(Enchantment.ARROW_FIRE, 1).addFlag(ItemFlag.HIDE_ENCHANTS);
		}
		
		return itemGenerator.build();
	}
	
}
