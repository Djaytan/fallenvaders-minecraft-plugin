package fr.fallenvaders.minecraft.mini_events;

// Les différents états que peut avoir un événement.
//
// Quand la commande permettant de savoir l'état en cours sera éxécutée,
// le plugin retournera à l'identique les nom ci-dessous.

public enum GameState {
	NOTSTARTED, WAITING, STARTING, PLAYING, FINISH;
}
