package fr.fallenvaders.minecraft.justice_hands.keyskeeper;

import fr.fallenvaders.minecraft.justice_hands.GeneralUtils;
import fr.fallenvaders.minecraft.justice_hands.criminalrecords.objects.CJSanction;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class KeysKeeperComponent {
  public static final String DAYTIME_PATTERN = "dd/MM/yyyy HH:mm:ss";
  public static final SimpleDateFormat SDF =
      new SimpleDateFormat(DAYTIME_PATTERN, new Locale("fr", "FR"));

  public static Component ejectingMessageCpnt(CJSanction sanction) {
    String date = SDF.format(sanction.getTSDate().getTime());

    final Component cpnt =
        LegacyComponentSerializer.legacyAmpersand()
            .deserialize(
                String.format(
                    "%s\n§cVous avez été éjecté du serveur pour la raison suivante : \n§7ID de Sanction : %s§f - §7%s\n\n§cLa réalité de cette infraction a été établie, conformément au réglèment du serveur\nque vous avez précédement lu et approuvé.\n§eCette sanction vous apportera donc §7%d points de sanction §eet sera enregistrée dans \nvotre casier judiciaire.\n§7Date de l'infraction : %s",
                    GeneralUtils.getPrefix("kk"),
                    sanction.getID(),
                    sanction.getName(),
                    sanction.getPoints(),
                    date));

    return cpnt;
  }

  public static Component loginBanMessage(CJSanction sanction) {
    String date = SDF.format(sanction.getTSDate().getTime());
    String expireDate = SDF.format(sanction.getTSExpireDate().getTime());

    final Component cpnt =
        LegacyComponentSerializer.legacyAmpersand()
            .deserialize(
                String.format(
                    "%s\n§cVous avez été banni temporairement du serveur pour la raison suivante : \n\n§bID de Sanction : §7%s§b - §7%s\n§bRaison : §7%s\n§bDate : §7%s §b- §7%s\n§bTemps restant : §7%s",
                    GeneralUtils.getPrefix("kk"),
                    sanction.getID(),
                    sanction.getName(),
                    sanction.getReason(),
                    date,
                    expireDate,
                    GeneralUtils.timeRemaining(
                        sanction.getTSExpireDate().getTime() - System.currentTimeMillis())));
    return cpnt;
  }

  public static Component loginBanDefMessage(CJSanction sanction) {
    String date = SDF.format(sanction.getTSDate().getTime());

      final Component cpnt =
          LegacyComponentSerializer.legacyAmpersand()
              .deserialize(
                  String.format(
                      "%s\n§cVous avez été banni définitivement du serveur pour la raison suivante : \n\n§bID de Sanction : §7%s§b - §7%s\n§bRaison : §7%s\n§bDate : §7%s",
                      GeneralUtils.getPrefix("kk"),
                      sanction.getID(),
                      sanction.getName(),
                      sanction.getReason(),
                      date));
      return cpnt;
  }
}
