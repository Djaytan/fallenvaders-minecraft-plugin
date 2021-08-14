package fr.fallenvaders.minecraft.mini_events.events;

import org.bukkit.entity.Player;

import fr.fallenvaders.minecraft.mini_events.MiniEvents;

public class PlayerElimination {

    // Téléporte le joueur au spawn de l'événement et le "réinitialise".
    public static void EventEliminationTP(Player player) {
        if (player.getWorld().getName().equalsIgnoreCase(GetEventWorld.getName())) {
            PlayerSettings.setSettings(player);
            player.teleport(GetSpawnsParameters.LocationEventSpawn());
        }
    }

    // Message d'élimination lorsqu'un joueur se téléporte en dehors du monde des
    // événements.
    public static void EventElimationMessageOutWorld(Player player, Player playerOut) {
        player.sendMessage("§f[" + MiniEvents.getGame().getGameName().getEventColoredPrefix().toUpperCase() + "§f] §c" + playerOut.getName()
            + " §7n'est plus dans la zone de jeu !");

    }

    // Message d'élimination lorsqu'un joueur se déconnecte durant un état d'événement précis.
    public static void EventElimationMessageQuit(Player player, Player playerQuit) {
        player.sendMessage("§f[" + MiniEvents.getGame().getGameName().getEventColoredPrefix().toUpperCase() + "§f] §c" + playerQuit.getName()
            + " §7a quitté l'événement !");
    }
}
