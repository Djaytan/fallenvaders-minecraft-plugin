package fr.fallenvaders.minecraft.mail_box.listeners;

import fr.fallenvaders.minecraft.mail_box.data_manager.MailBoxController;
import fr.fallenvaders.minecraft.mail_box.player_manager.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Charge en mémoire les données des joueurs lors de leurs connexion
 *
 * @author Bletrazer
 */
public class JoinListener implements Listener {

  @EventHandler
  private void onJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    MailBoxController.load(player.getUniqueId());

    PlayerManager.getInstance().load(player);
  }
}
