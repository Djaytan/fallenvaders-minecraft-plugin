package fr.fallenvaders.minecraft.mini_events.events.enclume.listeners;

import fr.fallenvaders.minecraft.mini_events.GameName;
import fr.fallenvaders.minecraft.mini_events.GameState;
import fr.fallenvaders.minecraft.mini_events.MiniEvents;
import fr.fallenvaders.minecraft.mini_events.events.GetEventWorld;
import fr.fallenvaders.minecraft.mini_events.events.PlayerElimination;
import fr.fallenvaders.minecraft.mini_events.events.PlayerRemaining;
import fr.fallenvaders.minecraft.mini_events.events.WhoIsWinner;
import fr.fallenvaders.minecraft.mini_events.events.enclume.EnclumeActions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import java.util.UUID;

public class EnclumeEntityDamageListener implements Listener {

  @EventHandler
  public void onDamage(EntityDamageByEntityEvent event) {
    GameName name = MiniEvents.getGame().getGameName();
    GameState state = MiniEvents.getGame().getGameState();
    String eventPrefix =
        ("§f[" + MiniEvents.getGame().getGameName().getEventColoredPrefix().toUpperCase() + "§f] ");

    if (name == GameName.ENCLUME && state == GameState.PLAYING) {
      Entity defender = event.getEntity();
      if (defender instanceof Player) {
        Player player = (Player) defender;
        UUID playerUUID = player.getUniqueId();
        if (MiniEvents.getGame().getParticipants().contains(playerUUID)) {
          if (event.getCause() == DamageCause.FALLING_BLOCK) {
            Entity damager = event.getDamager();
            FallingBlock fallB = (FallingBlock) damager;
            if (fallB.getMaterial() == Material.ANVIL) {
              event.setCancelled(true);
              player.sendMessage(
                  eventPrefix + "§7Tu as été éliminé, tu as été touché par une enclume !");
              PlayerElimination.EventEliminationTP(player);

              for (Player players : Bukkit.getServer().getOnlinePlayers()) {
                if (players.getWorld().getName().equalsIgnoreCase(GetEventWorld.getName())) {
                  EnclumeActions.EventElimationMessage(players, player);
                }
              }
              PlayerRemaining.PlayerLeft();
              MiniEvents.getGame().getParticipants().remove(playerUUID);

              WhoIsWinner.getWinner(MiniEvents.getGame().getParticipants());
            }
          }
        }
      }
    }
  }
}
