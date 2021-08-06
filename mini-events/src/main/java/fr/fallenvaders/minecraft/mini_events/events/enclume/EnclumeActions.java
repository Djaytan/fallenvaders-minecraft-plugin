package fr.fallenvaders.minecraft.mini_events.events.enclume;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import fr.fallenvaders.minecraft.mini_events.MiniEventsPlugin;
import fr.fallenvaders.minecraft.mini_events.events.GetEventWorld;
import fr.fallenvaders.minecraft.mini_events.events.enclume.tasks.EnclumeAnvilClear;
import fr.fallenvaders.minecraft.mini_events.events.enclume.tasks.EnclumeMiniWavesTimer;

public class EnclumeActions {

	public static void StartingWaves(MiniEventsPlugin main) {
		int waveNumber = 1;

		EnclumeMiniWavesTimer start = new EnclumeMiniWavesTimer(main, waveNumber);
		start.runTaskTimer(main, 0, 1);

		Integer groundY = EnclumeCalculs.getTheGround(main);
		EnclumeAnvilClear.ClearParameters(main, groundY);
	}

	public static void GenerateAnvil(MiniEventsPlugin main, List<Integer> blockPosX, List<Integer> blockPosZ, int waveNumber) {
		String pathToParameters = ("config-event.enclume.spawning-area.");
		int yPos = main.getConfig().getInt(pathToParameters + ".y");

		for (int i = 0; i < blockPosX.size(); i++) {
			World worldName = GetEventWorld.getWorld(main);
			Integer xPos = blockPosX.get(i);
			Integer zPos = blockPosZ.get(i);
			Location anvilLoc = new Location(worldName, xPos, yPos, zPos);
			anvilLoc.getBlock().setType(Material.ANVIL);
		}
	}

	/*public static void ClearAnvil(MiniEventsPlugin main, int lowestPointX, int lowestPointZ, int highestPointX, int highestPointZ) {

	}*/

	public static void EventElimationMessage(Player players, Player playerEliminated, MiniEventsPlugin main) {
		players.sendMessage("§f[" + main.getGameName().getEventColoredPrefix().toUpperCase() + "§f] §c" + playerEliminated.getName()
				+ " §7s'est fait écraser !");
	}

}
