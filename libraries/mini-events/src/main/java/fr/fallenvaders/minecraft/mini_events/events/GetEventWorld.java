package fr.fallenvaders.minecraft.mini_events.events;

import fr.fallenvaders.minecraft.mini_events.MiniEvents;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class GetEventWorld {

  // Retourne le nom du monde initialement indiqué dans la config du plugin :
  public static String getName() {
    String eventWorldName =
        MiniEvents.PLUGIN.getConfig().getString("config-event.event-world.name");
    return eventWorldName;
  }

  // Retourne le Monde événement, via le nom indiqué dans la config.
  public static World getWorld() {
    World eventWorld = Bukkit.getWorld(getName());
    return eventWorld;
  }
}
