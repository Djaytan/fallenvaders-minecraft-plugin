package fr.fallenvaders.minecraft.core.mail_box.sql;

import fr.fallenvaders.minecraft.core.FallenVadersCorePlugin;
import fr.fallenvaders.minecraft.core.mail_box.utils.LangManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

public class SQLConnection {
	public static final String SGBD_TYPE_ROOT = "jdbc:mysql://";
	private static final SQLConnection INSTANCE = new SQLConnection();

	private Connection connection;
	private String jdbc;
	private String host;
	private String database;
	private String user;
	private String password;
	private Boolean useSSL = FallenVadersCorePlugin.main.getConfig().getBoolean("database.useSSL");

	public static SQLConnection getInstance() {
		return INSTANCE;
	}

	public void connect() {
		FallenVadersCorePlugin.main.getLogger().log(Level.INFO, LangManager.getValue("string_sql_connection"));

		try {
			this.setConnection(DriverManager.getConnection(getUrl(), getUser(), getPassword()));
			FallenVadersCorePlugin.main.getLogger().log(Level.INFO, LangManager.getValue("string_sql_connected"));

		} catch (SQLException e) {
			FallenVadersCorePlugin.main.getLogger().log(Level.INFO, LangManager.getValue("string_sql_impossible_to_connect"));
		}
	}

	private String getUrl() {
		return this.getJdbc() + this.getHost() + "/" + this.getDatabase() + "?useSSL=" + this.getUseSSL();
	}

	public Boolean startTransaction() {
		Boolean res = false;
		if (this.isConnected()) {
			try {
				this.getConnection().setAutoCommit(false);
				res = true;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return res;
	}

	public Boolean rollBack() {
		Boolean res = false;

		if (this.isConnected()) {
			try {
				if (!this.getConnection().getAutoCommit()) {
					FallenVadersCorePlugin.main.getLogger().log(Level.SEVERE, "Transaction is being rolled back");
					this.getConnection().rollback();
					res = true;

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return res;
	}

	public Boolean commit() {
		Boolean res = false;

		if (this.isConnected()) {
			try {
				if(!this.getConnection().getAutoCommit() ) {
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
		if (this.isConnected()) {
			try {
				this.getConnection().close();
				FallenVadersCorePlugin.main.getLogger().log(Level.INFO, LangManager.getValue("string_sql_disconnected"));

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void refresh() {
		if (this.isConnected()) {
			try {
				this.getConnection().close();
				this.setConnection(DriverManager.getConnection(this.getUrl(), this.getUser(), this.getPassword()));

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	public Boolean isConnected() {
		boolean res = false;
		
		try {
			PreparedStatement query = this.getConnection().prepareStatement("SELECT 1");
			query.executeQuery();
			query.close();
			res = true;

		} catch (Exception exception) {
			FallenVadersCorePlugin.main.getLogger().log(Level.INFO, LangManager.getValue("string_sql_connexion_error") );
		}
		
		return res;
	}

	public Connection getConnection() {
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

	public Boolean getUseSSL() {
		return useSSL;
	}
}
