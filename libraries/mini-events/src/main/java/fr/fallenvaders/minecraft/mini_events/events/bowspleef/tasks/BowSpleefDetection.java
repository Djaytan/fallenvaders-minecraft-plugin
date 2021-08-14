package fr.fallenvaders.minecraft.mini_events.events.bowspleef.tasks;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fallenvaders.minecraft.mini_events.GameName;
import fr.fallenvaders.minecraft.mini_events.GameState;
import fr.fallenvaders.minecraft.mini_events.MiniEventsPlugin;
import fr.fallenvaders.minecraft.mini_events.events.GetEventWorld;
import fr.fallenvaders.minecraft.mini_events.events.GetSpawnsParameters;
import fr.fallenvaders.minecraft.mini_events.events.PlayerElimination;
import fr.fallenvaders.minecraft.mini_events.events.PlayerRemaining;
import fr.fallenvaders.minecraft.mini_events.events.WhoIsWinner;
import fr.fallenvaders.minecraft.mini_events.events.bowspleef.BowSpleefActions;

public class BowSpleefDetection extends BukkitRunnable{

	private MiniEventsPlugin main;
	
	public BowSpleefDetection(MiniEventsPlugin main) {
		this.main = main;
	}

	@Override
	public void run() {
		GameName name = main.getGameName();
		GameState state = main.getGameState();

		String eventPrefix = ("§f[" + main.getGameName().getEventColoredPrefix().toUpperCase() + "§f] ");

		if (name == GameName.BOWSPLEEF && (state == GameState.PLAYING || state == GameState.FINISH)) {
			if (main.getParticipants().size()>0) {
				for (UUID playerUUID : main.getParticipants()) {
					Player player = Bukkit.getPlayer(playerUUID);
	
					if (player.getWorld().getName().equalsIgnoreCase(GetEventWorld.getName(main))) {
						if (player.isOnGround() && !player.hasPotionEffect(PotionEffectType.LEVITATION)) {
							player.removePotionEffect(PotionEffectType.GLOWING);
						}
						if (player.getFallDistance() > 10) {
							if (state == GameState.PLAYING) {
								player.sendMessage(eventPrefix + "§7Tu as été éliminé, tu as chuté de très haut !");
								PlayerElimination.EventEliminationTP(player, main);
								
								// On envoit à tous les joueurs dans le monde d'événement le message
								// d'élimination.
								for (Player pls : Bukkit.getServer().getOnlinePlayers()) {
									if (pls.getWorld().getName().equalsIgnoreCase(GetEventWorld.getName(main))) {
										BowSpleefActions.EventElimationMessageFall(pls, player, main);
									}
								}
								
								// On supprime le joueur des participants et on regarde si il y a un gagnant.
								main.getParticipants().remove(player.getUniqueId());
								
								PlayerRemaining.PlayerLeft(main);
								WhoIsWinner.getWinner(main.getParticipants(), main);
							}
							else if (state == GameState.FINISH) {
								player.setFallDistance(0);
								player.teleport(GetSpawnsParameters.LocationSpawnArene(main));
							}
						}
					}
				}
			}
			else {
				cancel();
			}
		}
		else {
			// Si l'état du jeu n'est plus en "PLAYING", alors on éteint cette boucle de
			// détection.
			cancel();
		}
	}


}
