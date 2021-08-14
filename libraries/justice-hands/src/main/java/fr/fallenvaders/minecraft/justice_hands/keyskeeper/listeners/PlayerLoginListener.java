package fr.fallenvaders.minecraft.justice_hands.keyskeeper.listeners;

import fr.fallenvaders.minecraft.justicehands.GeneralUtils;
import fr.fallenvaders.minecraft.justicehands.JusticeHandsPlugin;
import fr.fallenvaders.minecraft.justicehands.criminalrecords.objects.CJSanction;
import fr.fallenvaders.minecraft.justice_hands.keyskeeper.KeysKeeperBot;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.List;

public class PlayerLoginListener implements Listener {


    // On vérifie lors du processus de login, si le joueur est ban
    @EventHandler
    public void PlayerLoginEvent(PlayerLoginEvent e){

        // On récupère la ban le plus long
        CJSanction playerLongestBan = KeysKeeperBot.getPlayerLongestBan(e.getPlayer());

        // Si la fonction précedence n'a trouvé aucun ban venant du joueur, on le laisse passer
        if (playerLongestBan == null) {
            e.allow();
        }
        // Si le joueur à un ban définif, on le kick
        else if (playerLongestBan.getTSExpireDate() == null){
            System.out.println("Ban définif detecté : " + playerLongestBan.getID());

        }
        // Si l'expiration time de la sanction est plus loin que celui actuel, on kick le joueur
        else if (playerLongestBan.getTSExpireDate().getTime() > System.currentTimeMillis()) {
            System.out.println("Ban detecté : " + playerLongestBan.getID());

        }
        else {
            e.allow();
        }



        /*
        if (unbanDate == -1l) {
            e.setResult(PlayerLoginEvent.Result.KICK_BANNED);
        } else if (unbanDate>System.currentTimeMillis()) {
            e.disallow(e.setResult(PlayerLoginEvent.Result.KICK_BANNED),"test");
            e.setKickMessage(GeneralUtils.getPrefix("kk") + "§cTu ne peux pas rejoindre le serveur, tu es banni pour encore : §b" + GeneralUtils.timeRemaining(unbanDate-System.currentTimeMillis()) + "§c.");
        } else {
            e.allow();
        }*/
    }

}
