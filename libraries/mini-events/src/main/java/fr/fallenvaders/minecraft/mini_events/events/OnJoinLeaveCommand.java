package fr.fallenvaders.minecraft.mini_events.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.fallenvaders.minecraft.mini_events.MiniEvents;

public class OnJoinLeaveCommand {

    // Lorsqu'un joueur effectue la commande permettant de rejoindre un événement:
    public static void EventJoin(Player player) {
        player.teleport(GetSpawnsParameters.LocationEventSpawn());
        PlayerSettings.setSettings(player);
        for (Player playerS : Bukkit.getOnlinePlayers()) {
            //playerS.playSound(playerS.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 0);
        }
    }

    // Lorsqu'un joueur effectue la commande permettant de quitter un événement:
    public static void EventLeave(Player player) {
        if (player.getWorld().getName().equalsIgnoreCase(GetEventWorld.getName())) {
            player.teleport(GetSpawnsParameters.LocationEventSpawn());
            PlayerSettings.setSettings(player);
            for (Player playerS : Bukkit.getOnlinePlayers()) {
                //playerS.playSound(playerS.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 2);
            }
        }
    }
}
