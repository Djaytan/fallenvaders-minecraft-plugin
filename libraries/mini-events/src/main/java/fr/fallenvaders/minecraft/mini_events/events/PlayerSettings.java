package fr.fallenvaders.minecraft.mini_events.events;

import java.util.Collection;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class PlayerSettings {
	
	// Permet de "réinitialiser" le joueur durant les différentes actions des événements.
	public static void setSettings(Player player) {
		player.setGameMode(GameMode.ADVENTURE);
		player.setFoodLevel(20);
		player.setHealth(20);
		player.setLevel(0);
		player.setInvulnerable(false);
		player.setFallDistance(0);
		player.getInventory().clear();
		
		Collection<PotionEffect> playerEffect = player.getActivePotionEffects();
		if(playerEffect.size() > 0) {
			for (PotionEffect potionEffect : playerEffect) {
				player.removePotionEffect(potionEffect.getType());
			}
		}
	}
}
