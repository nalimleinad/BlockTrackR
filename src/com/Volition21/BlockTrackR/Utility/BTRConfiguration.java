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
import java.util.Properties;

import com.Volition21.BlockTrackR.BlockTrackR;

public class BTRConfiguration {

	static Properties prop = new Properties();
	static OutputStream output = null;
	static File Dir = new File("config//BlockTrackR");
	static String ConfDir = "config//BlockTrackR//BlockTrackR.conf";

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

			BlockTrackR.debug = prop.getProperty("version_check");
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

	public static void createConfig() {
		try {

			output = new FileOutputStream(ConfDir);

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
