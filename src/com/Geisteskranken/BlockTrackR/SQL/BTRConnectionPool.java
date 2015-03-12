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

import com.Geisteskranken.BlockTrackR.BlockTrackR;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class BTRConnectionPool {

	private static BTRConnectionPool instance = null;
	private static HikariDataSource ds = null;

	static {
		try {
			instance = new BTRConnectionPool();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	private BTRConnectionPool() {
		HikariConfig config = new HikariConfig();
		config.setMaximumPoolSize(10);
		config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
		config.setJdbcUrl("jdbc:mysql://" + BlockTrackR.host + ":"
				+ BlockTrackR.port);
		config.setUsername(BlockTrackR.dbuser);
		config.setPassword(BlockTrackR.dbpass);

		ds = new HikariDataSource(config);
	}

	public static BTRConnectionPool getInstance() {
		return instance;
	}

	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

}
