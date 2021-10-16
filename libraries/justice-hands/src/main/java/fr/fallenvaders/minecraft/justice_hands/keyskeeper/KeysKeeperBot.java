package fr.fallenvaders.minecraft.justice_hands.keyskeeper;

import fr.fallenvaders.minecraft.justice_hands.JusticeHands;
import fr.fallenvaders.minecraft.justice_hands.criminalrecords.objects.CJSanction;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class KeysKeeperBot {

  /*
   * TODO A chaque fois que le joueur parle ça fait une requete dans la base de données,
   * il va falloir que je mette en place un systeme de cache qui s'actualise toutes les 5 minutes
   * Si jamais il y a une sanction de mute il va falloir que je mette à jour ce dernier
   */

  @NotNull
  public static Long getPlayerMuteDate(Player player) {
    List<Long> muteDateTSList = JusticeHands.getSqlKK().getPlayerMutesEDLong(player);
    try {
      return muteDateTSList.stream().max(Long::compare).get();
    } catch (NoSuchElementException e) {
      return 0l;
    }
  }

  @Nullable
  public static CJSanction getPlayerActiveBan(Player player) {
    List<CJSanction> playerBansList =
        JusticeHands.getSqlKK().getPlayerBans(player); // Récupération de tous les bans du joueurs
    System.out.println("Nombre de bans : " + playerBansList.size());

    List<Long> unbanDateTSList =
        new ArrayList(); // Liste des "expirationTimes" de tous les bans actif du joueur

    for (CJSanction sanction : playerBansList) {

      // Bandef enregistré = le plus récent car l'ordre de recherche dans la DB a été fait en DESC
      if (sanction.getTSExpireDate() == null) {
        return sanction;
        // Enregistrement du timestamp en long dans une liste
      } else {
        unbanDateTSList.add(sanction.getTSExpireDate().getTime());
      }
    }

    if (unbanDateTSList.size() > 0) {
      Long unbanDate =
          unbanDateTSList.stream()
              .max(Long::compare)
              .get(); // Timestamp le plus éloigné de la date actuelle
      // On retrouve la sanction avec ce timestamp le plus éloigné et on return la sanction
      for (CJSanction sanction : playerBansList) {
        CJSanction
            primarySanction; // La sanction primaire est celle qui interdit le joueur de rejoindre
        // le serveur
        if (sanction.getTSExpireDate().getTime() == unbanDate) {
          if (sanction.getTSExpireDate().getTime()
              > System.currentTimeMillis()) { // Vérifie si la sanction est toujours active
            primarySanction = sanction;
            return primarySanction;
          } else {
            return null;
          }
        }
      }
    }

    return null;
  }

  public static void kickPlayer(Player player, CJSanction sanction) {
    if (!"mute".equals(sanction.getType())) {
      player.kick(KeysKeeperComponent.ejectingMessageCpnt(sanction));
    }
  }
}