package fr.fallenvaders.minecraft.mini_events.events.enclume;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import fr.fallenvaders.minecraft.mini_events.MiniEvents;
import fr.fallenvaders.minecraft.mini_events.events.GetEventWorld;
import fr.fallenvaders.minecraft.mini_events.events.enclume.tasks.EnclumeAnvilClear;
import fr.fallenvaders.minecraft.mini_events.events.enclume.tasks.EnclumeMiniWavesTimer;

public class EnclumeActions {

    public static void StartingWaves() {
        int waveNumber = 1;

        EnclumeMiniWavesTimer start = new EnclumeMiniWavesTimer(waveNumber);
        start.runTaskTimer(MiniEvents.PLUGIN, 0, 1);

        Integer groundY = EnclumeCalculs.getTheGround();
        EnclumeAnvilClear.ClearParameters(groundY);
    }

    public static void GenerateAnvil(List<Integer> blockPosX, List<Integer> blockPosZ, int waveNumber) {
        String pathToParameters = ("config-event.enclume.spawning-area.");
        int yPos = MiniEvents.PLUGIN.getConfig().getInt(pathToParameters + ".y");

        for (int i = 0; i < blockPosX.size(); i++) {
            World worldName = GetEventWorld.getWorld();
            Integer xPos = blockPosX.get(i);
            Integer zPos = blockPosZ.get(i);
            Location anvilLoc = new Location(worldName, xPos, yPos, zPos);
            anvilLoc.getBlock().setType(Material.ANVIL);
        }
    }

	/*public static void ClearAnvil(MiniEventsPlugin main, int lowestPointX, int lowestPointZ, int highestPointX, int highestPointZ) {

	}*/

    public static void EventElimationMessage(Player players, Player playerEliminated) {
        players.sendMessage("§f[" + MiniEvents.getGame().getGameName().getEventColoredPrefix().toUpperCase() + "§f] §c" + playerEliminated.getName()
            + " §7s'est fait écraser !");
    }

}
