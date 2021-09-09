package fr.fallenvaders.minecraft.mini_events.events.spleef.listeners;

import fr.fallenvaders.minecraft.mini_events.GameName;
import fr.fallenvaders.minecraft.mini_events.GameState;
import fr.fallenvaders.minecraft.mini_events.MiniEvents;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.UUID;

public class SpleefBlockBreakListener implements Listener {

  // Evénement enregistrant chaque blocs cassés lors d'un SPLEEF
  @EventHandler
  public void onBreak(BlockBreakEvent event) {
    GameName name = MiniEvents.getGame().getGameName();
    GameState state = MiniEvents.getGame().getGameState();
    if (name == GameName.SPLEEF && state == GameState.PLAYING) {
      Player player = event.getPlayer();
      UUID playerUUID = player.getUniqueId();
      if (MiniEvents.getGame().getParticipants().contains(playerUUID)) {
        Block block = event.getBlock();
        Material material = block.getType();
        if (material == Material.SNOW_BLOCK) {
          MiniEvents.getGame().getBlockLoc().add(block.getLocation());
          event.setDropItems(false);
        } else {
          event.setCancelled(true);
        }
      }
    }
  }
}
