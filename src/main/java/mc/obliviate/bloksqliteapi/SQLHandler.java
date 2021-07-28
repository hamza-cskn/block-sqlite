package mc.obliviate.bloksqliteapi;

import java.io.File;
import java.sql.*;

public class SQLHandler {

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

	public static ResultSet sqlQuery(final String sql) {
		System.out.println("SQL EXECUTING: " + sql);

		ResultSet rs = null;
		try {
			final Statement statement = connection.createStatement();
			System.out.println("TRING: " + sql);
			rs = statement.executeQuery(sql);
			System.out.println("SUCCESS: " + sql);
		} catch (SQLException e) {
			e.printStackTrace();

		}
		if (rs != null)
			return rs;
		throw new IllegalStateException("Result set can not be null! SQLite API");
	}

	public static void sqlUpdate(final String sql) {
		System.out.println(sql);
		try {
			final PreparedStatement pstmt = SQLHandler.getConnection().prepareStatement(sql);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
