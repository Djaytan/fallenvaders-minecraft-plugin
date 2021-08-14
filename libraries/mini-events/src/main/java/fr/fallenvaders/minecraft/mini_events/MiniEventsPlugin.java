package fr.fallenvaders.minecraft.mini_events;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import fr.fallenvaders.minecraft.mini_events.commands.CommandEVENT;
import fr.fallenvaders.minecraft.mini_events.commands.TabComplete;
import fr.fallenvaders.minecraft.mini_events.events.bowspleef.listeners.BowSpleefArrowHitListener;
import fr.fallenvaders.minecraft.mini_events.events.bowspleef.listeners.BowSpleefEntityExplodeListener;
import fr.fallenvaders.minecraft.mini_events.events.enclume.listeners.EnclumeEntityDamageListener;
import fr.fallenvaders.minecraft.mini_events.events.spleef.listeners.SpleefBlockBreakListener;
import fr.fallenvaders.minecraft.mini_events.listeners.InventoryClickListener;
import fr.fallenvaders.minecraft.mini_events.listeners.PlayerJoinListener;
import fr.fallenvaders.minecraft.mini_events.listeners.PlayerQuitListener;
import fr.fallenvaders.minecraft.mini_events.listeners.PlayerTeleportListener;

public class MiniEventsPlugin extends JavaPlugin {

	public String prefix = "§7[§6MiniEvents§7] "; // Prefix pour les messages dans le chat.
	private List<UUID> participants = new ArrayList<>(); // Liste des participants de l'événement.
	private List<UUID> leaveDuringEvent = new ArrayList<>(); // Liste des joueurs déconnectés durant un événement.
	private List<Location> blockLocation = new ArrayList<>(); // Liste de location de block (Régénération intélligente).
	private GameState state; // État de l'événement.
	private GameName name; // Nom de l'événement.

	@Override
	public void onEnable() {
		saveDefaultConfig();

		setGameState(GameState.NOTSTARTED);
		setGameName(GameName.NONE);
		activeCommands();
		activeGeneralListeners();
		activeGameListeners();
	}

	private void activeCommands() {
		getCommand("event").setExecutor(new CommandEVENT(this));
		getCommand("event").setTabCompleter(new TabComplete());
	}

	private void activeGeneralListeners() {
		getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerTeleportListener(this), this);
	}

	private void activeGameListeners() {
		getServer().getPluginManager().registerEvents(new SpleefBlockBreakListener(this), this);
		getServer().getPluginManager().registerEvents(new EnclumeEntityDamageListener(this), this);
		getServer().getPluginManager().registerEvents(new BowSpleefArrowHitListener(this), this);
		getServer().getPluginManager().registerEvents(new BowSpleefEntityExplodeListener(this), this);
	}

	// Modifier l'état de l'event.
	public void setGameState(GameState state) {
		this.state = state;
	}

	// Récupère l'état de l'event.
	public GameState getGameState() {
		return this.state;
	}

	// Récupère la liste des joueurs voulant jouer ou qui sont entrain de jouer.
	public List<UUID> getParticipants() {
		return participants;
	}

	// Récupère la liste des joueurs déconnectés durant l'événement.
	public List<UUID> getLeaveDuringEvent() {
		return leaveDuringEvent;
	}

	// Récupère une liste de locations de blocks.
	public List<Location> getBlockLoc() {
		return blockLocation;
	}

	// Modifie le type d'événement.
	public void setGameName(GameName name) {
		this.name = name;
	}

	// Récupère l'événement en cours.
	public GameName getGameName() {
		return this.name;
	}
}
