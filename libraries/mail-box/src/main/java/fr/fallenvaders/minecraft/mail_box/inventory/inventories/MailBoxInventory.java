package fr.fallenvaders.minecraft.mail_box.inventory.inventories;

import fr.fallenvaders.minecraft.mail_box.data_manager.DataHolder;
import fr.fallenvaders.minecraft.mail_box.inventory.builders.InventoryBuilder;
import fr.fallenvaders.minecraft.mail_box.inventory.inventories.creation.CreationInventory;
import fr.fallenvaders.minecraft.mail_box.utils.ItemStackBuilder;
import fr.fallenvaders.minecraft.mail_box.utils.LangManager;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class MailBoxInventory extends InventoryBuilder {
    private static final String LETTERS = LangManager.getValue("string_menu_letters");
    private static final String ITEMS = LangManager.getValue("string_menu_items");
    private static final String CREATION = LangManager.getValue("string_menu_creation");

    private DataHolder dataSource;

    public MailBoxInventory(DataHolder dataSource) {
        super("MailBox_Principal", ChatColor.BOLD + "Menu principal", 3);
        this.setDataSource(dataSource);
    }

    @Override
    public void initializeInventory(Player player, InventoryContents contents) {

        contents.set(1, 2, ClickableItem.of(
            new ItemStackBuilder(Material.BOOKSHELF).setName(String.format("%s%s" + LETTERS, ChatColor.WHITE, ChatColor.BOLD)).build(),
            (e) -> {
                if (e.getClick() == ClickType.LEFT) {
                    LetterInventory inv = new LetterInventory(this.getDataSource(), this);
                    inv.openInventory(player);
                }
            }
        ));

        contents.set(1, 6, ClickableItem.of(
            new ItemStackBuilder(Material.CHEST).setName(String.format("%s%s" + ITEMS, ChatColor.WHITE, ChatColor.BOLD)).build(),
            (e) -> {
                if (e.getClick() == ClickType.LEFT) {
                    ItemInventory inv = new ItemInventory(this.getDataSource(), this);
                    inv.openInventory(player);
                }
            }
        ));

        if (player.hasPermission("mailbox.send.standard")) {
            contents.set(2, 4, ClickableItem.of(
                new ItemStackBuilder(Material.HOPPER).setName(String.format("%s%s" + CREATION, ChatColor.WHITE, ChatColor.BOLD)).build(),
                (e) -> {
                    if (e.getClick() == ClickType.LEFT) {
                        CreationInventory inv = CreationInventory.getInventory(player.getUniqueId());
                        inv.setParent(this);
                        inv.openInventory(player);

                    }

                }
            ));
        }
    }

    @Override
    public void updateInventory(Player player, InventoryContents contents) {

    }

    private DataHolder getDataSource() {
        return dataSource;
    }

    private void setDataSource(DataHolder dataSource) {
        this.dataSource = dataSource;
    }

}
