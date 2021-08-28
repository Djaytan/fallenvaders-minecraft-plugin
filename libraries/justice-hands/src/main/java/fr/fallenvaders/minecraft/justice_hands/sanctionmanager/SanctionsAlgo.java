package fr.fallenvaders.minecraft.justice_hands.sanctionmanager;

import java.sql.Timestamp;

import fr.fallenvaders.minecraft.justice_hands.GeneralUtils;
import fr.fallenvaders.minecraft.justice_hands.JusticeHands;
import fr.fallenvaders.minecraft.justice_hands.sanctionmanager.objects.Sanction;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SanctionsAlgo {

    public static final FileConfiguration CONFIG = JusticeHands.PLUGIN.getConfig();
    public static final String CFG_PATH_POINT = "justicehands.sanctionmanager.points-system";
    public static final int CFG_MUTE_MIN_POINT = CONFIG.getInt(CFG_PATH_POINT + ".mute-min-points");
    public static final int CFG_BAN_DAY_POINT = CONFIG.getInt(CFG_PATH_POINT + ".ban-day-points");
    public static final int CFG_RISINGBAN_LIMIT = CONFIG.getInt(CFG_PATH_POINT + ".risingban-points");
    public static final long LONG_MIN_IN_MS = 60l * 1000l; // sec(60) * ms(1000)
    public static final long LONG_DAY_IN_MS = 24l * 60l * 60l * 1000l; // hour(24) * min(60) * sec(60) * ms(1000)

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

        // Lors de l'algorithme, les points et le types de sanction peuvent être modifiés.
        // On va donc créer une nouvelle sanction identique à celle en paramètre.
        Sanction tempSanction = (Sanction) sanction.clone();
        final int targetPoints = JusticeHands.getSqlPA().getPoints(target.getUniqueId());

        // Permet de dire si un joueur mérite un ban au lieu d'un mute ou d'un kick
        if (targetPoints >= CFG_RISINGBAN_LIMIT
            && !("ban".equals(tempSanction.getInitialType())
            || "bandef".equals(tempSanction.getInitialType()))) {
            tempSanction.setInitialType("risingban");
            tempSanction.setPoints(tempSanction.getPoints() * 2); // Multiplication des points par deux
            generateBanMute(
                tempSanction, moderator, target, CFG_MUTE_MIN_POINT, CFG_BAN_DAY_POINT);
            return;

            // Le type de sanction est un type sans date d'expiration
        } else if (tempSanction.getInitialType().equals("kick")
            || tempSanction.getInitialType().equals("bandef")) {
            JusticeHands.getSqlSM().addSanction(target, moderator, tempSanction, null);
            sendAlertMsg(
                target, tempSanction, GeneralUtils.getPrefix("SM"), System.currentTimeMillis(), 0);
            return;

        } else if (tempSanction.getInitialType().equals("mute")
            || tempSanction.getInitialType().equals("ban")) {
            generateBanMute(
                tempSanction, moderator, target, CFG_MUTE_MIN_POINT, CFG_BAN_DAY_POINT);
            return;

        } else {
            moderator.sendMessage(
                GeneralUtils.getPrefix("SM")
                    + "§cUne erreur vient de se produire, l'action n'a pas aboutie. Veuillez contacter un administrateur au plus vite !");
            moderator.sendMessage(
                "§8§lErreur: §r§7 config.yml > §b" + sanction.getInitialType() + " §c(Mauvais type)");
        }
    }

    // Algorithme permettant la génération du temps d'expiration adéquat selon le joueur
    private static void generateBanMute(
        Sanction tempSanction, Player moderator, Player target, int muteMinPerPts, int banDayPerPts) {
        final int targetPoints = JusticeHands.getSqlPA().getPoints(target.getUniqueId());
        final long currentTime = System.currentTimeMillis();
        final double extraTime;

        if (tempSanction.getInitialType().equals("mute")) {
            // Points convertis en minutes (MUTE)
            extraTime =
                ((tempSanction.getPoints() + targetPoints) / Long.valueOf(muteMinPerPts)) * LONG_MIN_IN_MS;
        } else {
            // Points convertis en jours (BAN)
            extraTime =
                ((tempSanction.getPoints() + targetPoints) / Long.valueOf(banDayPerPts)) * LONG_DAY_IN_MS;
        }

        long expireTime = (long) (currentTime + extraTime);
        Timestamp expireDate = new Timestamp(expireTime);

        JusticeHands.getSqlSM().addSanction(target, moderator, tempSanction, expireDate);
        sendAlertMsg(target, tempSanction, GeneralUtils.getPrefix("SM"), currentTime, expireTime);
    }

    private static void sendAlertMsg(
        Player target, Sanction sanction, String SMPrefix, long currentTime, long expireTime) {
        final long timeDifference = expireTime - currentTime;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player != target) {
                if (sanction.getInitialType().equals("kick")) {
                    player.sendMessage(
                        SMPrefix
                            + "§7Le joueur §c"
                            + target.getName()
                            + " §7a été éjecté du serveur. (§8"
                            + sanction.getReason()
                            + "§7)");
                } else if (sanction.getInitialType().equals("mute")) {
                    player.sendMessage(
                        SMPrefix
                            + "§7Le joueur §c"
                            + target.getName()
                            + " §7est maintenant réduit au silence pendant §c"
                            + GeneralUtils.timeRemaining(timeDifference)
                            + "§7. (§8"
                            + sanction.getReason()
                            + "§7)");
                } else if (sanction.getInitialType().equals("ban")
                    || sanction.getInitialType().equals("risingban")) {
                    player.sendMessage(
                        SMPrefix
                            + "§cLe joueur §4"
                            + target.getName()
                            + " §ca été banni du serveur pendant §4"
                            + GeneralUtils.timeRemaining(timeDifference)
                            + "§7. (§8"
                            + sanction.getReason()
                            + "§7)");
                } else if (sanction.getInitialType().equals("bandef")) {
                    player.sendMessage(
                        SMPrefix
                            + "§cLe joueur §4"
                            + target.getName()
                            + " §ca été banni défénitivement du serveur. §7(§8"
                            + sanction.getReason()
                            + "§7)");
                }
            } else {
                if (sanction.getInitialType().equals("kick")) {
                    // nothing to do
                } else if (sanction.getInitialType().equals("mute")) {
                    target.sendMessage(
                        SMPrefix
                            + "§7Tu es maintenant réduit au silence pendant §c"
                            + GeneralUtils.timeRemaining(timeDifference)
                            + "§7. (§8"
                            + sanction.getReason()
                            + "§7)");
                } else if (sanction.getInitialType().equals("ban")
                    || sanction.getInitialType().equals("risingban")) {
                    // nothing to do
                } else if (sanction.getInitialType().equals("bandef")) {
                    // nothing to do
                }
            }
        }
    }
}
