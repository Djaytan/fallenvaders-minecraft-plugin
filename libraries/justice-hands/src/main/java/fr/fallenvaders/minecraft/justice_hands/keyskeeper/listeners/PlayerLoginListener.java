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
    public void PlayerLoginEvent(PlayerLoginEvent e) {

        // On récupère la ban le plus long
        CJSanction playerLongestBan = KeysKeeperBot.getPlayerLongestBan(e.getPlayer());

        // Si la fonction précedence n'a trouvé aucun ban venant du joueur, on le laisse passer
        if (playerLongestBan == null) {
            e.allow();
        }
        // Si le joueur à un ban, on le kick
        else if (playerLongestBan.getTSExpireDate() == null) {
            e.disallow(PlayerLoginEvent.Result.KICK_BANNED, KeysKeeperComponent.loginBanDefMessage(playerLongestBan));
        }
        else if (playerLongestBan.getTSExpireDate().getTime() > System.currentTimeMillis()) {
            e.disallow(PlayerLoginEvent.Result.KICK_BANNED, KeysKeeperComponent.loginBanMessage(playerLongestBan));
        }
        else {
            e.allow();
        }
    }

}
