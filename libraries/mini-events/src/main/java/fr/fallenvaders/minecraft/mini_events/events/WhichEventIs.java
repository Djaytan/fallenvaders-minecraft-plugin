package fr.fallenvaders.minecraft.mini_events.events;

import fr.fallenvaders.minecraft.mini_events.GameName;
import fr.fallenvaders.minecraft.mini_events.MiniEvents;
import fr.fallenvaders.minecraft.mini_events.events.bowspleef.tasks.BowSpleefDetection;
import fr.fallenvaders.minecraft.mini_events.events.enclume.EnclumeActions;
import fr.fallenvaders.minecraft.mini_events.events.spleef.tasks.SpleefDetection;

public class WhichEventIs {

    // Permet de sélectionner l'événement en cours et de le lancer en effectuant la
    // ou les premières actions nécéssaires.

    public static void SelectEvent() {
        if (MiniEvents.getGame().getGameName() == GameName.SPLEEF) {
            SpleefDetection detection = new SpleefDetection();
            detection.runTaskTimer(MiniEvents.PLUGIN, 0, 1);
        }
        if (MiniEvents.getGame().getGameName() == GameName.ENCLUME) {
            EnclumeActions.StartingWaves();
        }
        if (MiniEvents.getGame().getGameName() == GameName.BOWSPLEEF) {
            BowSpleefDetection detection = new BowSpleefDetection();
            detection.runTaskTimer(MiniEvents.PLUGIN, 0, 1);
        }
    }
}
