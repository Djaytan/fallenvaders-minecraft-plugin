package fr.fallenvaders.minecraft.mini_events.events.enclume;

import java.util.ArrayList;
import java.util.List;

import fr.fallenvaders.minecraft.mini_events.MiniEventsPlugin;

public class EnclumeGeneration {

	// Nombre d'enclume = (pourcentage * numero de vague * surface de spawn)
	public static void GenerateAnvilLocation(MiniEventsPlugin main, int waveNumber) {
		String pathToParameters = ("config-event.enclume.spawning-area.");

		// Premier point de la zone où les enclumes vont spawnent
		int x1 = main.getConfig().getInt(pathToParameters + "first-point.x");
		int z1 = main.getConfig().getInt(pathToParameters + "first-point.z");
		// Deuxieme point de la zone où les enclumes vont spawnent
		int x2 = main.getConfig().getInt(pathToParameters + "second-point.x");
		int z2 = main.getConfig().getInt(pathToParameters + "second-point.z");

		double spawningPourcentage = (waveNumber * 0.02);
		if (spawningPourcentage > 1.0) {
			spawningPourcentage = 1.0;
		}

		int lowestPointX = EnclumeCalculs.getLowestPointX(x1, x2);
		int lowestPointZ = EnclumeCalculs.getLowestPointZ(z1, z2);

		int highestPointX = EnclumeCalculs.getHighestPointX(x1, x2);
		int highestPointZ = EnclumeCalculs.getHighestPointZ(z1, z2);

		List<Integer> blockPosX = new ArrayList<>();
		List<Integer> blockPosZ = new ArrayList<>();

		int x = lowestPointX;
		int z = lowestPointZ;

		while (x <= highestPointX) {
			while (z <= highestPointZ) {
				double alea = Math.random();
				if (alea <= spawningPourcentage) {
					blockPosX.add(x);
					blockPosZ.add(z);
				}
				z++;
			}
			z = lowestPointZ;
			x++;
		}

		EnclumeActions.GenerateAnvil(main, blockPosX, blockPosZ, waveNumber);
	}
}
