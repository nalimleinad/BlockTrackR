/**
 * Copyright (C) 2015 Geistes
 * Geistes@hotmail.com
 *
 * Licensed under The MIT License (the "License");
 * you may not use this file except in compliance with the License.
 *
 * The MIT License (MIT)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.Geisteskranken.BlockTrackR.SQL;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.Geisteskranken.BlockTrackR.BlockTrackR;

/**
 * BTRSQL
 * 
 * Contains all callable methods to handle SQL functions. All methods fetch SQL
 * connections from the Hikari Connection Pool via method openConnection.
 * 
 **/
public class BTRSQL {

	/**
	 * CheckDB
	 * 
	 * Will return true if DB exists or has been created.
	 * 
	 **/
	public static boolean checkDB() {
		Connection connection = null;
		Statement statement = null;
		try {
			connection = openConnection(connection);
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

	/**
	 * CheckTables
	 * 
	 * Will return true if the table exists or has been created.
	 * 
	 **/
	public static boolean checkTable() {
		Connection connection = null;
		Statement statement = null;
		try {
			connection = openConnection(connection);
			statement = connection.createStatement();
			String sql = "USE " + BlockTrackR.database + ";";
			statement.execute(sql);
			// Create Table
			String createTable = "CREATE TABLE IF NOT EXISTS `"
					+ BlockTrackR.database + "`.`blocktrackr` ("
					+ "`UID` INT NOT NULL AUTO_INCREMENT, "
					+ "`player` VARCHAR(45) NOT NULL, "
					+ "`UUID` VARCHAR(60) NOT NULL, "
					+ "`x` VARCHAR(45) NOT NULL, "
					+ "`y` VARCHAR(45) NOT NULL, "
					+ "`z` VARCHAR(45) NOT NULL, "
					+ "`time` VARCHAR(45) NOT NULL, "
					+ "`content` VARCHAR(255) NOT NULL, "
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

	/**
	 * insertBlockBreak
	 * 
	 * Called on BlockBreakEvents to parse the data to the SQL server.
	 * 
	 **/
	public static boolean insertBlockBreak(String player, String UUID, int x,
			int y, int z, String time, String block) {
		Connection connection = null;
		Statement statement = null;
		String event = "BlockBreak";
		try {
			connection = openConnection(connection);
			statement = connection.createStatement();
			String SelectDB = "USE " + BlockTrackR.database + ";";
			String Insert = "INSERT INTO `blocktrackr` (`player`, `UUID`, `x`, `y`, `z`, `time`, `content`, `event`) VALUES ('"
					+ player
					+ "', '"
					+ UUID
					+ "', '"
					+ x
					+ "', '"
					+ y
					+ "', '"
					+ z
					+ "', '"
					+ time
					+ "', '"
					+ block
					+ "', '"
					+ event
					+ "'"
					+ ")" + ";";
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

	/**
	 * insertBlockPlace
	 * 
	 * Called on BlockPlaceEvents to parse the data to the SQL server.
	 * 
	 **/
	public static boolean insertBlockPlace(String player, String UUID, int x,
			int y, int z, String time, String block) {
		Connection connection = null;
		Statement statement = null;
		String event = "BlockPlace";
		try {
			connection = openConnection(connection);
			statement = connection.createStatement();
			String SelectDB = "USE " + BlockTrackR.database + ";";
			String Insert = "INSERT INTO `blocktrackr` (`player`, `UUID`, `x`, `y`, `z`, `time`, `content`, `event`) VALUES ('"
					+ player
					+ "', '"
					+ UUID
					+ "', '"
					+ x
					+ "', '"
					+ y
					+ "', '"
					+ z
					+ "', '"
					+ time
					+ "', '"
					+ block
					+ "', '"
					+ event
					+ "'"
					+ ")" + ";";
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

	/**
	 * insertPlayerChat
	 * 
	 * Called on AsyncPlayerChatEvents to parse the data to the SQL server.
	 * 
	 **/
	public static boolean insertPlayerChat(String player, String UUID, int x,
			int y, int z, String time, String MSG) {
		Connection connection = null;
		Statement statement = null;
		String event = "PlayerChat";
		try {
			connection = openConnection(connection);
			statement = connection.createStatement();
			String SelectDB = "USE " + BlockTrackR.database + ";";
			String Insert = "INSERT INTO `blocktrackr` (`player`, `UUID`, `x`, `y`, `z`, `time`, `content`, `event`) VALUES ('"
					+ player
					+ "', '"
					+ UUID
					+ "', '"
					+ x
					+ "', '"
					+ y
					+ "', '"
					+ z
					+ "', '"
					+ time
					+ "', '"
					+ MSG
					+ "', '"
					+ event
					+ "'"
					+ ")" + ";";
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

	/**
	 * getBlockRecord
	 * 
	 * Fetches edits to provided coordinates.
	 * 
	 **/
	//TODO
	public static List<String> getBlockRecord(int X, int Y, int Z, String event) {

		Connection connection = null;
		Statement statement = null;
		ResultSet rs;

		try {
			connection = openConnection(connection);
			statement = connection.createStatement();
			String SelectDB = "USE " + BlockTrackR.database + ";";
			String Fetch = "SELECT * FROM `blocktrackr` WHERE `x`='" + X
					+ "' AND `y`='" + Y + "' AND `z`='" + Z + "';";
			statement.execute(SelectDB);
			rs = statement.executeQuery(Fetch);

			ArrayList<String> list = new ArrayList<String>();
			while (rs.next()) {
				list.add(rs.getString("player"));

				String[] result = new String[list.size()];
				result = list.toArray(result);

				for (int i = 0; i < result.length; i++) {
					// Return this to command
					// Return this to tool.
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

	/**
	 * openConnection
	 * 
	 * Returns a connection fetched from HikariCP
	 * 
	 **/
	public static Connection openConnection(Connection connection) {
		try {
			connection = BTRConnectionPool.getConnection();
		} catch (SQLException e) {
			BlockTrackR.logger.log(Level.WARNING,
					"mySQL error: Could not close connection", e);
		}
		return connection;
	}

	/**
	 * closeConnection
	 * 
	 * Closes a connection.
	 * 
	 **/
	public static void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			BlockTrackR.logger.log(Level.WARNING,
					"mySQL error: Could not close connection", e);
		}
	}

	/**
	 * closeStatement
	 * 
	 * Closes a statement.
	 * 
	 **/
	public static void closeStatement(Statement statement) {
		try {
			statement.close();
		} catch (SQLException e) {
			BlockTrackR.logger.log(Level.WARNING,
					"mySQL error: Could not close statement", e);
		}
	}

	/**
	 * This method has now been deprecated in favor of HikariCP.
	 * 
	 * See BTRConnectionPool.
	 * 
	 **/
	/*
	 * public synchronized static Connection getConnection() throws SQLException
	 * { Connection conn = null; try { Class.forName("com.mysql.jdbc.Driver"); }
	 * catch (ClassNotFoundException e) { BlockTrackR.logger.log(Level.WARNING,
	 * "Disabled"); BlockTrackR.logger .log(Level.WARNING,
	 * "mySQL dependencies error", e); } try { conn =
	 * DriverManager.getConnection("jdbc:mysql://" + BlockTrackR.host + ":" +
	 * BlockTrackR.port, BlockTrackR.dbuser, BlockTrackR.dbpass); } catch
	 * (SQLException err) { BlockTrackR.logger.log(Level.WARNING, "Disabled");
	 * BlockTrackR.logger .log(Level.WARNING, "mySQL connection error", err); }
	 * return conn; }
	 */

}