package fr.fallenvaders.minecraft.mini_events.events;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.fallenvaders.minecraft.mini_events.GameName;
import fr.fallenvaders.minecraft.mini_events.GameState;
import fr.fallenvaders.minecraft.mini_events.MiniEvents;

public class OnStartEndOfEvent {

    // Cooldown de départ mais quand ce dernier commence.
    public static void CooldownStartOnStart(Player player) {
        MiniEvents.getGame().setGameState(GameState.STARTING);
        player.teleport(GetSpawnsParameters.LocationSpawnArene());
        PlayerSettings.setSettings(player);

        MiniEvents.getGame().getGameName().getSpecificItem(player); // Donne les objets de l'événement au joueur.
        player.setInvulnerable(MiniEvents.getGame().getGameName().getVulnerable()); // Donne l'invulnérabilité au joueur ou pas, selon les paramètres de l'événement.
    }

    // Cooldown de départ mais quand ce dernier se termine.
    public static void CooldownStartOnEnd(Player player) {
        // Met le mode de jeu associé à l'événement au joueur.
        player.setGameMode(MiniEvents.getGame().getGameName().getGamemode());
    }

    // Quand l'événement commence à se terminer normalement avec 1 joueur restant
    // (GameState.FINISH)
    public static void EventStateFinish() {
        UUID playerWhoWinUUID = MiniEvents.getGame().getParticipants().get(0);
        Player playerWhoWin = Bukkit.getPlayer(playerWhoWinUUID);
        String winner = playerWhoWin.getName();

        // Message pour tous les joueurs
        Bukkit.broadcastMessage("\n§f[§b" + MiniEvents.getGame().getGameName().getVisualName().toUpperCase()
            + "§f] §7Bravo à tous les participants de l'événement !");
        Bukkit.broadcastMessage("\n§f[§b" + MiniEvents.getGame().getGameName().getVisualName().toUpperCase()
            + "§f] §7Le grand vainqueur de cette événement n'est autre que le joueur §6§l" + winner + "§7 !\n ");

        playerWhoWin.setGameMode(GameMode.ADVENTURE);
        MiniEvents.getGame().setGameState(GameState.FINISH);

        CooldownOnEnd start = new CooldownOnEnd();
        start.runTaskTimer(MiniEvents.PLUGIN, 0, 20);
    }

    // Quand l'événement se termine réellement (R.A.Z.)
    // (Après l'état FINISH)
    public static void EventEndAndRAZ() {
        EventRAZ();
    }

    // Quand un membre du personnel décide de faire la commande d'arrêt forcé de
    // l'évenement.
    public static void EventStop() {
        Bukkit.broadcastMessage(MiniEvents.PREFIX + "§cL'événement §c§l" + MiniEvents.getGame().getGameName().getVisualName().toUpperCase()
            + "§r§c a été arrêté manuellement !");

        if (MiniEvents.getGame().getParticipants().size() >= 1) {
            for (UUID playerUUID : MiniEvents.getGame().getParticipants()) {
                Player player = Bukkit.getPlayer(playerUUID);

                if (player.getWorld().getName().equalsIgnoreCase(GetEventWorld.getName())) {
                    player.teleport(GetSpawnsParameters.LocationEventSpawn());
                    PlayerSettings.setSettings(player);
                }
                player.sendMessage(MiniEvents.PREFIX + "§7Téléportation de tous les joueurs !");
            }
        }
        EventRAZ();
    }

    // Permet de lancer la régénération d'un événement.
    private static void EventRegeneration() {
        // On vérifie si la liste contient une valeur.
        if (MiniEvents.getGame().getBlockLoc().size() > 0) {

            // Régénération du SPLEEF:
            if (MiniEvents.getGame().getGameName() == GameName.SPLEEF) {
                for (Location blockLocation : MiniEvents.getGame().getBlockLoc()) {
                    blockLocation.getBlock().setType(Material.SNOW_BLOCK);
                }
            }
            // Régénération du BOWSPLEEF:
            if (MiniEvents.getGame().getGameName() == GameName.BOWSPLEEF) {
                for (Location blockLocation : MiniEvents.getGame().getBlockLoc()) {
                    blockLocation.getBlock().setType(Material.TNT);
                }
            }

            // On vide la liste pour les prochains événements.
            MiniEvents.getGame().getBlockLoc().clear();

        } else {
            return;
        }
    }

    // Permet de remettre à zéro un événement.
    private static void EventRAZ() {
        EventRegeneration(); // Régénération de la map

        MiniEvents.getGame().setGameState(GameState.NOTSTARTED); // Le status de jeu change en "NOTSTARTED", l'event se termine
        MiniEvents.getGame().setGameName(GameName.NONE); // Le jeu passe en "NONE" pour dire que aucun jeu n'a démarré.
        MiniEvents.getGame().getParticipants().clear(); // On vide la liste des participants.
        MiniEvents.getGame().getLeaveDuringEvent().clear(); // De même pour ceux qui ont quitté en cours de route.

    }
}
