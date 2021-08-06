package fr.fallenvaders.minecraft.mini_events.commands.args;


import org.bukkit.entity.Player;

import fr.fallenvaders.minecraft.mini_events.GameState;
import fr.fallenvaders.minecraft.mini_events.MiniEventsPlugin;
import fr.fallenvaders.minecraft.mini_events.events.OnStartEndOfEvent;

public class ArgSTOP {
	public static void ArgStop(Player player, MiniEventsPlugin main) {
		GameState state = main.getGameState();
		
		if (!(state == GameState.NOTSTARTED)) {
			OnStartEndOfEvent.EventStop(main);
		}
		else {
			player.sendMessage(main.prefix + "§cAucun événement est en cours.");
		}
	}
}
