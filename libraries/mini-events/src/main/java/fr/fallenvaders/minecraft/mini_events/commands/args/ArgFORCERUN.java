package fr.fallenvaders.minecraft.mini_events.commands.args;

import org.bukkit.entity.Player;

import fr.fallenvaders.minecraft.mini_events.GameState;
import fr.fallenvaders.minecraft.mini_events.MiniEvents;
import fr.fallenvaders.minecraft.mini_events.events.CooldownOnStart;

public class ArgFORCERUN {

    public static void ArgForceRun(Player player) {
        GameState state = MiniEvents.getGame().getGameState();
        String prefix = MiniEvents.PREFIX;

        if (state == GameState.STARTING || state == GameState.PLAYING || state == GameState.FINISH) {
            player.sendMessage(prefix + "§cUn événement est déjà en cours, impossible d'en lancer un autre.");
        } else if (state == GameState.NOTSTARTED) {
            player.sendMessage(prefix
                + "§cAucun événement en cours, fait la commande §b/event start <§7event§b>§c pour lancer un événement.");
        } else if (state == GameState.WAITING) {
            if (MiniEvents.getGame().getParticipants().size() >= 1) {
                MiniEvents.getGame().setGameState(GameState.STARTING);

                CooldownOnStart start = new CooldownOnStart();
                start.runTaskTimer(MiniEvents.PLUGIN, 0, 20);
            } else {
                if (MiniEvents.getGame().getParticipants().size() == 0) {
                    player.sendMessage(prefix + "§cPour forcer le début d'une partie, il faut minimum 1 joueur.");
                }
            }
        }

    }

}
