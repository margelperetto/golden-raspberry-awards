package br.com.margel.goldenraspberryawards.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Db {
	private static Db instance;
	
	public static synchronized Db getInstance() throws SQLException {
		if(instance == null) {
			instance = new Db();
		}
		return instance;
	}

	private Db() throws SQLException {
		createTables();
	}
	
	public Connection newConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:h2:mem:movies;DB_CLOSE_DELAY=-1");
	}

	private void createTables() throws SQLException {
		try(
				Connection conn = newConnection();
				PreparedStatement ppst = conn.prepareStatement(CREATE_TABLES);
				){
			ppst.execute();
		}
	}

	private static final String CREATE_TABLES = 
			"CREATE TABLE IF NOT EXISTS MOVIES("
					+ "	ID BIGINT AUTO_INCREMENT,"
					+ "	YEAR INT,"
					+ "	TITLE VARCHAR,"
					+ "	STUDIOS VARCHAR,"
					+ "	PRODUCERS VARCHAR,"
					+ "	WINNER BOOLEAN"
					+ ");";
}
