package com.Geisteskranken.BlockTrackR;

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
	
	public static BlockTrackR P;
	public static Logger logger;
	public static Boolean Track = true;
	public static String debug = "false";
	
	public static String host;
	public static String database;
	public static String dbuser;
	public static String dbpass;

	@Override
	public void onEnable() {

		P = this;

		logger = Bukkit.getLogger();
		

		// CommandExecutor TWCommand = new TWCommand();
		// getCommand("trustworthy").setExecutor(TWCommand);
		// getCommand("tw").setExecutor(TWCommand);

		//Event Listeners.
		getServer().getPluginManager().registerEvents(new BTRBlockBreakEvent(), this);
		getServer().getPluginManager().registerEvents(new BTRBlockPlaceEvent(), this);
		getServer().getPluginManager().registerEvents(new BTRAsyncPlayerChatEvent(), this);

		logger.info("BlockTracker 1.0");
		logger.info("Server: v1.7");
		logger.info("Checking Config...");
		if (Configuration.readConfig()) {
			if (BlockTrackRSQL.checkDB()) {
				logger.info("Checking SQL Table...");
				if (BlockTrackRSQL.checkTable()) {
					logger.info("Everything appears OK");
					logger.info("Debugging: " + debug);
					logger.info("Enabled!");
					Track = true;
				} else {
					Track = false;
				}
			} else {
				Track = false;
			}
		} else {
			Track = false;
		}
	}

	@Override
	public void onDisable() {
		BlockTrackRExecutorService.ThreadPool.shutdown();
	}

	public static String getTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
