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
		config.setDataSourceClassName(BlockTrackR.connector);
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
