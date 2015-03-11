package com.Geisteskranken.BlockTrackR;

import org.bukkit.configuration.file.FileConfiguration;

public enum Configuration {

	CONF;

	public static String dbHost = "127.0.0.1";
	public static String dbPort = "3306";
	public static String database = "minecraft";
	public static String dbUser = "db_username";
	public static String dbPass = "db_password";

	public void readConfig(FileConfiguration savedConfig) {

		Configuration.dbHost = savedConfig.getString(dbHost, "127.0.0.1");
		Configuration.dbPort = savedConfig.getString(dbPort, "3306");
		Configuration.database = savedConfig.getString(database, "minecraft");
		Configuration.dbUser = savedConfig.getString(dbUser, "db_username");
		Configuration.dbPass = savedConfig.getString(dbPass, "db_password");
		
	}

}
