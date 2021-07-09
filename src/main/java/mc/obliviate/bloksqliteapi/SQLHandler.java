package mc.obliviate.bloksqliteapi;

import java.io.File;
import java.sql.*;

public class SQLHandler {

	private static Statement statement;
	private static Connection connection;
	private final String filePath;
	private final boolean debug;

	public SQLHandler(final String filePath) {
		this.filePath = filePath;
		debug = false;
	}

	public SQLHandler(final String filePath, boolean debug) {
		this.filePath = filePath;
		this.debug = debug;
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

	public static ResultSet sqlQuery(final String sql) {
		System.out.println(sql);
		try {
			final PreparedStatement pstmt = SQLHandler.getConnection().prepareStatement(sql);
			return pstmt.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new IllegalStateException("Result set can not be null! SQLite API");
	}

	public static void sqlUpdate(final String sql) {
		System.out.println(sql);
		try {
			final PreparedStatement pstmt = SQLHandler.getConnection().prepareStatement(sql);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
