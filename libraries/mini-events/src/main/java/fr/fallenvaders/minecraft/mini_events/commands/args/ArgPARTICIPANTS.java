package fr.fallenvaders.minecraft.mini_events.commands.args;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.fallenvaders.minecraft.mini_events.GameState;
import fr.fallenvaders.minecraft.mini_events.MiniEventsPlugin;

public class ArgPARTICIPANTS {
	public static void ArgParticipants(MiniEventsPlugin main, Player player) {
		if (!(main.getGameState() == GameState.NOTSTARTED)) {
			if (main.getParticipants().size() >= 1) {

				String participantsList = "";
				for (int i = 0; i < main.getParticipants().size(); i++) {
					Player participant = Bukkit.getPlayer(main.getParticipants().get(i));
					if (i == 0) {
						participantsList = ("§f" + participant.getName());
					} else {
						participantsList = (participantsList + "§7, §f" + participant.getName());
					}
				}
				player.sendMessage(main.prefix + "§7Liste des participants : " + participantsList);
			} else {
				player.sendMessage(main.prefix + "§cLa liste des participants est vide.");
			}
		} else {
			player.sendMessage(main.prefix + "§cAucun événement est en cours, aucune liste de participants.");
		}
	}
}
