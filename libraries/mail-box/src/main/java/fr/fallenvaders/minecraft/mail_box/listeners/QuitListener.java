package fr.fallenvaders.minecraft.mail_box.listeners;

import fr.fallenvaders.minecraft.mail_box.data_manager.MailBoxController;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Décharge de la mémoire les données des joueurs lors de leurs déconnexion
 * @author Bletrazer
 *
 */
public class QuitListener implements Listener {
	
	@EventHandler
	private void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		MailBoxController.unload(player.getUniqueId());
		
	}
	
}
