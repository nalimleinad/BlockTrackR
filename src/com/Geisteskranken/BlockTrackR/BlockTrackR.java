package com.Geisteskranken.BlockTrackR;

import static com.Geisteskranken.BlockTrackR.Configuration.CONF;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class BlockTrackR extends JavaPlugin {

	public static String Name = "§d[BlockTrackR] ";

	public static BlockTrackR P;

	public static Logger logger;

	public static Boolean Track = true;

	@Override
	public void onEnable() {

		P = this;

		logger = Bukkit.getLogger();

		saveDefaultConfig(); // Creates default config. Fails silently if
								// already exists.

		FileConfiguration savedConfig = getConfig();
		CONF.readConfig(savedConfig);

		// CommandExecutor TWCommand = new TWCommand();
		// getCommand("trustworthy").setExecutor(TWCommand);
		// getCommand("tw").setExecutor(TWCommand);

		getServer().getPluginManager().registerEvents(new BTEvent(), this);

		logger.info("BlockTracker 1.0");
		logger.info("Server: v1.7");
		logger.info("Checking Config...");
		//if (BlockTrackRSQL.checkDB()) {
			logger.info("Checking SQL Table...");
			//if (BlockTrackRSQL.checkTable()) {
				logger.info("Everything appears OK");
				logger.info("Enabled!");
				//Track = true;
			//} else {
				//Track = false;
			}
		//} else {
			//Track = false;
		//}
	//}

	@Override
	public void onDisable() {

	}
	
	public static String getTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
