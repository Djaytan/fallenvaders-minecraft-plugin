package fr.fallenvaders.minecraft.justice_hands.keyskeeper.listeners;

import fr.fallenvaders.minecraft.justice_hands.criminalrecords.objects.CJSanction;
import fr.fallenvaders.minecraft.justice_hands.keyskeeper.KeysKeeperBot;
import fr.fallenvaders.minecraft.justice_hands.keyskeeper.KeysKeeperComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginListener implements Listener {


    // On vérifie lors du processus de login, si le joueur est ban
    @EventHandler
    public void PlayerLoginEvent(PlayerLoginEvent ple) {
        try {
            CJSanction playerActiveBan = KeysKeeperBot.getPlayerActiveBan(ple.getPlayer());

            boolean isBanDef = false;
            if ("bandef".equals(playerActiveBan.getType())) {
                isBanDef = true;
            }

            if (isBanDef) {
                ple.disallow(PlayerLoginEvent.Result.KICK_BANNED, KeysKeeperComponent.loginBanDefMessage(playerActiveBan));
            } else {
                ple.disallow(PlayerLoginEvent.Result.KICK_BANNED, KeysKeeperComponent.loginBanMessage(playerActiveBan));
            }
        } catch (NullPointerException e) {
            //Si la fonction playerActiveBan retourne un null, le joueur n'a pas de ban actif
            return;
        };
    }
}
