package fr.fallenvaders.minecraft.mini_events.events;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.fallenvaders.minecraft.mini_events.GameName;
import fr.fallenvaders.minecraft.mini_events.GameState;
import fr.fallenvaders.minecraft.mini_events.MiniEventsPlugin;

public class OnStartEndOfEvent {

	// Cooldown de départ mais quand ce dernier commence.
	public static void CooldownStartOnStart(Player player, MiniEventsPlugin main) {
		main.setGameState(GameState.STARTING);
		player.teleport(GetSpawnsParameters.LocationSpawnArene(main));
		PlayerSettings.setSettings(player);

		main.getGameName().getSpecificItem(player); // Donne les objets de l'événement au joueur.
		player.setInvulnerable(main.getGameName().getVulnerable()); // Donne l'invulnérabilité au joueur ou pas, selon les paramètres de l'événement.
	}

	// Cooldown de départ mais quand ce dernier se termine.
	public static void CooldownStartOnEnd(Player player, MiniEventsPlugin main) {
		// Met le mode de jeu associé à l'événement au joueur.
		player.setGameMode(main.getGameName().getGamemode());
	}

	// Quand l'événement commence à se terminer normalement avec 1 joueur restant
	// (GameState.FINISH)
	public static void EventStateFinish(MiniEventsPlugin main) {
		UUID playerWhoWinUUID = main.getParticipants().get(0);
		Player playerWhoWin = Bukkit.getPlayer(playerWhoWinUUID);
		String winner = playerWhoWin.getName();

		// Message pour tous les joueurs
		Bukkit.broadcastMessage("\n§f[§b" + main.getGameName().getVisualName().toUpperCase()
				+ "§f] §7Bravo à tous les participants de l'événement !");
		Bukkit.broadcastMessage("\n§f[§b" + main.getGameName().getVisualName().toUpperCase()
				+ "§f] §7Le grand vainqueur de cette événement n'est autre que le joueur §6§l" + winner + "§7 !\n ");

		playerWhoWin.setGameMode(GameMode.ADVENTURE);
		main.setGameState(GameState.FINISH);

		CooldownOnEnd start = new CooldownOnEnd(main);
		start.runTaskTimer(main, 0, 20);
	}

	// Quand l'événement se termine réellement (R.A.Z.)
	// (Après l'état FINISH)
	public static void EventEndAndRAZ(MiniEventsPlugin main) {
		EventRAZ(main);
	}

	// Quand un membre du personnel décide de faire la commande d'arrêt forcé de
	// l'évenement.
	public static void EventStop(MiniEventsPlugin main) {
		Bukkit.broadcastMessage(main.prefix + "§cL'événement §c§l" + main.getGameName().getVisualName().toUpperCase()
				+ "§r§c a été arrêté manuellement !");

		if (main.getParticipants().size() >= 1) {
			for (UUID playerUUID : main.getParticipants()) {
				Player player = Bukkit.getPlayer(playerUUID);

				if (player.getWorld().getName().equalsIgnoreCase(GetEventWorld.getName(main))) {
					player.teleport(GetSpawnsParameters.LocationEventSpawn(main));
					PlayerSettings.setSettings(player);
				}
				player.sendMessage(main.prefix + "§7Téléportation de tous les joueurs !");
			}
		}
		EventRAZ(main);
	}

	// Permet de lancer la régénération d'un événement.
	private static void EventRegeneration(MiniEventsPlugin main) {
		// On vérifie si la liste contient une valeur.
		if (main.getBlockLoc().size() > 0) {

			// Régénération du SPLEEF:
			if (main.getGameName() == GameName.SPLEEF) {
				for (Location blockLocation : main.getBlockLoc()) {
					blockLocation.getBlock().setType(Material.SNOW_BLOCK);
				}
			}
			// Régénération du BOWSPLEEF:
			if (main.getGameName() == GameName.BOWSPLEEF) {
				for (Location blockLocation : main.getBlockLoc()) {
					blockLocation.getBlock().setType(Material.TNT);
				}
			}

			// On vide la liste pour les prochains événements.
			main.getBlockLoc().clear();

		} else {
			return;
		}
	}

	// Permet de remettre à zéro un événement.
	private static void EventRAZ(MiniEventsPlugin main) {
		EventRegeneration(main); // Régénération de la map

		main.setGameState(GameState.NOTSTARTED); // Le status de jeu change en "NOTSTARTED", l'event se termine
		main.setGameName(GameName.NONE); // Le jeu passe en "NONE" pour dire que aucun jeu n'a démarré.
		main.getParticipants().clear(); // On vide la liste des participants.
		main.getLeaveDuringEvent().clear(); // De même pour ceux qui ont quitté en cours de route.

	}
}
