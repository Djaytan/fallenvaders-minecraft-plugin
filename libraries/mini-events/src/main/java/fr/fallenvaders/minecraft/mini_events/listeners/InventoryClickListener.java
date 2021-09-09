package fr.fallenvaders.minecraft.mini_events.listeners;

import fr.fallenvaders.minecraft.mini_events.GameName;
import fr.fallenvaders.minecraft.mini_events.MiniEvents;
import fr.fallenvaders.minecraft.mini_events.events.GetSpawnsParameters;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class InventoryClickListener implements Listener {

  // Evénement utilisé pour l'interface "graphique" de la liste des événements
  // dans un inventaire séparé.
  @EventHandler
  public void onClick(InventoryClickEvent event) {
    ItemStack currentType = event.getCurrentItem();
    Inventory inv = event.getInventory();

    if (currentType == null) {
      return;
    }

    // inv.getName to inv.getType().name()
    if (inv.getType().name().equals("§0Menu des événements")) {
      Player player = (Player) event.getWhoClicked();
      String currentName = event.getCurrentItem().getItemMeta().getDisplayName();

      event.setCancelled(true);
      List<GameName> eventNames = Arrays.asList(GameName.values());
      for (int i = 0; i < eventNames.size(); i++) {
        if (currentName.equals(
            "§f§lEvent: §r" + eventNames.get(i).getEventColoredPrefix().toUpperCase())) {
          messageAndTeleport(eventNames.get(i), player);
          return;
        }
      }
    } else {
      return;
    }
  }

  // Message de téléportation lors d'un clic sur un item du menu.
  private void messageAndTeleport(GameName gameName, Player player) {
    player.closeInventory();
    player.sendMessage(
        MiniEvents.PREFIX
            + "§fTéléportation sur l'événement "
            + gameName.getEventColoredPrefix().toUpperCase()
            + "§f.");
    player.teleport(GetSpawnsParameters.EventTeleportOnSpawn(gameName.getRealName()));
  }
}
