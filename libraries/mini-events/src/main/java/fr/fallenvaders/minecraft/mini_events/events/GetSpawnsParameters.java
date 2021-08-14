package fr.fallenvaders.minecraft.mini_events.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import fr.fallenvaders.minecraft.mini_events.MiniEventsPlugin;

public class GetSpawnsParameters {

	// Permet de retourner le point de spawn initial de l'événement qui est
	// actuellement en cours:
	public static Location LocationEventSpawn(MiniEventsPlugin main) {
		String pathToParameters = "config-event." + main.getGameName().name().toLowerCase() + ".spawns.spawn-on-join";
		Double x = main.getConfig().getDouble(pathToParameters + ".x");
		Double y = main.getConfig().getDouble(pathToParameters + ".y");
		Double z = main.getConfig().getDouble(pathToParameters + ".z");
		Float yaw = (float) main.getConfig().getDouble(pathToParameters + ".yaw");
		Float pitch = (float) main.getConfig().getDouble(pathToParameters + ".pitch");

		Location loc = new Location(Bukkit.getWorld(GetEventWorld.getName(main)), x, y, z, yaw, pitch);
		return loc;
	}

	// Permet de retourner le point de spawn de l'arène de l'événement qui est
	// actuellement en cours:
	public static Location LocationSpawnArene(MiniEventsPlugin main) {
		String pathToParameters = "config-event." + main.getGameName().name().toLowerCase() + ".spawns.spawn-arene";
		Double x = main.getConfig().getDouble(pathToParameters + ".x");
		Double y = main.getConfig().getDouble(pathToParameters + ".y");
		Double z = main.getConfig().getDouble(pathToParameters + ".z");
		Float yaw = (float) main.getConfig().getDouble(pathToParameters + ".yaw");
		Float pitch = (float) main.getConfig().getDouble(pathToParameters + ".pitch");

		Location loc = new Location(Bukkit.getWorld(GetEventWorld.getName(main)), x, y, z, yaw, pitch);
		return loc;
	}

	// Cette méthode est utilisé pour se téléporter à un événement via le menu des événements.
	public static Location EventTeleportOnSpawn(MiniEventsPlugin main, String eventName) {

		String pathToParameters = "config-event." + eventName.toLowerCase() + ".spawns.spawn-on-join";
		Double x = main.getConfig().getDouble(pathToParameters + ".x");
		Double y = main.getConfig().getDouble(pathToParameters + ".y");
		Double z = main.getConfig().getDouble(pathToParameters + ".z");
		Float yaw = (float) main.getConfig().getDouble(pathToParameters + ".yaw");
		Float pitch = (float) main.getConfig().getDouble(pathToParameters + ".pitch");

		Location loc = new Location(Bukkit.getWorld(GetEventWorld.getName(main)), x, y, z, yaw, pitch);
		return loc;
	}
}
