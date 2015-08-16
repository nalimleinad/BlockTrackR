/**
	BlockTrackR - Minecraft monitoring plugin designed to capture, index, and correlate real-time data in a searchable repository.
    Copyright (C) 2015 - Damion (Volition21) Volition21@Hackion.com

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.Volition21.BlockTrackR.SQL;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.Volition21.BlockTrackR.BlockTrackR;
import com.Volition21.BlockTrackR.Utility.BTRDebugger;

/**
 * BTRSQL
 * 
 * Contains all callable methods to handle SQL functions. All methods fetch SQL
 * connections from the Hikari Connection Pool via method openConnection.
 * 
 **/
public class BTRSQL {

	/**
	 * Checks if the DB exists or has been created.
	 * 
	 * @return True if DB exists or has been created, False if otherwise.
	 */
	public static boolean checkDB() {
		
		if (!BlockTrackR.dbtype.toLowerCase().equals("mysql")) return true;
		
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
				return true;
			} else {
				BlockTrackR.logger.warn("BlockTrackR Disabled!", sqlException);
				closeStatement(statement);
				closeConnection(connection);
				return false;
			}
		}
		// Database created
		closeStatement(statement);
		closeConnection(connection);
		return true;
	}

	/**
	 * Checks if the Table exists or has been created.
	 * 
	 * @return True if Table exists or has been created, False if otherwise.
	 */
	public static boolean checkTable() {
		Connection connection = null;
		Statement statement = null;
		try {
			connection = openConnection(connection);
			statement = connection.createStatement();
			
			if (BlockTrackR.dbtype.toLowerCase().equals("sqlite")) {
				// Create Table
				String createTable = "CREATE TABLE IF NOT EXISTS "
						+ "`blocktrackr` ("
						+ "`UID` INT NOT NULL, "
						+ "`player` VARCHAR(45) NOT NULL, "
						+ "`UUID` VARCHAR(60) NOT NULL, "
						+ "`x` VARCHAR(45) NOT NULL, "
						+ "`y` VARCHAR(45) NOT NULL, "
						+ "`z` VARCHAR(45) NOT NULL, "
						+ "`world` VARCHAR(60) NOT NULL, "
						+ "`time` VARCHAR(45) NOT NULL, "
						+ "`content` VARCHAR(255) NOT NULL, "
						+ "`event` VARCHAR(45) NOT NULL, "
						+ "`unix_time` int NOT NULL, " + "PRIMARY KEY (`UID`));";
				statement.execute(createTable);
			} else {
				// Create Table
				String createTable = "CREATE TABLE IF NOT EXISTS "
						+ "`blocktrackr` ("
						+ "`UID` INT NOT NULL AUTO_INCREMENT, "
						+ "`player` VARCHAR(45) NOT NULL, "
						+ "`UUID` VARCHAR(60) NOT NULL, "
						+ "`x` VARCHAR(45) NOT NULL, "
						+ "`y` VARCHAR(45) NOT NULL, "
						+ "`z` VARCHAR(45) NOT NULL, "
						+ "`world` VARCHAR(60) NOT NULL, "
						+ "`time` VARCHAR(45) NOT NULL, "
						+ "`content` VARCHAR(255) NOT NULL, "
						+ "`event` VARCHAR(45) NOT NULL, "
						+ "`unix_time` int NOT NULL, " + "PRIMARY KEY (`UID`));";
				statement.execute(createTable);
			}
		} catch (SQLException e) {
			BlockTrackR.logger.warn("Disabled!");
			BlockTrackR.logger.warn(BlockTrackR.dbtype.toLowerCase() + " table related error", e);
			closeConnection(connection);
			closeStatement(statement);
			return false;
		}
		// Table exists
		closeConnection(connection);
		closeStatement(statement);
		return true;
	}

	/**
	 * Inserts BlockBreakEvent data to the SQL server.
	 * 
	 * 
	 * @param player
	 *            The responsible player's name.
	 * @param UUID
	 *            The responsible player's Universal Unique Identifier.
	 * @param x
	 *            The X coordinate of the affected block.
	 * @param y
	 *            The Y coordinate of the affected block.
	 * @param z
	 *            The Z coordinate of the affected block.
	 * @param world
	 *            The world the affected block resides in.
	 * @param time
	 *            The time this action took place.
	 * @param block
	 *            The name of the blocktype that was affected.
	 * @return True on successful insert.
	 */
	public static boolean insertBlockBreak(String player, String UUID, int x,
			int y, int z, String world, String time, String block) {
		Connection connection = null;
		Statement statement = null;
		String event = "BlockBreak";
		try {
			connection = openConnection(connection);
			statement = connection.createStatement();
			String Insert = "INSERT INTO `blocktrackr` (`player`, `UUID`, `x`, `y`, `z`, `world`, `time`, `content`, `event`, `unix_time`) VALUES ('"
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
					+ world
					+ "', '"
					+ time
					+ "', '"
					+ block
					+ "', '"
					+ event
					+ "', "
					+ "UNIX_TIMESTAMP(NOW())"
					+ ")"
					+ ";";
			statement.execute(Insert);
		} catch (SQLException sqlException) {
			BlockTrackR.logger.warn("BlockTrackR Disabled!", sqlException);
			closeStatement(statement);
			closeConnection(connection);
			return false;
		}
		closeStatement(statement);
		closeConnection(connection);
		return true;
	}

	/**
	 * Inserts BlockPlaceEvent data to the SQL server.
	 * 
	 * 
	 * @param player
	 *            The responsible player's name.
	 * @param UUID
	 *            The responsible player's Universal Unique Identifier.
	 * @param x
	 *            The X coordinate of the affected block.
	 * @param y
	 *            The Y coordinate of the affected block.
	 * @param z
	 *            The Z coordinate of the affected block.
	 * @param world
	 *            The world the affected block resides in.
	 * @param time
	 *            The time this action took place.
	 * @param block
	 *            The name of the blocktype that was affected.
	 * @return True on successful insert.
	 */
	public static boolean insertBlockPlace(String player, String UUID, int x,
			int y, int z, String world, String time, String block) {
		Connection connection = null;
		Statement statement = null;
		String event = "BlockPlace";
		try {
			connection = openConnection(connection);
			statement = connection.createStatement();
			String Insert = "INSERT INTO `blocktrackr` (`player`, `UUID`, `x`, `y`, `z`, `world`, `time`, `content`, `event`, `unix_time`) VALUES ('"
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
					+ world
					+ "', '"
					+ time
					+ "', '"
					+ block
					+ "', '"
					+ event
					+ "', "
					+ "UNIX_TIMESTAMP(NOW())"
					+ ")"
					+ ";";
			statement.execute(Insert);
		} catch (SQLException sqlException) {
			BlockTrackR.logger.warn("BlockTrackR Disabled!", sqlException);
			closeStatement(statement);
			closeConnection(connection);
			return false;
		}
		closeStatement(statement);
		closeConnection(connection);
		return true;
	}

	/**
	 * Inserts AsyncPlayerChatEvent data to the SQL server.
	 * 
	 * 
	 * @param player
	 *            The responsible player's name.
	 * @param UUID
	 *            The responsible player's Universal Unique Identifier.
	 * @param x
	 *            The X coordinate of the responsible player.
	 * @param y
	 *            The Y coordinate of the responsible player.
	 * @param z
	 *            The Z coordinate of the responsible player.
	 * @param world
	 *            The world the responsible player resides in.
	 * @param time
	 *            The time this action took place.
	 * @param MSG
	 *            The message itself.
	 * @return True on successful insert.
	 */
	public static boolean insertPlayerChat(String player, String UUID, int x,
			int y, int z, String world, String time, String MSG) {
		Connection connection = null;
		Statement statement = null;
		PreparedStatement stmt = null;
		String event = "PlayerChat";
		try {
			connection = openConnection(connection);
			statement = connection.createStatement();
			stmt = connection
					.prepareStatement("INSERT INTO `blocktrackr` (`player`, `UUID`, `x`, `y`, `z`, `world`, `time`, `content`, `event`, `unix_time`) VALUES ('"
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
							+ world
							+ "', '"
							+ time
							+ "', ?, '"
							+ event
							+ "', "
							+ "UNIX_TIMESTAMP(NOW())" + ")" + ";");
			stmt.setString(1, MSG);
			stmt.executeUpdate();
		} catch (SQLException sqlException) {
			BlockTrackR.logger.warn("BlockTrackR Disabled!", sqlException);
			closeStatement(statement);
			closeConnection(connection);
			return false;
		}
		closeStatement(statement);
		closeConnection(connection);
		return true;
	}

	/**
	 * insertDropItem
	 * 
	 * Called on PlayerDropItemEvent to parse the data to the SQL server.
	 * 
	 **/
	public static boolean insertDropItem(String player, String UUID, int x,
			int y, int z, String world, String time, String ItemType) {
		Connection connection = null;
		Statement statement = null;
		String event = "DropItem";
		try {
			connection = openConnection(connection);
			statement = connection.createStatement();
			String Insert = "INSERT INTO `blocktrackr` (`player`, `UUID`, `x`, `y`, `z`, `world`, `time`, `content`, `event`, `unix_time`) VALUES ('"
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
					+ world
					+ "', '"
					+ time
					+ "', '"
					+ ItemType
					+ "', '"
					+ event
					+ "', "
					+ "UNIX_TIMESTAMP(NOW())"
					+ ")"
					+ ";";
			statement.execute(Insert);
		} catch (SQLException sqlException) {
			BlockTrackR.logger.warn("BlockTrackR Disabled!", sqlException);
			closeStatement(statement);
			closeConnection(connection);
			return false;
		}
		closeStatement(statement);
		closeConnection(connection);
		return true;
	}

	/**
	 * insertPickupItem
	 * 
	 * Called on BTRPlayerPickupItemEvent to parse the data to the SQL server.
	 * 
	 **/
	public static boolean insertPickupItem(String player, String UUID, int x,
			int y, int z, String world, String time, String ItemType) {
		Connection connection = null;
		Statement statement = null;
		String event = "PickupItem";
		try {
			connection = openConnection(connection);
			statement = connection.createStatement();
			String Insert = "INSERT INTO `blocktrackr` (`player`, `UUID`, `x`, `y`, `z`, `world`, `time`, `content`, `event`, `unix_time`) VALUES ('"
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
					+ world
					+ "', '"
					+ time
					+ "', '"
					+ ItemType
					+ "', '"
					+ event
					+ "', "
					+ "UNIX_TIMESTAMP(NOW())"
					+ ")"
					+ ";";
			statement.execute(Insert);
		} catch (SQLException sqlException) {
			BlockTrackR.logger.warn("BlockTrackR Disabled!", sqlException);
			closeStatement(statement);
			closeConnection(connection);
			return false;
		}
		closeStatement(statement);
		closeConnection(connection);
		return true;
	}

	/**
	 * insertPlayerLogin
	 * 
	 * Called on PlayerLoginEvent to parse the data to the SQL server.
	 * 
	 **/
	public static boolean insertPlayerLogin(String player, String UUID, int x,
			int y, int z, String world, String time, String IP) {
		Connection connection = null;
		Statement statement = null;
		String event = "PlayerLogin";
		try {
			connection = openConnection(connection);
			statement = connection.createStatement();
			String Insert = "INSERT INTO `blocktrackr` (`player`, `UUID`, `x`, `y`, `z`, `world`, `time`, `content`, `event`, `unix_time`) VALUES ('"
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
					+ world
					+ "', '"
					+ time
					+ "', '"
					+ IP
					+ "', '"
					+ event + "', " + "UNIX_TIMESTAMP(NOW())" + ")" + ";";
			statement.execute(Insert);
		} catch (SQLException sqlException) {
			BlockTrackR.logger.warn("BlockTrackR Disabled!", sqlException);
			closeStatement(statement);
			closeConnection(connection);
			return false;
		}
		closeStatement(statement);
		closeConnection(connection);
		return true;
	}

	/**
	 * insertPlayerQuit
	 * 
	 * Called on PlayerQuitEvent to parse the data to the SQL server.
	 * 
	 **/
	public static boolean insertPlayerQuit(String player, String UUID, int x,
			int y, int z, String world, String time, String Name) {
		Connection connection = null;
		Statement statement = null;
		String event = "PlayerQuit";
		try {
			connection = openConnection(connection);
			statement = connection.createStatement();
			String Insert = "INSERT INTO `blocktrackr` (`player`, `UUID`, `x`, `y`, `z`, `world`, `time`, `content`, `event`, `unix_time`) VALUES ('"
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
					+ world
					+ "', '"
					+ time
					+ "', '"
					+ Name
					+ "', '"
					+ event
					+ "', "
					+ "UNIX_TIMESTAMP(NOW())"
					+ ")"
					+ ";";
			statement.execute(Insert);
		} catch (SQLException sqlException) {
			BlockTrackR.logger.warn("BlockTrackR Disabled!", sqlException);
			closeStatement(statement);
			closeConnection(connection);
			return false;
		}
		closeStatement(statement);
		closeConnection(connection);
		return true;
	}

	/**
	 * Inserts PlayerInteractBlockEvent data to the SQL server.
	 * 
	 * 
	 * @param player
	 *            The responsible player's name.
	 * @param UUID
	 *            The responsible player's Universal Unique Identifier.
	 * @param x
	 *            The X coordinate of the affected block.
	 * @param y
	 *            The Y coordinate of the affected block.
	 * @param z
	 *            The Z coordinate of the affected block.
	 * @param world
	 *            The world the affected block resides in.
	 * @param time
	 *            The time this action took place.
	 * @param InteractionType
	 *            The name of the InteractionType.
	 * @return True on successful insert.
	 */
	public static boolean insertPlayerInteract(String player, String UUID,
			int x, int y, int z, String world, String time,
			String InteractionType) {
		Connection connection = null;
		Statement statement = null;
		String event = "PlayerInteract";
		try {
			connection = openConnection(connection);
			statement = connection.createStatement();
			String Insert = "INSERT INTO `blocktrackr` (`player`, `UUID`, `x`, `y`, `z`, `world`, `time`, `content`, `event`, `unix_time`) VALUES ('"
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
					+ world
					+ "', '"
					+ time
					+ "', '"
					+ InteractionType
					+ "', '"
					+ event
					+ "', "
					+ "UNIX_TIMESTAMP(NOW())" + ")" + ";";
			statement.execute(Insert);
		} catch (SQLException sqlException) {
			BlockTrackR.logger.warn("BlockTrackR Disabled!", sqlException);
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
	public List<String> getBlockRecord(String X, String Y, String Z) {

		Connection connection = null;
		Statement statement = null;
		ResultSet rs;
		ArrayList<String> list = new ArrayList<String>();

		try {
			connection = openConnection(connection);
			statement = connection.createStatement();
			String Fetch = "SELECT * FROM `blocktrackr` WHERE `x`='" + X
					+ "' AND `y`='" + Y + "' AND `z`='" + Z + "';";
			BTRDebugger.DLog(Fetch);
			rs = statement.executeQuery(Fetch);

			while (rs.next()) {
				list.add(rs.getString("player") + " : "
						+ rs.getString("content") + " : "
						+ rs.getString("event") + " : " + rs.getString("time"));
			}

			String[] result = new String[list.size()];
			result = list.toArray(result);

			if (!(rs.last())) {
				BTRDebugger.DLog("getBlockRecord - No Rows");
			} else {
				BTRDebugger.DLog("getBlockRecord - Rows were found!");
			}

		} catch (SQLException ex) {
			BlockTrackR.logger.warn(ex.toString());
		}

		closeConnection(connection);
		closeStatement(statement);
		return list;

	}

	/**
	 * Deletes all SQL records <= the unix timestamp parsed in 'age'.
	 * 
	 * @param age
	 *            The unix timestamp to delete from.
	 */
	public void delRecords(Long age) {

		Connection connection = null;
		Statement statement = null;

		try {
			connection = openConnection(connection);
			statement = connection.createStatement();
			
			if (BlockTrackR.dbtype.toLowerCase().equals("mysql")) {
				String DisableSafe = "SET SQL_SAFE_UPDATES = 0;";
				String Delete = "delete from blocktrackr where unix_time <= '"
						+ age + "' ;";
				String EnableSafe = "SET SQL_SAFE_UPDATES = 1;";
				statement.execute(DisableSafe);
				statement.execute(Delete);
				BTRDebugger.DLog(Delete);
				statement.execute(EnableSafe);
			} else {
				String Delete = "delete from blocktrackr where unix_time <= '"
						+ age + "' ;";
				statement.execute(Delete);
				BTRDebugger.DLog(Delete);
			}

		} catch (SQLException ex) {
			BlockTrackR.logger.warn(ex.toString());
		}

		closeConnection(connection);
		closeStatement(statement);
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
			BlockTrackR.logger.warn(BlockTrackR.dbtype.toLowerCase() + " error: Could not open connection.",
					e);
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
			BlockTrackR.logger.warn(BlockTrackR.dbtype.toLowerCase() + " error: Could not close connection",
					e);
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
			BlockTrackR.logger
					.warn(BlockTrackR.dbtype.toLowerCase() + " error: Could not close statement", e);
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