package fr.fallenvaders.minecraft.mini_events.commands.args;

import fr.fallenvaders.minecraft.mini_events.GameName;
import org.bukkit.entity.Player;

public class ArgNAME {
  ///////////////////////////////////////////////////////
  // Permet de savoir le nom l'événement en cours.
  // Pratique pour lors de bugs ou de dysfonctionnement.
  ///////////////////////////////////////////////////////
  public static void ArgName(Player player, String pluginPrefix, GameName gameName) {

    if (!(gameName == GameName.NONE)) {
      player.sendMessage(
          pluginPrefix + "§7L'événement §b" + gameName.name().toUpperCase() + " §7est en cours");
    } else {
      player.sendMessage(pluginPrefix + "§7Il y a aucun événement en cours.");
    }
  }
}
