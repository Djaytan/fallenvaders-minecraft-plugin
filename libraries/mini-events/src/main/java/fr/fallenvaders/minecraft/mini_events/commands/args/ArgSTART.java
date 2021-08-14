package fr.fallenvaders.minecraft.mini_events.commands.args;

import org.bukkit.entity.Player;

import fr.fallenvaders.minecraft.mini_events.GameName;
import fr.fallenvaders.minecraft.mini_events.GameState;
import fr.fallenvaders.minecraft.mini_events.MiniEvents;
import fr.fallenvaders.minecraft.mini_events.events.BroadcastEventAnnouncement;

public class ArgSTART {

    public static void ArgStart(Player player, GameName eventName) {
        GameState state = MiniEvents.getGame().getGameState();
        String prefix = MiniEvents.PREFIX;

        if (state == GameState.STARTING || state == GameState.PLAYING || state == GameState.FINISH
            || state == GameState.WAITING) {
            player.sendMessage(prefix + "§cUn événement est déjà en cours, impossible d'en lancer un autre.");
        }

        if (state == GameState.NOTSTARTED) {
            MiniEvents.getGame().setGameState(GameState.WAITING); // On met l'évenement en WAITING (attente de joueurs).
            MiniEvents.getGame().setGameName(eventName); // On dit que l'événement sera celui qu'on a écrit dans le commande.

            BroadcastEventAnnouncement.BroadcastMessage(MiniEvents.getGame().getGameName().getVisualName().toUpperCase()); // Message d'annonce dans le chat.
        }
    }
}
