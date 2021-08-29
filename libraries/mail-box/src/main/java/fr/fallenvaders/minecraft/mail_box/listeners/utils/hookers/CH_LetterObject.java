package fr.fallenvaders.minecraft.mail_box.listeners.utils.hookers;

import fr.fallenvaders.minecraft.mail_box.inventory.builders.InventoryBuilder;
import fr.fallenvaders.minecraft.mail_box.listeners.utils.ChatHooker;
import org.bukkit.entity.Player;

public class CH_LetterObject extends ChatHooker {

    public CH_LetterObject(String startMsg, StringBuilder object, InventoryBuilder parentInv) {
        super("MailBox_LetterContent_ChatHooker", startMsg);

        this.setExecution(event -> {
            Player ePlayer = event.getPlayer();
            String eMessage = event.getMessage();
            event.setCancelled(true);

            if (eMessage.equals("#stop")) {
                parentInv.openInventory(ePlayer);
                this.stop();
                return;
            }


            object.setLength(0);

            object.append(eMessage);


            this.stop();
            parentInv.openInventory(ePlayer);

        });
    }
}
