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
package com.Geisteskranken.BlockTrackR;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;

public class BTRConfiguration {

	static Properties prop = new Properties();
	static OutputStream output = null;
	static File Dir = new File("plugins//BlockTrackR");
	static String ConfDir = "plugins//BlockTrackR//BlockTrackR.conf";

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

			BlockTrackR.debug = prop.getProperty("debug");

			BlockTrackR.host = prop.getProperty("host");
			BlockTrackR.port = prop.getProperty("port");
			BlockTrackR.connector = prop.getProperty("connector");
			BlockTrackR.database = prop.getProperty("database");
			BlockTrackR.dbuser = prop.getProperty("dbuser");
			BlockTrackR.dbpass = prop.getProperty("dbpass");

		} catch (IOException ex) {
			BlockTrackR.logger.log(Level.WARNING,
					"Disabled! Configuration error.", ex);
		}
		try {
			input.close();
		} catch (IOException e) {
			BlockTrackR.logger.log(Level.WARNING,
					"Disabled! Configuration error.", e);
			return false;
		}
		BlockTrackR.logger.info("Config: OK");
		return true;
	}

	public static void createConfig() {
		try {

			output = new FileOutputStream(ConfDir);

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
							.log(Level.WARNING,
									"Configuration file created. Please edit and restart server");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

}
