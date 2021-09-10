package fr.fallenvaders.minecraft.justice_hands;

import fr.fallenvaders.minecraft.justice_hands.criminalrecords.CommandCR;
import fr.fallenvaders.minecraft.justice_hands.criminalrecords.listeners.PlayerJoinListener;
import fr.fallenvaders.minecraft.justice_hands.keyskeeper.listeners.AsyncChatListener;
import fr.fallenvaders.minecraft.justice_hands.keyskeeper.listeners.PlayerLoginListener;
import fr.fallenvaders.minecraft.justice_hands.sanctionmanager.CategoriesList;
import fr.fallenvaders.minecraft.justice_hands.sanctionmanager.CommandSM;
import fr.fallenvaders.minecraft.justice_hands.sql.SqlConnection;
import fr.fallenvaders.minecraft.justice_hands.sql.SqlKeysKeeper;
import fr.fallenvaders.minecraft.justice_hands.sql.SqlPlayerAccount;
import fr.fallenvaders.minecraft.justice_hands.sql.SqlSanctionManager;
import org.bukkit.plugin.java.JavaPlugin;

public class JusticeHands extends JavaPlugin {

  public static JavaPlugin PLUGIN;

  private static SqlConnection sql = null;
  private static SqlPlayerAccount sqlPA = null;
  private static SqlSanctionManager sqlSM = null;
  private static SqlKeysKeeper sqlKK = null;

  public static void enableModule(JavaPlugin plugin) {
    PLUGIN = plugin;
    plugin.getConfig().options().copyDefaults(true);
    plugin.saveConfig();

    // Connexion à la base de données
    String database = PLUGIN.getConfig().getString("database.database");
    String host = PLUGIN.getConfig().getString("database.host");
    String user = PLUGIN.getConfig().getString("database.user");
    String password = PLUGIN.getConfig().getString("database.password");
    sql = new SqlConnection("jdbc:mariadb://" + host + ":3306/" + database, user, password);
    sql.connection();

    // On donne la connection
    sqlPA = new SqlPlayerAccount(sql.getConnection());
    sqlSM = new SqlSanctionManager(sql.getConnection());
    sqlKK = new SqlKeysKeeper(sql.getConnection());

    // On active commandes et listeners
    activeCommands();
    activeListeners();

    // On charge les sanctions du dossier de configuration
    CategoriesList.getSanctionsConfig(plugin.getConfig());
  }

  public static void disableModule(JavaPlugin plugin) {
    // Déconnection de la base de données
    sql.disconnect();
  }

  private static void activeListeners() {
    PLUGIN.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), PLUGIN);
    PLUGIN.getServer().getPluginManager().registerEvents(new AsyncChatListener(), PLUGIN);
    PLUGIN.getServer().getPluginManager().registerEvents(new PlayerLoginListener(), PLUGIN);
  }

  private static void activeCommands() {
    PLUGIN.getCommand("cr").setExecutor(new CommandCR());
    // getCommand("mt").setExecutor(new CommandMT(this));
    PLUGIN.getCommand("sm").setExecutor(new CommandSM(PLUGIN.getConfig()));
  }

  //// **** ACCESSEURS & MUTATEURS ****////
  public static SqlConnection getSql() {
    return sql;
  }

  public static SqlPlayerAccount getSqlPA() {
    return sqlPA;
  }

  public static SqlSanctionManager getSqlSM() {
    return sqlSM;
  }

  public static SqlKeysKeeper getSqlKK() {
    return sqlKK;
  }
  //////////////////////////////////////////
}
