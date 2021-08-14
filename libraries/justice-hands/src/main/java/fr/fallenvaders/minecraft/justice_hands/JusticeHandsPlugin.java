package fr.fallenvaders.minecraft.justice_hands;

import fr.fallenvaders.minecraft.justicehands.criminalrecords.CommandCR;
import fr.fallenvaders.minecraft.justicehands.criminalrecords.listeners.PlayerJoinListener;
import fr.fallenvaders.minecraft.justicehands.keyskeeper.listeners.PlayerLoginListener;
import fr.fallenvaders.minecraft.justicehands.sanctionmanager.CategoriesList;
import fr.fallenvaders.minecraft.justicehands.sanctionmanager.CommandSM;
import fr.fallenvaders.minecraft.justicehands.sql.SqlConnection;
import fr.fallenvaders.minecraft.justicehands.sql.SqlKeysKeeper;
import fr.fallenvaders.minecraft.justicehands.sql.SqlPlayerAccount;
import fr.fallenvaders.minecraft.justicehands.sql.SqlSanctionManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.fallenvaders.minecraft.justicehands.keyskeeper.listeners.AsyncChatListener;

public class JusticeHandsPlugin extends JavaPlugin {
	private static SqlConnection sql = null;
	private static SqlPlayerAccount sqlPA = null;
	private static SqlSanctionManager sqlSM = null;
	private static SqlKeysKeeper sqlKK = null;

	@Override
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		saveConfig();

		// Connexion à la base de données
		sql = new SqlConnection("jdbc:mariadb://178.170.13.15:3306/justicehands","dbuser","dbuser");
		sql.connection();
		
		//On donne la connection
		sqlPA = new SqlPlayerAccount(sql.getConnection());
		sqlSM = new SqlSanctionManager(sql.getConnection());
		sqlKK = new SqlKeysKeeper(sql.getConnection());
		
		// On active commandes et listeners
		activeCommands();
		activeListeners();
		
		// On charge les sanctions du dossier de configuration
		CategoriesList.getSanctionsConfig(getConfig());
	}
	
	@Override
	public void onDisable() {
		// Déconnection de la base de données
		sql.disconnect();
	}

	private void activeListeners() {
		getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
		getServer().getPluginManager().registerEvents(new AsyncChatListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerLoginListener(), this);
	}

	private void activeCommands() {
		getCommand("cr").setExecutor(new CommandCR());
		//getCommand("mt").setExecutor(new CommandMT(this));
		getCommand("sm").setExecutor(new CommandSM(this.getConfig()));
	}

	//// **** ACCESSEURS & MUTATEURS ****////
	public static SqlConnection getSql() {
		return sql;
	}
	public static SqlPlayerAccount getSqlPA() {
		return sqlPA;
	}
	public static SqlSanctionManager getSqlSM() {return sqlSM;}
	public static SqlKeysKeeper getSqlKK() {
		return sqlKK;
	}
	//////////////////////////////////////////
}
