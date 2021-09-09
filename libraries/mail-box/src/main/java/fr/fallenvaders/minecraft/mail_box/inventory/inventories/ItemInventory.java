package fr.fallenvaders.minecraft.mail_box.inventory.inventories;

import fr.fallenvaders.minecraft.mail_box.data_manager.DataHolder;
import fr.fallenvaders.minecraft.mail_box.data_manager.DataManager;
import fr.fallenvaders.minecraft.mail_box.data_manager.ItemData;
import fr.fallenvaders.minecraft.mail_box.data_manager.MailBoxController;
import fr.fallenvaders.minecraft.mail_box.inventory.MailBoxInventoryHandler;
import fr.fallenvaders.minecraft.mail_box.inventory.builders.InventoryBuilder;
import fr.fallenvaders.minecraft.mail_box.utils.ItemStackBuilder;
import fr.fallenvaders.minecraft.mail_box.utils.LangManager;
import fr.fallenvaders.minecraft.mail_box.utils.MessageLevel;
import fr.fallenvaders.minecraft.mail_box.utils.MessageUtils;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.List;

public class ItemInventory extends InventoryBuilder {
  private static final String TITLE = LangManager.getValue("string_menu_items");
  private static final String CLEAN = LangManager.getValue("string_clean_inbox");
  private static final String QUESTION_CLEAN = LangManager.getValue("question_clean_items");
  private static final String PERMISSION_NEEDED = LangManager.getValue("string_permission_needed");
  private static final String QUESTION_DELETE = LangManager.getValue("question_delete_item");
  private static final String RETREIVE_ALL = LangManager.getValue("string_retreive_all");

  private DataHolder dataSource;
  private List<ItemData> toShow = new ArrayList<>();

  public ItemInventory(DataHolder dataSource) {
    super("MailBox_Items", ChatColor.BOLD + TITLE, 5);
    this.setDataSource(dataSource);
  }

  public ItemInventory(DataHolder dataSource, InventoryBuilder parent) {
    super("MailBox_Items", ChatColor.BOLD + TITLE, 5);
    this.setDataSource(dataSource);
    this.setParent(parent);
  }

  private void deleteAllButton(Player player, InventoryContents contents) {
    boolean playerIsOwner = this.getDataSource().getOwnerUuid().equals(player.getUniqueId());

    if (playerIsOwner && player.hasPermission("mailbox.item.delete.self")
        || player.hasPermission("mailbox.item.delete.other")) {

      contents.set(
          4,
          6,
          ClickableItem.of(
              new ItemStackBuilder(Material.BARRIER)
                  .setName(String.format("%s%s%S", ChatColor.DARK_RED, ChatColor.BOLD, CLEAN))
                  .build(),
              e -> {
                if (e.getClick() == ClickType.LEFT) {
                  if (getToShow() != null && !getToShow().isEmpty()) {
                    DeletionDatasInventory deletionDatasInventory =
                        new DeletionDatasInventory(
                            this.getDataSource(),
                            new ArrayList<>(this.getToShow()),
                            LangManager.format(
                                "%s%s" + QUESTION_CLEAN,
                                ChatColor.DARK_RED,
                                ChatColor.BOLD,
                                this.getToShow().size()),
                            this,
                            true);
                    deletionDatasInventory.openInventory(player);
                  }
                }
              }));
    }
  }

  private void dynamicContent(Player player, InventoryContents contents) {
    int state = contents.property("state", 0);
    contents.setProperty("state", state + 1);

    if (state % 20 != 0) {
      return;
    }

    this.setToShow(DataManager.getTypeData(this.getDataSource(), ItemData.class));

    ClickableItem[] clickableItems = new ClickableItem[getToShow().size()];

    for (int index = 0; index < getToShow().size(); index++) {
      ItemData tempData = getToShow().get(index);

      if (tempData.isOutOfDate()) {
        if (!MailBoxController.deleteItem(player, this.getDataSource(), tempData)) {
          MessageUtils.sendMessage(
              player, MessageLevel.ERROR, LangManager.getValue("string_error_player"));
          player.closeInventory();
        }
      } else {
        clickableItems[index] =
            ClickableItem.of(
                MailBoxInventoryHandler.generateItemRepresentation(tempData),
                e -> {
                  ClickType clickType = e.getClick();
                  boolean playerIsOwner =
                      this.getDataSource().getOwnerUuid().equals(player.getUniqueId());

                  if (clickType == ClickType.LEFT) {
                    if (playerIsOwner && player.hasPermission("mailbox.item.recover.self")
                        || player.hasPermission("mailbox.item.recover.other")) {
                      MailBoxController.recoverItem(player, getDataSource(), tempData);
                      contents.setProperty("state", 0);
                      this.dynamicContent(player, contents);
                    } else {
                      MessageUtils.sendMessage(player, MessageLevel.ERROR, PERMISSION_NEEDED);
                    }

                  } else if (clickType == ClickType.CONTROL_DROP || clickType == ClickType.DROP) {
                    if (playerIsOwner && player.hasPermission("mailbox.item.delete.self")
                        || player.hasPermission("mailbox.item.delete.other")) {
                      DeletionDataInventory inv =
                          new DeletionDataInventory(
                              this.getDataSource(), tempData,
                              String.format(
                                      "%s%s" + QUESTION_DELETE, ChatColor.RED, ChatColor.BOLD),
                                  this);
                      inv.openInventory(player);

                    } else {
                      MessageUtils.sendMessage(player, MessageLevel.ERROR, PERMISSION_NEEDED);
                    }
                  }
                });
      }
    }

    Pagination pagination = contents.pagination();
    pagination.setItems(clickableItems);
    pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 0, 0));
  }

  @Override
  public void initializeInventory(Player player, InventoryContents contents) {
    Pagination pagination = contents.pagination();
    pagination.setItemsPerPage(27);

    this.dynamicContent(player, contents);

    deleteAllButton(player, contents);

    contents.fillRow(
        3,
        ClickableItem.empty(
            new ItemStackBuilder(Material.BLACK_STAINED_GLASS_PANE).setName(" ").build()));

    if (!pagination.isFirst()) {
      contents.set(4, 1, this.previousPageItem(player, contents));
    }

    boolean playerIsOwner = this.getDataSource().getOwnerUuid().equals(player.getUniqueId());

    if (playerIsOwner && player.hasPermission("mailbox.item.recover.self")
        || player.hasPermission("mailbox.item.recover.other")) {
      contents.set(4, 2, recoverAllButton(player, contents));
    }

    if (!pagination.isLast()) {
      contents.set(4, 7, this.nextPageItem(player, contents));
    }
  }

  @Override
  public void updateInventory(Player player, InventoryContents contents) {
    dynamicContent(player, contents);
  }

  private ClickableItem recoverAllButton(Player player, InventoryContents contents) {
    ItemStackBuilder itemStackBuilder =
        new ItemStackBuilder(Material.CHEST)
            .setName(String.format("%s%s" + RETREIVE_ALL, ChatColor.YELLOW, ChatColor.BOLD));

    return ClickableItem.of(
        itemStackBuilder.build(),
        e -> {
          if (e.getClick() == ClickType.LEFT) {
            if (getToShow() != null && !getToShow().isEmpty()) {
              List<ItemData> dataList = getToShow();
              dataList.sort(DataManager.descendingDateComparator().reversed());

              for (ItemData itemData : dataList) {
                if (!MailBoxController.recoverItem(player, this.getDataSource(), itemData)) {
                  break;
                }
              }

              contents.setProperty("state", 0);
              this.dynamicContent(player, contents);
            }
          }
        });
  }

  public DataHolder getDataSource() {
    return dataSource;
  }

  public void setDataSource(DataHolder dataSource) {
    this.dataSource = dataSource;
  }

  public List<ItemData> getToShow() {
    return toShow;
  }

  public void setToShow(List<ItemData> toShow) {
    this.toShow = toShow;
  }
}
