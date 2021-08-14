package fr.fallenvaders.minecraft.mini_events.listeners;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import fr.fallenvaders.minecraft.mini_events.GameState;
import fr.fallenvaders.minecraft.mini_events.MiniEventsPlugin;
import fr.fallenvaders.minecraft.mini_events.events.GetEventWorld;
import fr.fallenvaders.minecraft.mini_events.events.PlayerElimination;
import fr.fallenvaders.minecraft.mini_events.events.PlayerRemaining;
import fr.fallenvaders.minecraft.mini_events.events.WhoIsWinner;

public class PlayerTeleportListener implements Listener {

	private MiniEventsPlugin main;

	public PlayerTeleportListener(MiniEventsPlugin main) {
		this.main = main;
	}

	////////////////////////////////////////////////////
	// Lorsqu'un joueur se téléporte on vérifie si il
	// se téléporte en dehors du monde d'événement.
	//
	// Si c'est le cas, on le retire de la liste des
	// participants.
	////////////////////////////////////////////////////

	@EventHandler
	public void onTeleport(PlayerTeleportEvent event) {
		GameState state = main.getGameState();
		List<UUID> participants = main.getParticipants();

		Location toLoc = event.getTo();
		String eventName = ("\n§f[" + main.getGameName().getEventColoredPrefix().toUpperCase() + "§f] ");

		Player player = event.getPlayer();

		// On vérifie si l'état de l'événement est en PLAYING. Que le joueur se trouve dans la
		// liste des participants. Et que la localisation finale de la téléportation est
		// en dehors du monde d'événement.
		if (participants.contains(player.getUniqueId()) && !toLoc.getWorld().getName().equalsIgnoreCase(GetEventWorld.getName(main))){
			if (state == GameState.WAITING) {
				main.getParticipants().remove(player.getUniqueId()); // On le supprime de la liste des participants vu qu'il a quitté le monde des événements.
				Integer maxPlayers = main.getConfig().getInt("config-event." + main.getGameName().getRealName().toLowerCase() + ".players.max-players");

				// Broadcast d'annonce, annonçant que le joueur a quitté l'événement.
				Bukkit.broadcastMessage(player.getName() + "§7 a quitté le zone de l'événement §7(§c"
						+ main.getParticipants().size() + "§7/§c" + maxPlayers + "§7).");
			}
			else if (state == GameState.STARTING || state == GameState.PLAYING) {
	
				// Message personnel au joueur qui s'est téléporté.
				player.sendMessage(eventName + "§7Tu as été éliminé, tu t'es téléporté en dehors de l'événement !");
	
				// Envoit à tous les joueurs présent dans le monde événements, le
				// message d'élimination du joueur qui s'est téléporté.
				for (Player pls : Bukkit.getServer().getOnlinePlayers()) {
					if (pls.getWorld().getName().equalsIgnoreCase(GetEventWorld.getName(main))) {
						PlayerElimination.EventElimationMessageOutWorld(pls, player, main);
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
