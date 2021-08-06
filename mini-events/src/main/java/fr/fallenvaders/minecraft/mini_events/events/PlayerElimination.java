package fr.fallenvaders.minecraft.mini_events.events;

import org.bukkit.entity.Player;

import fr.fallenvaders.minecraft.mini_events.MiniEventsPlugin;

public class PlayerElimination {

	// Téléporte le joueur au spawn de l'événement et le "réinitialise".
	public static void EventEliminationTP(Player player, MiniEventsPlugin main) {
		if (player.getWorld().getName().equalsIgnoreCase(GetEventWorld.getName(main))) {
			PlayerSettings.setSettings(player);
			player.teleport(GetSpawnsParameters.LocationEventSpawn(main));
		}
	}

	// Message d'élimination lorsqu'un joueur se téléporte en dehors du monde des
	// événements.
	public static void EventElimationMessageOutWorld(Player player, Player playerOut, MiniEventsPlugin main) {
		player.sendMessage("§f[" + main.getGameName().getEventColoredPrefix().toUpperCase() + "§f] §c" + playerOut.getName()
				+ " §7n'est plus dans la zone de jeu !");

	}
	
	// Message d'élimination lorsqu'un joueur se déconnecte durant un état d'événement précis.
	public static void EventElimationMessageQuit(Player player, Player playerQuit, MiniEventsPlugin main) {
		player.sendMessage("§f[" + main.getGameName().getEventColoredPrefix().toUpperCase() + "§f] §c" + playerQuit.getName()
				+ " §7a quitté l'événement !");
	}
}
