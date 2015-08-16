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

import com.Volition21.BlockTrackR.BlockTrackR;
import com.Volition21.BlockTrackR.Utility.BTRConfiguration;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import org.spongepowered.api.service.sql.SqlService;

public class BTRConnectionPool {

	private static BTRConnectionPool instance = null;
	private static SqlService sql = null;
	private static String jdbcUrl;
	private final String dbuser;
    private final String dbpass;

	static {
		try {
			instance = new BTRConnectionPool();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	private BTRConnectionPool() {
		StringBuilder urlBuilder = new StringBuilder("jdbc:").append(BlockTrackR.dbtype.toLowerCase()).append("://");
		
		if (BlockTrackR.dbtype.toLowerCase().equals("mysql")) {
            this.dbuser = BlockTrackR.dbuser;
            this.dbpass = BlockTrackR.dbpass;
        } else {
            this.dbuser = "";
            this.dbpass = "";
        }
		
		switch (BlockTrackR.dbtype.toLowerCase()) {
        case "sqlite":
            urlBuilder.append(BlockTrackR.host
                    .replace("%DIR%", BTRConfiguration.Dir.getAbsolutePath()))
                    .append(File.separatorChar)
                    .append("database.db");
            break;
        case "mysql":
            urlBuilder.append(dbuser).append(':').append(dbpass).append("@")
                    .append(BlockTrackR.host)
                    .append(':')
                    .append(BlockTrackR.port)
                    .append('/')
                    .append(BlockTrackR.database);
            break;
        case "h2":
        default:
            urlBuilder.append(BlockTrackR.host
                    .replace("%DIR%", BTRConfiguration.Dir.getAbsolutePath()))
                    .append(File.separatorChar)
                    .append("database");
            break;
    }

    BTRConnectionPool.jdbcUrl = urlBuilder.toString();

	}

	public static BTRConnectionPool getInstance() {
		return instance;
	}

	public static Connection getConnection() throws SQLException {
        if (sql == null) {
            sql = BlockTrackR.game.getServiceManager().provide(SqlService.class).get();
        }

        return sql.getDataSource(jdbcUrl).getConnection();
    }

}
