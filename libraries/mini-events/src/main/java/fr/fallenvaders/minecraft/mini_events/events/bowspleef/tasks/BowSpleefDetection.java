package fr.fallenvaders.minecraft.mini_events.events.bowspleef.tasks;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fallenvaders.minecraft.mini_events.GameName;
import fr.fallenvaders.minecraft.mini_events.GameState;
import fr.fallenvaders.minecraft.mini_events.MiniEvents;
import fr.fallenvaders.minecraft.mini_events.events.GetEventWorld;
import fr.fallenvaders.minecraft.mini_events.events.GetSpawnsParameters;
import fr.fallenvaders.minecraft.mini_events.events.PlayerElimination;
import fr.fallenvaders.minecraft.mini_events.events.PlayerRemaining;
import fr.fallenvaders.minecraft.mini_events.events.WhoIsWinner;
import fr.fallenvaders.minecraft.mini_events.events.bowspleef.BowSpleefActions;

public class BowSpleefDetection extends BukkitRunnable {

    @Override
    public void run() {
        GameName name = MiniEvents.getGame().getGameName();
        GameState state = MiniEvents.getGame().getGameState();

        String eventPrefix = ("§f[" + MiniEvents.getGame().getGameName().getEventColoredPrefix().toUpperCase() + "§f] ");

        if (name == GameName.BOWSPLEEF && (state == GameState.PLAYING || state == GameState.FINISH)) {
            if (MiniEvents.getGame().getParticipants().size() > 0) {
                for (UUID playerUUID : MiniEvents.getGame().getParticipants()) {
                    Player player = Bukkit.getPlayer(playerUUID);

                    if (player.getWorld().getName().equalsIgnoreCase(GetEventWorld.getName())) {
                        if (player.isOnGround() && !player.hasPotionEffect(PotionEffectType.LEVITATION)) {
                            player.removePotionEffect(PotionEffectType.GLOWING);
                        }
                        if (player.getFallDistance() > 10) {
                            if (state == GameState.PLAYING) {
                                player.sendMessage(eventPrefix + "§7Tu as été éliminé, tu as chuté de très haut !");
                                PlayerElimination.EventEliminationTP(player);

                                // On envoit à tous les joueurs dans le monde d'événement le message
                                // d'élimination.
                                for (Player pls : Bukkit.getServer().getOnlinePlayers()) {
                                    if (pls.getWorld().getName().equalsIgnoreCase(GetEventWorld.getName())) {
                                        BowSpleefActions.EventElimationMessageFall(pls, player);
                                    }
                                }

                                // On supprime le joueur des participants et on regarde si il y a un gagnant.
                                MiniEvents.getGame().getParticipants().remove(player.getUniqueId());

                                PlayerRemaining.PlayerLeft();
                                WhoIsWinner.getWinner(MiniEvents.getGame().getParticipants());
                            } else if (state == GameState.FINISH) {
                                player.setFallDistance(0);
                                player.teleport(GetSpawnsParameters.LocationSpawnArene());
                            }
                        }
                    }
                }
            } else {
                cancel();
            }
        } else {
            // Si l'état du jeu n'est plus en "PLAYING", alors on éteint cette boucle de
            // détection.
            cancel();
        }
    }


}
