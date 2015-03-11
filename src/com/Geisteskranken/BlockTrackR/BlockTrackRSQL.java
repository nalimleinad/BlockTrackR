package com.Geisteskranken.BlockTrackR;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//TODO
//Need to resdesign table to allow for different events
//BlockBreak
//BlockPlace
//TODO
//SQL Connection Pooling.
public class BlockTrackRSQL {

	Properties prop = new Properties();
	OutputStream output = null;

	public synchronized static Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			BlockTrackR.logger.log(Level.WARNING, "Disabled");
			BlockTrackR.logger
					.log(Level.WARNING, "mySQL dependencies error", e);
		}
		try {
			conn = DriverManager.getConnection("jdbc:mysql://"
					+ BlockTrackR.host, BlockTrackR.dbuser,
					BlockTrackR.dbpass);
		} catch (SQLException err) {
			BlockTrackR.logger.log(Level.WARNING, "Disabled");
			BlockTrackR.logger
					.log(Level.WARNING, "mySQL connection error", err);
		}
		return conn;
	}

	// Returns true if DB exists or if it has been created.
	// Returns false if error.
	// Server should continue to run.
	public static boolean checkDB() {
		Connection connection = null;
		Statement statement = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			String sql = "CREATE DATABASE " + BlockTrackR.database + ";";
			statement.executeUpdate(sql);
		} catch (SQLException sqlException) {
			if (sqlException.getErrorCode() == 1007) {
				// Database already exists error
				closeStatement(statement);
				closeConnection(connection);
				BlockTrackR.logger.info("Database: OK");
				return true;
			} else {
				BlockTrackR.logger.log(Level.WARNING, "BlockTrackR Disabled!",
						sqlException);
				closeStatement(statement);
				closeConnection(connection);
				return false;
			}
		}
		// Database created
		BlockTrackR.logger.info("Database: OK");
		closeStatement(statement);
		closeConnection(connection);
		return true;
	}

	// Returns true if Table exists or if it has been created.
	// Server should continue to run.
	public static boolean checkTable() {
		Connection connection = null;
		Statement statement = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			String sql = "USE " + BlockTrackR.database + ";";
			statement.execute(sql);
			// Create Table
			String createTable = "CREATE TABLE IF NOT EXISTS `" + BlockTrackR.database + "`.`blockbreaks` ("
					+ "`UID` INT NOT NULL AUTO_INCREMENT, "
					+ "`player` VARCHAR(45) NOT NULL, "
					+ "`x` VARCHAR(45) NOT NULL, "
					+ "`y` VARCHAR(45) NOT NULL, "
					+ "`z` VARCHAR(45) NOT NULL, "
					+ "`time` VARCHAR(45) NOT NULL, "
					+ "`block` VARCHAR(255) NOT NULL, "
					+ "`event` VARCHAR(45) NOT NULL, "
					+ "PRIMARY KEY (`UID`));";
			statement.execute(createTable);
		} catch (SQLException e) {
			BlockTrackR.logger.log(Level.WARNING, "Disabled!");
			BlockTrackR.logger.log(Level.WARNING, "mySQL table related error",
					e);
			closeConnection(connection);
			closeStatement(statement);
			return false;
		}
		// Table exists
		closeConnection(connection);
		closeStatement(statement);
		BlockTrackR.logger.info("Tables OK");
		return true;
	}

	public static boolean insertBlockBreak(String player, int x, int y, int z,
			String time, String block) {
		Connection connection = null;
		Statement statement = null;
		String event = "BlockBreak";
		try {
			connection = getConnection();
			statement = connection.createStatement();
			String SelectDB = "USE " + BlockTrackR.database + ";";
			String Insert = "INSERT INTO `blockbreaks` (`player`, `x`, `y`, `z`, `time`, `block`, `event`) VALUES ('"
					+ player
					+ "', '"
					+ x
					+ "', '"
					+ y
					+ "', '"
					+ z
					+ "', '"
					+ time + "', '" + block + "', '" + event + "'" + ")" + ";";
			statement.execute(SelectDB);
			statement.execute(Insert);
		} catch (SQLException sqlException) {
			BlockTrackR.logger.log(Level.WARNING, "BlockTrackR Disabled!",
					sqlException);
			closeStatement(statement);
			closeConnection(connection);
			return false;
		}
		closeStatement(statement);
		closeConnection(connection);
		return true;
	}

	public static boolean insertBlockPlace(String player, int x, int y, int z,
			String time, String block) {
		Connection connection = null;
		Statement statement = null;
		String event = "BlockPlace";
		try {
			connection = getConnection();
			statement = connection.createStatement();
			String SelectDB = "USE " + BlockTrackR.database + ";";
			String Insert = "INSERT INTO `blockbreaks` (`player`, `x`, `y`, `z`, `time`, `block`, `event`) VALUES ('"
					+ player
					+ "', '"
					+ x
					+ "', '"
					+ y
					+ "', '"
					+ z
					+ "', '"
					+ time + "', '" + block + "', '" + event + "'" + ")" + ";";
			statement.execute(SelectDB);
			statement.execute(Insert);
		} catch (SQLException sqlException) {
			BlockTrackR.logger.log(Level.WARNING, "BlockTrackR Disabled!",
					sqlException);
			closeStatement(statement);
			closeConnection(connection);
			return false;
		}
		closeStatement(statement);
		closeConnection(connection);
		return true;
	}

	public static List<String> getBlockRecord(int X, int Y, int Z, String event) {

		Connection connection = null;
		Statement statement = null;
		ResultSet rs;

		try {
			connection = getConnection();
			statement = connection.createStatement();
			String SelectDB = "USE " + BlockTrackR.database + ";";
			String Fetch = "SELECT * FROM `blockbreaks` WHERE `x`='" + X
					+ "' AND `y`='" + Y + "' AND `z`='" + Z + "';";
			statement.execute(SelectDB);
			rs = statement.executeQuery(Fetch);

			ArrayList<String> list = new ArrayList<String>();
			while (rs.next()) {
				list.add(rs.getString("player"));

				String[] result = new String[list.size()];
				result = list.toArray(result);

				for (int i = 0; i < result.length; i++) {
					//Return this to command
					//Return this to tool.
					BlockTrackR.logger.info(result[i]);
				}
			}

		} catch (SQLException ex) {
			BlockTrackR.logger.log(Level.WARNING, ex.toString());
		}

		closeConnection(connection);
		closeStatement(statement);
		return null;

	}

	public static void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			BlockTrackR.logger.log(Level.WARNING,
					"mySQL error: Could not close connection", e);
		}
	}

	public static void closeStatement(Statement statement) {
		try {
			statement.close();
		} catch (SQLException e) {
			BlockTrackR.logger.log(Level.WARNING,
					"mySQL error: Could not close statement", e);
		}
	}

}