package mc.obliviate.bloksqliteapi;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLHandler {

	private static Statement statement;
	private static Connection connection;
	private final String filePath;

	public SQLHandler(final String filePath) {
		this.filePath = filePath;
	}

	public static Connection getConnection() {
		return connection;
	}

	public static Statement getStatement() {
		return statement;
	}

	public void connect(final String databaseName) {
		connection = null;
		try {
			final String URL = "jdbc:sqlite:" + filePath + File.separator + databaseName + ".db";
			connection = DriverManager.getConnection(URL);
			statement = connection.createStatement();
			onConnect();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void onConnect() {

	}
}
