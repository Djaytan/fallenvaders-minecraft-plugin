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

    public static Component ejectingMessageCpnt(CJSanction sanction) {

        final Component cpnt = LegacyComponentSerializer.legacyAmpersand().deserialize(
            String.format(GeneralUtils.getPrefix("kk") +
                        "\n§cVous avez été éjecté du serveur pour la raison suivante : " +
                        "\n§7ID de Sanction : " + sanction.getID() + "§f - §7" + sanction.getName() +
                        "\n" +
                        "\n§cLa réalité de cette infraction a été établie, conformément au réglèment du serveur" +
                        "\nque vous avez précédement lu et approuvé." +
                        "\n§eCette sanction vous apportera donc §7" + sanction.getPoints() + " points de sanction §eet sera enregistrée dans " +
                        "\nvotre casier judiciaire." +
                        "\n§7Date de l'infraction : " + new Date(sanction.getTSDate().getTime())
                )
            );
        return cpnt;
    }

    public static Component loginBanMessage(CJSanction sanction) {
        String pattern = "dd/MM/yyyy HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, new Locale("fr", "FR"));
        String date = sdf.format(sanction.getTSDate().getTime());
        String expireDate = sdf.format(sanction.getTSExpireDate().getTime());


        final Component cpnt = LegacyComponentSerializer.legacyAmpersand().deserialize(
            String.format(GeneralUtils.getPrefix("kk") +
                "\n§cVous avez été banni temporairement du serveur pour la raison suivante : " +
                "\n" +
                "\n§bID de Sanction : §7" + sanction.getID() + "§b - §7" + sanction.getName() +
                "\n§bRaison : §7" + sanction.getReason() +
                "\n§bDate : §7" + date + " §b- §7" + expireDate +
                "\n§bTemps restant : §7" + GeneralUtils.timeRemaining(sanction.getTSExpireDate().getTime()-System.currentTimeMillis())
            )
        );
        return cpnt;
    }

    public static Component loginBanDefMessage(CJSanction sanction) {
        String pattern = "dd/MM/yyyy HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, new Locale("fr", "FR"));
        String date = sdf.format(sanction.getTSDate().getTime());

        final Component cpnt = LegacyComponentSerializer.legacyAmpersand().deserialize(
            String.format(GeneralUtils.getPrefix("kk") +
                "\n§cVous avez été banni définitivement du serveur pour la raison suivante : " +
                "\n" +
                "\n§bID de Sanction : §7" + sanction.getID() + "§b - §7" + sanction.getName() +
                "\n§bRaison : §7" + sanction.getReason() +
                "\n§bDate : §7" + date
            )
        );
        return cpnt;
    }
}
