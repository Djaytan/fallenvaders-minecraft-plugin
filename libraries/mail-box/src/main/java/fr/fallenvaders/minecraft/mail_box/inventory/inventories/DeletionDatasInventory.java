package fr.fallenvaders.minecraft.mail_box.inventory.inventories;

import fr.fallenvaders.minecraft.mail_box.data_manager.Data;
import fr.fallenvaders.minecraft.mail_box.data_manager.DataHolder;
import fr.fallenvaders.minecraft.mail_box.data_manager.ItemData;
import fr.fallenvaders.minecraft.mail_box.data_manager.MailBoxController;
import fr.fallenvaders.minecraft.mail_box.inventory.builders.ConfirmationInventoryBuilder;
import fr.fallenvaders.minecraft.mail_box.inventory.builders.InventoryBuilder;
import fr.minuskube.inv.content.InventoryContents;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class DeletionDatasInventory extends ConfirmationInventoryBuilder {
  public static final String INVENTORY_SUB_ID = "deleteItems";

  private DataHolder dataSource;
  private List<Data> dataList = new ArrayList<>();
  private boolean doUpdate = false;

  public DeletionDatasInventory(
      DataHolder dataSource,
      List<Data> dataList,
      String inventoryTitle,
      InventoryBuilder parent,
      boolean doUpdate) {
    super(INVENTORY_SUB_ID, inventoryTitle);
    this.setDataSource(dataSource);
    this.setDataList(dataList);
    this.setParent(parent);
    this.doUpdate = doUpdate;
  }

  @Override
  public Consumer<InventoryClickEvent> onConfirmation(Player player, InventoryContents contents) {
    return e -> {
      if (e.getClick() == ClickType.LEFT) {

        if (MailBoxController.deleteDatas(player, this.getDataSource(), getDataList())) {
          this.returnToParent(player);

        } else {
          player.closeInventory();
        }
      }
    };
  }

  @Override
  public Consumer<InventoryClickEvent> onAnnulation(Player player, InventoryContents contents) {
    return null;
  }

  @Override
  public void onUpdate(Player player, InventoryContents contents) {
    int state = contents.property("state", 0);
    contents.setProperty("state", state + 1);

    if (state % 20 != 0) {
      return;
    }

    if (this.doUpdate) {
      Iterator<Data> it = this.getDataList().iterator();

      while (it.hasNext()) {
        Data data = it.next();

        if (data != null) {
          if (data instanceof ItemData) {
            ItemData tempData = (ItemData) data;

            if (tempData.isOutOfDate()) {
              if (MailBoxController.deleteItem(player, this.getDataSource(), tempData)) {
                if (this.getDataList().isEmpty()) {
                  this.returnToParent(player);
                }
              } else {
                player.closeInventory();
              }
              it.remove();
            }
          }
        } else {
          it.remove();
        }
      }
    }
  }

  public DataHolder getDataSource() {
    return this.dataSource;
  }

  private void setDataSource(DataHolder holder) {
    this.dataSource = holder;
  }

  public List<Data> getDataList() {
    return dataList;
  }

  public void setDataList(List<Data> dataList) {
    this.dataList = dataList;
  }
}
