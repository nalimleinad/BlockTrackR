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
package com.Volition21.BlockTrackR.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Properties;

import org.apache.commons.lang.ArrayUtils;

import com.Volition21.BlockTrackR.BlockTrackR;

public class BTRConfiguration {

	static Properties prop = new Properties();
	static OutputStream output = null;
	static File Dir = new File("config//BlockTrackR");
	static String ConfDir = "config//BlockTrackR//BlockTrackR.conf";

	/**
	 * Writes the provided configuration key and value to the config file.
	 * 
	 * @param key
	 *            The property identifier. EX: "authorized_players"
	 * @param value
	 *            The property's value. EX: "Notch"
	 * @param array
	 *            The property array to configure.
	 *            BlockTrackR.authorized_players;
	 */
	public int authorizeUser(String key, String value) {
		String[] array = BlockTrackR.authorized_players;
		int status = 0;
		try {
			FileInputStream in = new FileInputStream(ConfDir);
			Properties prop = new Properties();
			prop.load(in);
			in.close();

			FileOutputStream out = new FileOutputStream(ConfDir);
			if (array == null) {
				String[] a = {value};
				array = a;
				status = 1;
			} else {
				if (Arrays.asList(array).contains(value)) {
					array = (String[]) ArrayUtils.removeElement(array, value);
					status = 2;
				} else {
					array = (String[]) ArrayUtils.add(array, value);
					status = 1;
				}
			}
			prop.setProperty(key, Arrays.toString(array));
			prop.store(out, null);
			out.close();
			BlockTrackR.authorized_players = array;
		} catch (IOException e) {
			BlockTrackR.logger.info(e.toString());
			return 999;
		}
		return status;
	}

	/**
	 * Writes the provided configuration key and value to the config file.
	 * 
	 * @param key
	 *            The property identifier. EX: "debug"
	 * @param value
	 *            The property's value. EX: "true"
	 */
	public void setConfigValue(String key, String value) {
		try {
			FileInputStream in = new FileInputStream(ConfDir);
			Properties prop = new Properties();
			prop.load(in);
			in.close();

			FileOutputStream out = new FileOutputStream(ConfDir);
			prop.setProperty(key, value);
			prop.store(out, null);
			out.close();
		} catch (IOException e) {
			BlockTrackR.logger.info(e.toString());
		}

	}

	/**
	 * Reads the configuration file and updates all the relevant variables.
	 * 
	 * @return Returns false if the configuration file could not be read OR did
	 *         not exist and was created.
	 */
	public static boolean readConfig() {

		Properties prop = new Properties();
		InputStream input = null;

		try {

			try {
				if (!Dir.exists()) {
					Dir.mkdir();
				}
				input = new FileInputStream(ConfDir);
			} catch (FileNotFoundException e) {
				createConfig();
				return false;
			}

			prop.load(input);

			String AP_Temp = prop.getProperty("authorized_players");
			if (!((AP_Temp.equals("")) || (AP_Temp.equals("[]")))) {
				BlockTrackR.authorized_players = AP_Temp.substring(1,
						AP_Temp.length() - 1).split(",");
			} else {
				BlockTrackR.authorized_players = null;
			}
			BlockTrackR.version_check = prop.getProperty("version_check");
			BlockTrackR.debug = prop.getProperty("debug");
			BlockTrackR.host = prop.getProperty("host");
			BlockTrackR.port = prop.getProperty("port");
			BlockTrackR.connector = prop.getProperty("connector");
			BlockTrackR.database = prop.getProperty("database");
			BlockTrackR.dbuser = prop.getProperty("dbuser");
			BlockTrackR.dbpass = prop.getProperty("dbpass");

		} catch (IOException ex) {
			BlockTrackR.logger.warn("Disabled! Configuration error.", ex);
			;
		}
		try {
			input.close();
		} catch (IOException e) {
			BlockTrackR.logger.warn("Disabled! Configuration error.", e);
			return false;
		}
		return true;
	}

	/**
	 * Creates the configuration file with default values and informs the end
	 * user a restart is necessary.
	 */
	public static void createConfig() {
		try {

			output = new FileOutputStream(ConfDir);

			prop.setProperty("authorized_players", "[]");
			prop.setProperty("version_check", "true");
			prop.setProperty("debug", "false");
			prop.setProperty("host", "localhost");
			prop.setProperty("port", "3306");
			prop.setProperty("connector",
					"com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
			prop.setProperty("database", "minecraft");
			prop.setProperty("dbuser", "db_username");
			prop.setProperty("dbpass", "db_pasword");

			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
					BlockTrackR.logger
							.warn("Configuration file created. Please edit and restart server");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

}
