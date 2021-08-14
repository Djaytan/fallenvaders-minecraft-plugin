package fr.fallenvaders.minecraft.mini_events.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import fr.fallenvaders.minecraft.mini_events.MiniEvents;
import org.bukkit.plugin.java.JavaPlugin;

public class GetSpawnsParameters {

    // Permet de retourner le point de spawn initial de l'événement qui est
    // actuellement en cours:
    public static Location LocationEventSpawn() {
        String pathToParameters = "config-event." + MiniEvents.getGame().getGameName().name().toLowerCase() + ".spawns.spawn-on-join";
        Double x = MiniEvents.PLUGIN.getConfig().getDouble(pathToParameters + ".x");
        Double y = MiniEvents.PLUGIN.getConfig().getDouble(pathToParameters + ".y");
        Double z = MiniEvents.PLUGIN.getConfig().getDouble(pathToParameters + ".z");
        Float yaw = (float) MiniEvents.PLUGIN.getConfig().getDouble(pathToParameters + ".yaw");
        Float pitch = (float) MiniEvents.PLUGIN.getConfig().getDouble(pathToParameters + ".pitch");

        Location loc = new Location(Bukkit.getWorld(GetEventWorld.getName()), x, y, z, yaw, pitch);
        return loc;
    }

    // Permet de retourner le point de spawn de l'arène de l'événement qui est
    // actuellement en cours:
    public static Location LocationSpawnArene() {
        String pathToParameters = "config-event." + MiniEvents.getGame().getGameName().name().toLowerCase() + ".spawns.spawn-arene";
        Double x = MiniEvents.PLUGIN.getConfig().getDouble(pathToParameters + ".x");
        Double y = MiniEvents.PLUGIN.getConfig().getDouble(pathToParameters + ".y");
        Double z = MiniEvents.PLUGIN.getConfig().getDouble(pathToParameters + ".z");
        Float yaw = (float) MiniEvents.PLUGIN.getConfig().getDouble(pathToParameters + ".yaw");
        Float pitch = (float) MiniEvents.PLUGIN.getConfig().getDouble(pathToParameters + ".pitch");

        Location loc = new Location(Bukkit.getWorld(GetEventWorld.getName()), x, y, z, yaw, pitch);
        return loc;
    }

    // Cette méthode est utilisé pour se téléporter à un événement via le menu des événements.
    public static Location EventTeleportOnSpawn(String eventName) {

        String pathToParameters = "config-event." + eventName.toLowerCase() + ".spawns.spawn-on-join";
        Double x = MiniEvents.PLUGIN.getConfig().getDouble(pathToParameters + ".x");
        Double y = MiniEvents.PLUGIN.getConfig().getDouble(pathToParameters + ".y");
        Double z = MiniEvents.PLUGIN.getConfig().getDouble(pathToParameters + ".z");
        Float yaw = (float) MiniEvents.PLUGIN.getConfig().getDouble(pathToParameters + ".yaw");
        Float pitch = (float) MiniEvents.PLUGIN.getConfig().getDouble(pathToParameters + ".pitch");

        Location loc = new Location(Bukkit.getWorld(GetEventWorld.getName()), x, y, z, yaw, pitch);
        return loc;
    }
}
