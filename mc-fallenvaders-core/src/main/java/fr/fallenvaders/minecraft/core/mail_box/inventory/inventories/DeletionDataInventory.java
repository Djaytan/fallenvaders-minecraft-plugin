package fr.fallenvaders.minecraft.core.mail_box.inventory.inventories;

import fr.fallenvaders.minecraft.core.mail_box.data_manager.Data;
import fr.fallenvaders.minecraft.core.mail_box.data_manager.DataHolder;
import fr.fallenvaders.minecraft.core.mail_box.data_manager.ItemData;
import fr.fallenvaders.minecraft.core.mail_box.data_manager.MailBoxController;
import fr.fallenvaders.minecraft.core.mail_box.inventory.builders.ConfirmationInventoryBuilder;
import fr.fallenvaders.minecraft.core.mail_box.inventory.builders.InventoryBuilder;
import fr.minuskube.inv.content.InventoryContents;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class DeletionDataInventory extends ConfirmationInventoryBuilder {
	public static final String INVENTORY_SUB_ID = "deleteItem";

	private DataHolder dataSource;
	private Data data;
	private Boolean update = false;

	public DeletionDataInventory(DataHolder dataSource, Data data, String InventoryTitle, InventoryBuilder parent) {
		super(INVENTORY_SUB_ID, InventoryTitle);
		this.setDataSource(dataSource);
		this.setData(data);
		this.setParent(parent);
		if (data instanceof ItemData) {
			update = true;
		}
	}

	@Override
	public Consumer<InventoryClickEvent> onConfirmation(Player player, InventoryContents contents) {
		return e -> {
			if (e.getClick() == ClickType.LEFT) {
				if (MailBoxController.deleteData(player, this.getDataSource(), this.getData())) {
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

        if(state % 20 != 0) {
        	return;
        }
        
		if (update) {
			ItemData tempData = (ItemData) this.getData();

			if (tempData.isOutOfDate()) {
				MailBoxController.deleteItem(player, this.getDataSource(), tempData);
				returnToParent(player);
			}
		}
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public DataHolder getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataHolder dataSource) {
		this.dataSource = dataSource;
	}

}
