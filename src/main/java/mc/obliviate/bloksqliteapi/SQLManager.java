package mc.obliviate.bloksqliteapi;

import mc.obliviate.bloksqliteapi.sqlutils.DataType;
import mc.obliviate.bloksqliteapi.sqlutils.SQLTable;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLManager {

	private static Statement statement;
	private static Connection connection;
	private final Plugin plugin;

	public SQLManager(final Plugin plugin) {
		this.plugin = plugin;
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
			final String URL = "jdbc:sqlite:" + plugin.getDataFolder().getAbsolutePath() + File.separator + databaseName + ".db";
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
	/**
	public SQLTable createTable() {

		//Create a table object
		//First param, specify your table name
		//Second param, specify name of field that you'll use instead of ID
		//Third param, add a field to your table.
		//You know. Fields has some properties, data type, not null, unique, primary key...
		//You can specify this properties too.
		final SQLTable sqlTable = new SQLTable("school3", "no")
				.addField("no", DataType.INTEGER, true, true, true)
				.addField("name", DataType.TEXT) //other params is false as default
				.addField("class", DataType.TEXT);

		//Create table
		//No No No. You don't need the long text. You already given what we
		//need so we can create table instead of you.
		//
		//just use sqlTable.create() and enjoy.
		//this method returns created table.
		return sqlTable.create();

	}
	public void test(SQLTable sqltable) {
		Bukkit.getLogger().info("Test starting...");
		final long startTime = System.currentTimeMillis();

		//Let's test our database.
		//firstly specify an id to make an example. i choose 306. (because that is my school number xd)

		int id = 306;

		//Check your table and ask 'is this id inserted before?'
		if (sqltable.exist(id)) {

			//if answer is yes, you must update.
			//your table already has another fields you need.
			//so, you can specify fields only that you want to change.
			sqltable.update(sqltable.createUpdate(id).putData("name", "ali"));
		} else {

			//if answer is no, you must insert.
			//your table does not have another fields you need.
			//so you must specify all fields.
			sqltable.insert(sqltable.createUpdate(id).putData("name", "hamza").putData("class", "13E").putData("no", id));
		}

		sqltable.delete(308);
		//sqltable.insertOrUpdate(sqltable.createUpdate(308).putData("name","mehmet").putData("class","12A").putData("no",308));

		final long endTime = System.currentTimeMillis();
		final long totalTime = endTime - startTime;
		Bukkit.getLogger().info("Test completed (" + totalTime + "ms)");
	}

    **/

}
