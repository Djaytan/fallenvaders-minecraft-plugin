package fr.fallenvaders.minecraft.mini_events.commands.args;


import org.bukkit.entity.Player;

import fr.fallenvaders.minecraft.mini_events.GameState;
import fr.fallenvaders.minecraft.mini_events.MiniEvents;
import fr.fallenvaders.minecraft.mini_events.events.OnStartEndOfEvent;

public class ArgSTOP {
    public static void ArgStop(Player player) {
        GameState state = MiniEvents.getGame().getGameState();

        if (!(state == GameState.NOTSTARTED)) {
            OnStartEndOfEvent.EventStop();
        } else {
            player.sendMessage(MiniEvents.PREFIX + "§cAucun événement est en cours.");
        }
    }
}
