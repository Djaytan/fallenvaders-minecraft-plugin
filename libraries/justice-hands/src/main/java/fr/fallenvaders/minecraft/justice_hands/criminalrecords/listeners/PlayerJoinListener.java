package fr.fallenvaders.minecraft.justice_hands.criminalrecords.listeners;

import fr.fallenvaders.minecraft.justice_hands.JusticeHands;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    /*
     * onJoin(), lorsque le joueur se connecte, on lui créer
     * un compte si il en a pas déja un.
     */

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        JusticeHands.getSqlPA().createAccount(player.getUniqueId());
    }
}
