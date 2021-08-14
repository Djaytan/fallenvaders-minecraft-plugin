package fr.fallenvaders.minecraft.mini_events.events.enclume.tasks;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fallenvaders.minecraft.mini_events.GameName;
import fr.fallenvaders.minecraft.mini_events.GameState;
import fr.fallenvaders.minecraft.mini_events.MiniEventsPlugin;
import fr.fallenvaders.minecraft.mini_events.events.enclume.EnclumeGeneration;

public class EnclumeMiniWavesTimer extends BukkitRunnable {

	private MiniEventsPlugin main;
	private Integer waveNumber;
	private int timer = 20;
	private int totalMiniWaves = 2;

	public EnclumeMiniWavesTimer(MiniEventsPlugin main, int waveNumber) {
		this.main = main;
		this.waveNumber = waveNumber;
	}

	@Override
	public void run() {
		GameName name = main.getGameName();
		GameState state = main.getGameState();
		if (name == GameName.ENCLUME && state == GameState.PLAYING) {
			if (totalMiniWaves == 2 && timer == 20) {
				String eventPrefix = ("§f[" + main.getGameName().getEventColoredPrefix().toUpperCase() + "§f] ");
				for (UUID playerUUID : main.getParticipants()) {
					Player player = Bukkit.getPlayer(playerUUID);
					player.sendMessage(eventPrefix + "§7La vague d'enclumes §b§ln°" + waveNumber + " §7arrive !!");
				}
			}
			
			if (totalMiniWaves > 0) {
				if (timer < 20) {
					timer++;
				} else {
					EnclumeGeneration.GenerateAnvilLocation(main, waveNumber);
					timer = 0;
					totalMiniWaves--;
				}
			} else {
				EnclumeTimer startTimer = new EnclumeTimer(main, waveNumber);
				startTimer.runTaskTimer(main, 0, 2);
				cancel();
			}
		} else {
			cancel();
		}
	}

}
