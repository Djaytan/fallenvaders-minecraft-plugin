package fr.fallenvaders.minecraft.mail_box.inventory.inventories;

import fr.fallenvaders.minecraft.mail_box.data_manager.LetterData;
import fr.fallenvaders.minecraft.mail_box.inventory.builders.ConfirmationInventoryBuilder;
import fr.fallenvaders.minecraft.mail_box.inventory.builders.InventoryBuilder;
import fr.fallenvaders.minecraft.mail_box.sql.LetterDataSQL;
import fr.fallenvaders.minecraft.mail_box.utils.LangManager;
import fr.fallenvaders.minecraft.mail_box.utils.MessageLevel;
import fr.fallenvaders.minecraft.mail_box.utils.MessageUtils;
import fr.minuskube.inv.content.InventoryContents;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MarkAllLettersInventory extends ConfirmationInventoryBuilder {
  private static final String MARK_ALL_2 = LangManager.getValue("string_mark_all_as_read");
  private static final String SUB_ID = "mark_all";
  private List<LetterData> dataList = new ArrayList<>();

  public MarkAllLettersInventory(List<LetterData> dataList, InventoryBuilder parent) {
    super(SUB_ID, ChatColor.BOLD + LangManager.format(MARK_ALL_2, dataList.size()));
    super.setParent(parent);
    this.dataList = dataList;
  }

  @Override
  public void onUpdate(Player player, InventoryContents contents) {}

  @Override
  public Consumer<InventoryClickEvent> onConfirmation(Player player, InventoryContents contents) {
    return event -> {
      for (LetterData letter : dataList) {
        letter.setIsRead(true);
      }
      LetterDataSQL letterDataSQL = new LetterDataSQL();
      List<LetterData> tempList = letterDataSQL.updateAll(dataList);

      if (tempList != null) {
        for (LetterData letter : dataList) {
          letter.setIsRead(false);
        }
        this.returnToParent(player);

      } else {
        MessageUtils.sendMessage(
            player, MessageLevel.ERROR, LangManager.getValue("string_error_player"));
        player.closeInventory();
      }
    };
  }

  @Override
  public Consumer<InventoryClickEvent> onAnnulation(Player player, InventoryContents contents) {
    return null;
  }
}
