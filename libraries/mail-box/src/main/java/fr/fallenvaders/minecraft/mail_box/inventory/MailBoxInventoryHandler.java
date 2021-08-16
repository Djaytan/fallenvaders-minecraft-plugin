package fr.fallenvaders.minecraft.mail_box.inventory;

import fr.fallenvaders.minecraft.mail_box.data_manager.Data;
import fr.fallenvaders.minecraft.mail_box.data_manager.ItemData;
import fr.fallenvaders.minecraft.mail_box.data_manager.LetterData;
import fr.fallenvaders.minecraft.mail_box.utils.ItemStackBuilder;
import fr.fallenvaders.minecraft.mail_box.utils.LangManager;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;

public class MailBoxInventoryHandler {

    public static ItemStack generateItemRepresentation(Data data) {
        ItemStack res = null;

        if (data instanceof ItemData) {
            res = generateItemDataRepresentation((ItemData) data);

        } else if (data instanceof LetterData) {
            res = generateLetterDataRepresentation((LetterData) data);
        }


        return res;
    }

    private static ItemStack generateItemDataRepresentation(ItemData data) {
        return data.getItem();
    }

    private static ItemStack generateLetterDataRepresentation(LetterData data) {
        SimpleDateFormat sdf = new SimpleDateFormat(LangManager.getValue("string_date_format"));
        ItemStackBuilder itemGenerator = new ItemStackBuilder(data.getLetterType().getMaterial())
            .setLoreFormat(ChatColor.WHITE.toString())
            .addAutoFormatingLore(
                String.format(
                    "%s%s" + LangManager.getValue("string_object") + ":%s%s " + data.getObject(),
                    ChatColor.YELLOW, ChatColor.BOLD, ChatColor.RESET, ChatColor.WHITE),
                35
            )
            .addAutoFormatingLore(
                String.format(
                    "%s%s" + LangManager.getValue("string_author") + ":%s%s " + data.getAuthor(),
                    ChatColor.YELLOW, ChatColor.BOLD, ChatColor.RESET, ChatColor.WHITE),
                35
            )
            .addAutoFormatingLore(
                String.format(
                    "%s%s" + LangManager.getValue("string_reception_date") + ":%s%s " + sdf.format(data.getCreationDate()),
                    ChatColor.YELLOW, ChatColor.BOLD, ChatColor.RESET, ChatColor.WHITE),
                35
            )
            .addLore(" ");

        if (!data.getIsRead()) {
            itemGenerator.enchant(Enchantment.ARROW_FIRE, 1).addFlag(ItemFlag.HIDE_ENCHANTS);
        }

        return itemGenerator.build();
    }

}
