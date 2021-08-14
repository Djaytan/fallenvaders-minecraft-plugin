package fr.fallenvaders.minecraft.mini_events.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.fallenvaders.minecraft.mini_events.GameState;
import fr.fallenvaders.minecraft.mini_events.MiniEventsPlugin;
import fr.fallenvaders.minecraft.mini_events.events.GetEventWorld;
import fr.fallenvaders.minecraft.mini_events.events.PlayerElimination;
import fr.fallenvaders.minecraft.mini_events.events.PlayerRemaining;
import fr.fallenvaders.minecraft.mini_events.events.WhoIsWinner;

public class PlayerQuitListener implements Listener {

	private MiniEventsPlugin main;

	public PlayerQuitListener(MiniEventsPlugin main) {
		this.main = main;
	}

	////////////////////////////////////////////////////
	// Lorsqu'un joueur se deconnecte on vérifie si il
	// est dans la liste des participants.
	//
	// On le met ensuite dans la liste des déconnectés.
	////////////////////////////////////////////////////

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		GameState state = main.getGameState();

		Player player = event.getPlayer();
		UUID playerUUID = player.getUniqueId();

		if (state == GameState.WAITING || state == GameState.STARTING || state == GameState.PLAYING
				|| state == GameState.FINISH) {
			if (main.getParticipants().contains(playerUUID)) {

				Player disconnectedPlayer = Bukkit.getPlayer(playerUUID);

				main.getLeaveDuringEvent().add(playerUUID); // On l'ajoute dans liste des déconnectés.
				main.getParticipants().remove(playerUUID); // On le supprime de la liste des participants.

				// Le joueur s'est déconnecté quand l'événement était en WAITING.
				if (state == GameState.WAITING) {
					String eventName = main.getGameName().getRealName().toLowerCase();
					Integer maxPlayers = main.getConfig().getInt("config-event." + eventName + ".players.max-players");

					// Broadcast d'annonce, annonçant que le joueur a quitté l'événement.
					Bukkit.broadcastMessage(disconnectedPlayer.getName() + "§7 s'est déconnecté §7(§c"
							+ main.getParticipants().size() + "§7/§c" + maxPlayers + "§7).");
				}

				// Le joueur s'est déconnecté quand l'événement était en STARTING ou PLAYING.
				if (state == GameState.PLAYING || state == GameState.STARTING) {
					// Envoit à tous les joueurs présent dans le monde des événements, le
					// message d'élimination du joueur qui s'est déconnecté.
					for (Player pls : Bukkit.getServer().getOnlinePlayers()) {
						if (pls.getWorld().getName().equalsIgnoreCase(GetEventWorld.getName(main))) {
							PlayerElimination.EventElimationMessageQuit(pls, disconnectedPlayer, main);
						}
					}
					// On retire le joueur des participants.
					main.getParticipants().remove(player.getUniqueId());
					// Indique le nombre de joueur restant.
					PlayerRemaining.PlayerLeft(main);
					
					if (state == GameState.PLAYING) {
						// Après sa déconnexion, nous regardons si il y a un gagnant dans la partie.
						WhoIsWinner.getWinner(main.getParticipants(), main);
					}
				}
			}
		}
	}
}
