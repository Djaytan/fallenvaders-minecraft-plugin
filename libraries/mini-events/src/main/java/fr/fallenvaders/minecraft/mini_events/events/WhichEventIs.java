package fr.fallenvaders.minecraft.mini_events.events;

import fr.fallenvaders.minecraft.mini_events.GameName;
import fr.fallenvaders.minecraft.mini_events.MiniEventsPlugin;
import fr.fallenvaders.minecraft.mini_events.events.bowspleef.tasks.BowSpleefDetection;
import fr.fallenvaders.minecraft.mini_events.events.enclume.EnclumeActions;
import fr.fallenvaders.minecraft.mini_events.events.spleef.tasks.SpleefDetection;

public class WhichEventIs {

	// Permet de sélectionner l'événement en cours et de le lancer en effectuant la
	// ou les premières actions nécéssaires.

	public static void SelectEvent(MiniEventsPlugin main) {
		if (main.getGameName() == GameName.SPLEEF) {
			SpleefDetection detection = new SpleefDetection(main);
			detection.runTaskTimer(main, 0, 1);
		}
		if (main.getGameName() == GameName.ENCLUME) {
			EnclumeActions.StartingWaves(main);
		}
		if (main.getGameName() == GameName.BOWSPLEEF) {
			BowSpleefDetection detection = new BowSpleefDetection(main);
			detection.runTaskTimer(main, 0, 1);
		}
	}
}
