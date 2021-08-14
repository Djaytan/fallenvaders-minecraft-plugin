package fr.fallenvaders.minecraft.justicehands.keyskeeper.listeners;

import fr.fallenvaders.minecraft.justicehands.GeneralUtils;
import fr.fallenvaders.minecraft.justicehands.keyskeeper.KeysKeeperBot;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Date;

public class AsyncChatListener implements Listener {
	
	@EventHandler
	public void AsyncChatEvent(AsyncChatEvent e) {
		Long unmuteDate = KeysKeeperBot.getPlayerMuteDate(e.getPlayer());

		// Vérification si le joueur peut parler
		if (unmuteDate>System.currentTimeMillis()) {
			e.getPlayer().sendMessage(GeneralUtils.getPrefix("kk") + "§cTu ne peux pas parler, tu es réduit au silence pendant encore §b" + GeneralUtils.timeRemaining(unmuteDate-System.currentTimeMillis()) + "§c.");
			e.setCancelled(true);
		} else {
			e.setCancelled(false);
		}
	}
}
