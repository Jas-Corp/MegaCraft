package fr.jas14.megacraft.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseManager {

	private String urlBase;
	private String host;
	private String dataBase;
	private String userName;
	private String password;
	private static Connection connection;

	public DataBaseManager(String urlBase, String host, String dataBase, String userName, String password) {
		this.userName = userName;
		this.urlBase = urlBase;
		this.host = host;
		this.password = password;
		this.dataBase = dataBase;

	}

	public Connection getConnection() {
		return connection;
	}

	public void connection() {
		if (!isOnline()) {
			try {
				connection = DriverManager.getConnection(this.urlBase + this.host + "/" + this.dataBase, this.userName, this.password);
				System.out.println("§8[§2DataBaseManager§8] §fSuccefully connected.");
				return;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
			
		}

	}

	public void deconnection() {
		if (isOnline()) {
			
			try {
				connection.close();
				System.out.println("§8[§2DataBaseManager§8] §fSuccefully disconected.");
			} catch (SQLException e) {
				e.printStackTrace();			}
			
			
		}

	}

	public boolean isOnline() {
		try {
			if ((connection == null) || (connection.isClosed())) {
				return false;
			}
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

}
