package fr.fallenvaders.minecraft.justice_hands.sanctionmanager;

import java.sql.Timestamp;

import fr.fallenvaders.minecraft.justice_hands.GeneralUtils;
import fr.fallenvaders.minecraft.justice_hands.JusticeHands;
import fr.fallenvaders.minecraft.justice_hands.sanctionmanager.objects.Sanction;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SanctionsAlgo {

    /*
     * Echelle de sanctions: Points <-> Temps :
     *
     * Mute (en minutes) = points*1
     * Ban (en jours) = points/10
     *
     * Cette algorithme va gérer la création de la sanction par rapport aux
     * ancienne sanction du joueur mais aussi par rapport à son nombre de points actuel
     *
     * TODO, il va falloire créer le systeme qui permet de perdre 1 points tous les jours (par exemple), le
     * joueur perdra des points succéssivement chaque jours si il est sage et qu'il ne fait
     * aucune violation du réglèment. (Permet de se racheter)
     */

    public static void generateSanction(Sanction sanction, Player moderator, Player target) {

        // Récupération de la configuration
        FileConfiguration config = JusticeHands.PLUGIN.getConfig();
        String pointConfigurationPath = "justicehands.sanctionmanager.points-system";
        int muteMinPerPts = config.getInt(pointConfigurationPath + ".mute-min-points");
        int banDayPerPts = config.getInt(pointConfigurationPath + ".ban-day-points");
        int risingbanLimit = config.getInt(pointConfigurationPath + ".risingban-points");

        //Lors de l'algorithme, les points et le types de sanction peuvent être modifiés.
        //On va donc créer une nouvelle sanction identique à celle en paramètre.
        Sanction tempSanction = (Sanction) sanction.clone();
        final int targetPoints = JusticeHands.getSqlPA().getPoints(target.getUniqueId());

        // Permet de dire si un joueur mérite un ban au lieu d'un mute ou d'un kick
        if (targetPoints >= risingbanLimit && !(tempSanction.getInitialType().equals("ban") || tempSanction.getInitialType().equals("bandef"))) {
            tempSanction.setInitialType("risingban");
            tempSanction.setPoints(tempSanction.getPoints() * 2); //Multiplication des points par deux
            generateBanMute(tempSanction, moderator, target, muteMinPerPts, banDayPerPts);
            return;

            // Le type de sanction est un type sans date d'expiration
        } else if (tempSanction.getInitialType().equals("kick") || tempSanction.getInitialType().equals("bandef")) {
            JusticeHands.getSqlSM().addSanction(target, moderator, tempSanction, null);
            sendAlertMsg(target, tempSanction, GeneralUtils.getPrefix("SM"), System.currentTimeMillis(), 0);
            return;

        } else if (tempSanction.getInitialType().equals("mute") || tempSanction.getInitialType().equals("ban")) {
            generateBanMute(tempSanction, moderator, target, muteMinPerPts, banDayPerPts);
            return;

        } else {
            moderator.sendMessage(GeneralUtils.getPrefix("SM") + "§cUne erreur vient de se produire, l'action n'a pas aboutie. Veuillez contacter un administrateur au plus vite !");
            moderator.sendMessage("§8§lErreur: §r§7 config.yml > §b" + sanction.getInitialType() + " §c(Mauvais type)");
        }
    }

    // Algorithme permettant la génération du temps d'expiration adéquat selon le joueur
    private static void generateBanMute(Sanction tempSanction, Player moderator, Player target, int muteMinPerPts, int banDayPerPts) {
        final int targetPoints = JusticeHands.getSqlPA().getPoints(target.getUniqueId());
        final long currentTime = System.currentTimeMillis();
        final double extraTime;

        final long longMin = 60l*1000l; // sec(60) * ms(1000)
        final long longDay = 24l*60l*60l*1000l; // hour(24) * min(60) * sec(60) * ms(1000)

        if (tempSanction.getInitialType().equals("mute")) {
            // Points convertis en minutes (MUTE)
            extraTime = ((tempSanction.getPoints() + targetPoints) / Long.valueOf(muteMinPerPts)) * longMin;
        } else {
            // Points convertis en jours (BAN)
            extraTime = ((tempSanction.getPoints() + targetPoints) / Long.valueOf(banDayPerPts)) * longDay;
        }

        long expireTime = (long) (currentTime + extraTime);
        Timestamp expireDate = new Timestamp(expireTime);

        JusticeHands.getSqlSM().addSanction(target, moderator, tempSanction, expireDate);
        sendAlertMsg(target, tempSanction, GeneralUtils.getPrefix("SM"), currentTime, expireTime);
    }

    private static void sendAlertMsg(Player target, Sanction sanction, String SMPrefix, long currentTime, long expireTime) {
        final long timeDifference = expireTime - currentTime;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player != target) {
                if (sanction.getInitialType().equals("kick")) {
                    player.sendMessage(SMPrefix + "§7Le joueur §c" + target.getName() + " §7a été éjecté du serveur. (§8" + sanction.getReason() + "§7)");
                } else if (sanction.getInitialType().equals("mute")) {
                    player.sendMessage(SMPrefix + "§7Le joueur §c" + target.getName() + " §7est maintenant réduit au silence pendant §c" + GeneralUtils.timeRemaining(timeDifference) + "§7. (§8" + sanction.getReason() + "§7)");
                } else if (sanction.getInitialType().equals("ban") || sanction.getInitialType().equals("risingban")) {
                    player.sendMessage(SMPrefix + "§cLe joueur §4" + target.getName() + " §ca été banni du serveur pendant §4" + GeneralUtils.timeRemaining(timeDifference) + "§7. (§8" + sanction.getReason() + "§7)");
                } else if (sanction.getInitialType().equals("bandef")) {
                    player.sendMessage(SMPrefix + "§cLe joueur §4" + target.getName() + " §ca été banni défénitivement du serveur. §7(§8" + sanction.getReason() + "§7)");
                }
            } else {
                if (sanction.getInitialType().equals("kick")) {
                    //nothing to do
                } else if (sanction.getInitialType().equals("mute")) {
                    target.sendMessage(SMPrefix + "§7Tu es maintenant réduit au silence pendant §c" + GeneralUtils.timeRemaining(timeDifference) + "§7. (§8" + sanction.getReason() + "§7)");
                } else if (sanction.getInitialType().equals("ban") || sanction.getInitialType().equals("risingban")) {
                    //nothing to do
                } else if (sanction.getInitialType().equals("bandef")) {
                    //nothing to do
                }
            }
        }
    }
}
