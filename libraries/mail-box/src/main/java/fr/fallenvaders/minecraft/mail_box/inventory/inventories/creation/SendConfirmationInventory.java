package fr.fallenvaders.minecraft.mail_box.inventory.inventories.creation;

import fr.fallenvaders.minecraft.mail_box.data_manager.*;
import fr.fallenvaders.minecraft.mail_box.data_manager.factories.DataFactory;
import fr.fallenvaders.minecraft.mail_box.inventory.builders.ConfirmationInventoryBuilder;
import fr.fallenvaders.minecraft.mail_box.inventory.builders.InventoryBuilder;
import fr.fallenvaders.minecraft.mail_box.inventory.inventories.utils.IdentifiersList;
import fr.fallenvaders.minecraft.mail_box.player_manager.PlayerInfo;
import fr.fallenvaders.minecraft.mail_box.utils.LangManager;
import fr.fallenvaders.minecraft.mail_box.utils.MessageLevel;
import fr.fallenvaders.minecraft.mail_box.utils.MessageUtils;
import fr.minuskube.inv.content.InventoryContents;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SendConfirmationInventory extends ConfirmationInventoryBuilder {
    public static final String SUB_ID = "confirmation_sendLetter";

    private static final String SEND_CONFIRMATION = LangManager.getValue("string_send_confirmation");
    private static final String SEND_LETTER = LangManager.getValue("string_send_letter");
    private static final String SEND_ITEM = LangManager.getValue("string_send_item");

    private IdentifiersList recipients;
    private String object;
    private List<String> content;
    private Duration duration;
    private ItemStack item;

    public SendConfirmationInventory(InventoryBuilder parent, IdentifiersList recipients, String object, List<String> content, Duration duration, ItemStack item) {
        super(SUB_ID, ChatColor.BOLD + SEND_CONFIRMATION);
        super.setParent(parent);
        this.setRecipients(recipients);
        this.setObject(object);
        this.setContent(content);
        this.setDuration(duration);
        this.setItem(item);
    }

    @Override
    public void onUpdate(Player player, InventoryContents contents) {
    }

    @Override
    public Consumer<InventoryClickEvent> onConfirmation(Player player, InventoryContents contents) {
        return e -> {
            ClickType click = e.getClick();
            boolean error = false;

            if (click == ClickType.LEFT) {
                LetterType type = getRecipients().getPlayerList().size() > 1 ? LetterType.ANNOUNCE : LetterType.STANDARD;
                List<LetterData> letters = new ArrayList<>();
                List<ItemData> items = new ArrayList<>();

                for (PlayerInfo pi : getRecipients().getPlayerList()) {
                    Data data = new DataFactory(pi.getUuid(), player.getName(), getObject());

                    if (!getContent().isEmpty()) {
                        letters.add(new LetterData(data.clone(), type, getContent(), false));
                    }

                    if (getItem() != null) {
                        items.add(new ItemData(data.clone(), getItem(), this.getDuration()));
                    }

                }

                if (!letters.isEmpty()) {
                    if (MailBoxController.sendLetters(player, letters)) {
                        MessageUtils.sendMessage(player, MessageLevel.INFO, LangManager.format(SEND_LETTER, ": " + getRecipients().getPreviewString()));

                    } else {
                        error = true;
                    }
                }

                if (!error && !items.isEmpty()) {
                    if (MailBoxController.sendItems(player, items)) {
                        MessageUtils.sendMessage(player, MessageLevel.INFO, LangManager.format(SEND_ITEM, ": " + getRecipients().getPreviewString()));

                    } else {
                        error = true;
                    }
                }

                player.closeInventory();
                CreationInventory.newInventory(player.getUniqueId());
            }
        };
    }

    @Override
    public Consumer<InventoryClickEvent> onAnnulation(Player player, InventoryContents contents) {
        return null;
    }

    public IdentifiersList getRecipients() {
        return recipients;
    }

    public void setRecipients(IdentifiersList recipients) {
        this.recipients = recipients;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

}
