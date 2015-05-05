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
package com.Geisteskranken.BlockTrackR;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.Geisteskranken.BlockTrackR.Event.*;
import com.Geisteskranken.BlockTrackR.SQL.BTRSQL;

@SuppressWarnings("unused")
public class BlockTrackR extends JavaPlugin {

	public static BlockTrackR P;
	public static Logger logger;
	public static Boolean Track = true;
	public static String debug = "false";

	public static String host;
	public static String port;
	public static String connector;
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

		// Event Listeners.
		getServer().getPluginManager().registerEvents(new BTRBlockBreakEvent(),
				this);
		getServer().getPluginManager().registerEvents(new BTRBlockPlaceEvent(),
				this);
		getServer().getPluginManager().registerEvents(
				new BTRAsyncPlayerChatEvent(), this);
		getServer().getPluginManager().registerEvents(
				new BTRPlayerDropItemEvent(), this);
		getServer().getPluginManager().registerEvents(
				new BTRPlayerPickupItemEvent(), this);
		getServer().getPluginManager().registerEvents(
				new BTRPlayerLoginEvent(), this);
		getServer().getPluginManager().registerEvents(new BTRPlayerQuitEvent(),
				this);

		logger.info("BlockTracker 1.0");
		logger.info("Server: v1.7");
		logger.info("Checking Config...");
		if (BTRConfiguration.readConfig()) {
			if (BTRSQL.checkDB()) {
				logger.info("Checking SQL Table...");
				if (BTRSQL.checkTable()) {
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
		BTRExecutorService.ThreadPool.shutdown();
	}

	public static String getTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
