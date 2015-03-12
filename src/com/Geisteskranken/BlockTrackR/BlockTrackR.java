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

		//Event Listeners.
		getServer().getPluginManager().registerEvents(new BTRBlockBreakEvent(), this);
		getServer().getPluginManager().registerEvents(new BTRBlockPlaceEvent(), this);
		getServer().getPluginManager().registerEvents(new BTRAsyncPlayerChatEvent(), this);

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
