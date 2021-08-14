package fr.fallenvaders.minecraft.mini_events.listeners;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.fallenvaders.minecraft.mini_events.GameName;
import fr.fallenvaders.minecraft.mini_events.GameState;
import fr.fallenvaders.minecraft.mini_events.MiniEvents;
import fr.fallenvaders.minecraft.mini_events.events.GetEventWorld;
import fr.fallenvaders.minecraft.mini_events.events.PlayerElimination;
import fr.fallenvaders.minecraft.mini_events.events.PlayerSettings;

public class PlayerJoinListener implements Listener {

    ////////////////////////////////////////////////////
    // Lorsqu'un joueur se connecte on vérifie si il
    // est dans la liste de ceux qui ont quitté
    // l'événement en cours de jeu.
    //
    // Selon l'état du jeu on le rajoute dans le jeu ou
    // pas.
    ////////////////////////////////////////////////////

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        List<UUID> leaveDuringEvent = MiniEvents.getGame().getLeaveDuringEvent();
        List<UUID> participants = MiniEvents.getGame().getParticipants();
        GameName name = MiniEvents.getGame().getGameName();
        GameState state = MiniEvents.getGame().getGameState();

        Player player = event.getPlayer();

        if ((!(name == GameName.NONE)) && leaveDuringEvent.contains(player.getUniqueId())) {

            // Le joueur se reconnecte quand l'événement est en STARTING, PLAYING ou en
            // FINISH:
            if (state == GameState.STARTING || state == GameState.PLAYING || state == GameState.FINISH) {
                PlayerElimination.EventEliminationTP(player);
                player.sendMessage(MiniEvents.PREFIX
                    + "§7Désolé mais l'événement est en cours, tu as été téléporté au spawn de l'événement.");

                // On le supprime de la liste des déconnectés, car il s'est reconnecté et a
                // subit les actions nécéssaires.
                leaveDuringEvent.remove(player.getUniqueId());
            }

            // Le joueur se reconnecte quand l'événement est en WAITING:
            if (state == GameState.WAITING) {
                String eventName = MiniEvents.getGame().getGameName().getRealName().toLowerCase();
                Integer maxPlayers = MiniEvents.PLUGIN.getConfig().getInt("config-event." + eventName + ".players.max-players");

                // Si il reste encore une place dans la liste d'attente, alors on le rajoute à
                // cette dernière.
                if (participants.size() < maxPlayers) {
                    participants.add(player.getUniqueId()); // On le rajoute dans la liste des participants.
                    leaveDuringEvent.remove(player.getUniqueId()); // On le retire de la liste de ceux qui ont quitté.

                    // Broadcast d'annonce, annonçant que le joueur a rejoint l'événement.
                    Bukkit.broadcastMessage(player.getName() + "§7 s'est reconnecté §7(§a"
                        + MiniEvents.getGame().getParticipants().size() + "§7/§a" + maxPlayers + "§7).");
                }

                // Sinon, on lui dit simplement que la liste d'attente est devenue pleine.
                else {
                    PlayerElimination.EventEliminationTP(player);
                    player.sendMessage(MiniEvents.PREFIX
                        + "§7Désolé mais lors de ta déconnexion, la liste d'attente est devenue pleine. Tu as été téléporté au spawn de l'événement.");
                    // On le supprime de la liste des déconnectés, car il s'est reconnecté et a
                    // subit les actions nécéssaires.
                    leaveDuringEvent.remove(player.getUniqueId());
                }
            }
        }
        // Si le joueur s'est déconnecté dans le monde d'événement et que pendant ce
        // temps
        // l'événement s'est terminé et qu'il ne se trouve plus dans aucune liste
        if (name == GameName.NONE && player.getWorld().getName().equalsIgnoreCase(GetEventWorld.getName())
            && (!leaveDuringEvent.contains(player.getUniqueId())) && (!player.isOp())) {
            Location loc = new Location(Bukkit.getWorld(GetEventWorld.getName()), 0.5, 95.6, -0.5, 0f, 0f);
            player.teleport(loc);
            PlayerSettings.setSettings(player);
        }

    }
}
