package fr.fallenvaders.minecraft.mail_box.inventory.inventories;

import fr.fallenvaders.minecraft.mail_box.inventory.builders.InventoryBuilder;
import fr.fallenvaders.minecraft.mail_box.inventory.inventories.utils.IdentifiersList;
import fr.fallenvaders.minecraft.mail_box.listeners.utils.ChatHooker;
import fr.fallenvaders.minecraft.mail_box.listeners.utils.hookers.CH_Player;
import fr.fallenvaders.minecraft.mail_box.utils.ItemStackBuilder;
import fr.fallenvaders.minecraft.mail_box.utils.LangManager;
import fr.fallenvaders.minecraft.mail_box.utils.MessageLevel;
import fr.fallenvaders.minecraft.mail_box.utils.MessageUtils;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class PlayerSelectionInventory extends InventoryBuilder {
    public static final String ID = "MailBox_Player_Selector";
    public static final Material CHOOSE_ALL_MATERIAL = Material.NETHER_STAR;
    public static final Material CHOOSE_FACTION_MATERIAL = Material.MAGENTA_BANNER;
    public static final Material CHOOSE_PRECISE_PLAYER_MATERIAL = Material.PLAYER_HEAD;
    private static final String PRECISE = LangManager.getValue("string_choose_precise_player");
    private static final String CHOOSE_SERVER = LangManager.getValue("string_choose_server");
    private static final String CHOOSE_ONLINE = LangManager.getValue("string_choose_server_online");
    private static final String CHOOSE_OFFLINE = LangManager.getValue("string_choose_server_offline");
    private static final String DISPLAYED_PLAYERS = LangManager.getValue("string_displayed_players");
    private static final String HELP_DELETE_FILTER = LangManager.getValue("help_delete_player_filter");

    private IdentifiersList identifiersList;
    private boolean filterMode = true;

    public PlayerSelectionInventory(IdentifiersList identifiersList, String invTitle, InventoryBuilder parent) {
        super(ID, invTitle, 3);
        super.setParent(parent);
        this.setIdentifiersList(identifiersList);
    }

    @Override
    public void initializeInventory(Player player, InventoryContents contents) {
        if (this.getFilterMode() || player.hasPermission("mailbox.send.annouce")) {

            contents.set(1, 6, ClickableItem.of(new ItemStackBuilder(CHOOSE_ALL_MATERIAL)
                .setName(String.format("%s%s" + CHOOSE_SERVER, ChatColor.WHITE, ChatColor.BOLD))
                .addAutoFormatingLore(CHOOSE_ONLINE, 35)
                .addAutoFormatingLore(CHOOSE_OFFLINE, 35)
                .build(), e -> {
                ClickType clickType = e.getClick();

                if (clickType == ClickType.LEFT) {
                    this.getIdentifiersList().addIdentifier("#online");

                } else if (clickType == ClickType.RIGHT) {
                    this.getIdentifiersList().addIdentifier("#offline");
                }

            }));
        }

        contents.set(1, 2, ClickableItem.of(new ItemStackBuilder(CHOOSE_PRECISE_PLAYER_MATERIAL)
                .setName(String.format("%s%s" + PRECISE, ChatColor.WHITE, ChatColor.BOLD)).build(),
            (e) -> {
                if (e.getClick() == ClickType.LEFT) {
                    ChatHooker chatHooker = ChatHooker.get(player.getUniqueId());

                    if (chatHooker == null) {
                        chatHooker = new CH_Player(this.getIdentifiersList(), this);
                        player.closeInventory();
                        chatHooker.start(player);

                    } else {
                        MessageUtils.sendMessage(player, MessageLevel.ERROR, LangManager.getValue("string_end_last_entry_first"));
                    }
                }
            }
        ));
    }

    @Override
    public void updateInventory(Player player, InventoryContents contents) {
        int state = contents.property("state", 0);
        contents.setProperty("state", state + 1);

        if (state % 20 != 0) {
            return;
        }

        contents.set(0, 4, ClickableItem.of(new ItemStackBuilder(Material.REDSTONE)
            .setName(String.format("%s%s" + DISPLAYED_PLAYERS + ":", ChatColor.WHITE, ChatColor.BOLD))
            .addLores(this.getIdentifiersList().getPreviewLore())
            .addLore(HELP_DELETE_FILTER)
            .build(), e -> {
            if (e.getClick() == ClickType.DROP || e.getClick() == ClickType.CONTROL_DROP) {
                this.getIdentifiersList().clear();
            }
        }));


    }

    public IdentifiersList getIdentifiersList() {
        return identifiersList;
    }

    public void setIdentifiersList(IdentifiersList identifiersList) {
        this.identifiersList = identifiersList;
    }

    public boolean getFilterMode() {
        return filterMode;
    }

    public void setFilterMode(boolean filterMode) {
        this.filterMode = filterMode;
    }
}
