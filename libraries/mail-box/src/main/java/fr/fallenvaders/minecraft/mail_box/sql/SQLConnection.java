package fr.fallenvaders.minecraft.mail_box.sql;

import fr.fallenvaders.minecraft.mail_box.MailBox;
import fr.fallenvaders.minecraft.mail_box.utils.LangManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class SQLConnection {
  private Connection connection;
  private String jdbc;
  private String host;
  private String database;
  private String user;
  private String password;
  private boolean useSSL = MailBox.main.getConfig().getBoolean("database.useSSL");

  public SQLConnection() {
    this.setJdbc("jdbc:mysql://");
    this.setHost(MailBox.main.getConfig().getString("database.host"));
    this.setDatabase(MailBox.main.getConfig().getString("database.database"));
    this.setUser(MailBox.main.getConfig().getString("database.user"));
    this.setPassword(MailBox.main.getConfig().getString("database.password"));

    this.connect();
  }

  public boolean connect() {
    boolean result = false;
    MailBox.main.getLogger().log(Level.INFO, LangManager.getValue("string_sql_connection"));

    try {
      this.setConnection(DriverManager.getConnection(getUrl(), getUser(), getPassword()));
      MailBox.main.getLogger().log(Level.INFO, LangManager.getValue("string_sql_connected"));

      result = true;

    } catch (SQLException e) {
      MailBox.main
          .getLogger()
          .log(Level.INFO, LangManager.getValue("string_sql_impossible_to_connect"), e);
    }

    return result;
  }

  private String getUrl() {
    return this.getJdbc()
        + this.getHost()
        + "/"
        + this.getDatabase()
        + "?useSSL="
        + this.getUseSSL();
  }

  public boolean startTransaction() {
    boolean res = false;
    Connection conn = this.getConnection();

    if (conn != null) {
      try {
        conn.setAutoCommit(false);
        res = true;

      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return res;
  }

  public boolean rollBack() {
    boolean res = false;
    Connection conn = this.getConnection();

    if (conn != null) {
      try {
        if (!conn.getAutoCommit()) {
          MailBox.main.getLogger().log(Level.SEVERE, "Transaction is being rolled back");
          this.getConnection().rollback();
          res = true;
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return res;
  }

  public boolean commit() {
    boolean res = false;
    Connection conn = this.getConnection();

    if (conn != null) {
      try {
        if (!conn.getAutoCommit()) {
          this.getConnection().commit();
          this.getConnection().setAutoCommit(true);
        }

        res = true;
      } catch (SQLException e) {
        e.printStackTrace();
        this.rollBack();
      }
    }

    return res;
  }

  public void disconnect() {
    Connection conn = this.getConnection();

    if (conn != null) {
      try {
        conn.close();
        MailBox.main.getLogger().log(Level.INFO, LangManager.getValue("string_sql_disconnected"));

      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public void refresh() {
    Connection conn = this.getConnection();

    if (conn != null) {
      try {
        conn.close();
        this.setConnection(
            DriverManager.getConnection(this.getUrl(), this.getUser(), this.getPassword()));

      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public Connection getConnection() {
    try {
      if (this.connection == null || this.connection.isClosed()) {
        this.connect();
      }
    } catch (SQLException ignored) {
    }

    return this.connection;
  }

  private SQLConnection setConnection(Connection connection) {
    this.connection = connection;
    return this;
  }

  public String getJdbc() {
    return this.jdbc;
  }

  public SQLConnection setJdbc(String jdbc) {
    this.jdbc = jdbc;
    return this;
  }

  public String getHost() {
    return this.host;
  }

  public SQLConnection setHost(String host) {
    this.host = host;
    return this;
  }

  public String getDatabase() {
    return this.database;
  }

  public SQLConnection setDatabase(String database) {
    this.database = database;
    return this;
  }

  public String getUser() {
    return this.user;
  }

  public SQLConnection setUser(String user) {
    this.user = user;
    return this;
  }

  public String getPassword() {
    return this.password;
  }

  public SQLConnection setPassword(String password) {
    this.password = password;
    return this;
  }

  public boolean getUseSSL() {
    return useSSL;
  }
}
