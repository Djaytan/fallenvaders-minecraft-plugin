package fr.fallenvaders.minecraft.justice_hands.sql;

import java.sql.*;

public class SqlConnection {

  // Connexion et déconnexion de la base de données du serveur
  private Connection connection;
  private String url, user, pass;

  // Constructeur
  public SqlConnection(String url, String user, String pass) {
    this.url = url;
    this.user = user;
    this.pass = pass;
  }

  // Connexion à la base de données
  public void connection() {
    if (!isConnected()) {
      connection = null;
      try {
        Class.forName("org.mariadb.jdbc.Driver");
        connection = DriverManager.getConnection(url, user, pass);
        System.out.println("[JusticeHands] Base de données connectée");
      } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
      }
      // Vérification lors de la connexion de l'intégrité de la base de données
      dbIntegrityVerification();
    }
  }

  public void dbIntegrityVerification() {
    if (isConnected()) {
      try {
        DatabaseMetaData md = connection.getMetaData();
        PreparedStatement q;
        boolean exists;

        // Création de la table : "players_points" si elle n'existe pas
        exists = md.getTables(null, null, "players_points", null).next();
        if (!exists) {
          q =
              connection.prepareStatement(
                  "CREATE TABLE `players_points` (\n"
                      + "\t`uuid` CHAR(255) NOT NULL,\n"
                      + "\t`points` INT NOT NULL DEFAULT 0\n"
                      + ")\n"
                      + "COLLATE='utf8_general_ci'\n"
                      + ";\n");
          q.execute();
          q.close();
        }

        // Création de la table : "sanctions_list" si elle n'existe pas
        exists = md.getTables(null, null, "sanctions_list", null).next();
        if (!exists) {
          q =
              connection.prepareStatement(
                  "CREATE TABLE `sanctions_list` (\n"
                      + "\t`id` INT(255) UNSIGNED NOT NULL AUTO_INCREMENT,\n"
                      + "\t`uuid` CHAR(255) NOT NULL DEFAULT '' COLLATE 'utf8_general_ci',\n"
                      + "\t`name` CHAR(255) NOT NULL COLLATE 'utf8_general_ci',\n"
                      + "\t`reason` CHAR(255) NOT NULL COLLATE 'utf8_general_ci',\n"
                      + "\t`points` INT(255) UNSIGNED NOT NULL,\n"
                      + "\t`date` TIMESTAMP NOT NULL DEFAULT (sysdate() + interval 2 hour),\n"
                      + "\t`expiredate` TIMESTAMP NULL DEFAULT NULL,\n"
                      + "\t`moderator` CHAR(255) NOT NULL COLLATE 'utf8_general_ci',\n"
                      + "\t`type` CHAR(255) NOT NULL COLLATE 'utf8_general_ci',\n"
                      + "\t`state` CHAR(255) NOT NULL DEFAULT 'active' COLLATE 'utf8_general_ci',\n"
                      + "\tPRIMARY KEY (`id`) USING BTREE\n"
                      + ")\n"
                      + "COLLATE='utf8_general_ci'\n"
                      + "ENGINE=InnoDB\n"
                      + "AUTO_INCREMENT=1\n"
                      + ";");
          q.execute();
          q.close();
        }

      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  // Déconnexion de la base de données
  public void disconnect() {
    if (isConnected()) {
      try {
        connection.close();
        System.out.println("[JusticeHands] Base de données déconnectée");
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  // Déja connecté ?
  public boolean isConnected() {
    return connection != null;
  }

  // Récuperer la connexion
  public Connection getConnection() {
    if (isConnected()) {
      return connection;
    }
    return null;
  }
}
