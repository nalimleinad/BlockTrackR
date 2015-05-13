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
package com.Volition21.BlockTrackR;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.Volition21.BlockTrackR.Command.BTRMainCommand;
import com.Volition21.BlockTrackR.Event.*;
import com.Volition21.BlockTrackR.SQL.BTRSQL;
import com.Volition21.BlockTrackR.Utility.BTRConfiguration;
import com.Volition21.BlockTrackR.Utility.BTRVersionCheck;
import com.google.inject.Inject;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.state.ServerStartingEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.command.CommandService;

@Plugin(id = "BTR", name = "BlockTrackR", version = "1.0")
public class BlockTrackR {

	public static String id = "BTR";
	public static String version = "1.0";

	public static Boolean Track = false;
	public static String version_check = "true";
	public static String debug = "false";

	public static String host;
	public static String port;
	public static String connector;
	public static String database;
	public static String dbuser;
	public static String dbpass;

	@Inject
	public Game game;

	public CommandService cs;
	public Server server;
	public static Logger logger;

	@Inject
	private void setLogger(Logger logger) {
		BlockTrackR.logger = logger;
	}

	BTRVersionCheck BTRvc = new BTRVersionCheck();

	/**
	 * Initialize all of BlockTrackR
	 */
	@Subscribe
	public void onServerStarting(ServerStartingEvent event) {
		logger.info("BlockTracker Starting Up...");
		logger.info("Minecraft: v1.8 - Sponge");
		/*
		 * Initialize
		 * 
		 * -The Server object with the game's server instance. Must not be
		 * attempted before the server has started - for obvious reasons.
		 * 
		 * -The CommandService object with the game's CommandDispatcher.
		 */
		server = game.getServer();
		cs = game.getCommandDispatcher();

		/*
		 * Register Commands.
		 */
		cs.register(this, new BTRMainCommand(server, game), "BTR");

		/*
		 * Register all the event listeners with the EventManager
		 */
		logger.info("Registering Events...");
		try {
			game.getEventManager().register(this, new BTRPlayerJoinEvent());
			game.getEventManager().register(this, new BTRPlayerQuitEvent());
			game.getEventManager().register(this,
					new BTRPlayerPickUpItemEvent());
			game.getEventManager().register(this, new BTRPlayerDropItemEvent());
			game.getEventManager().register(this, new BTRBlockPlaceEvent());
			game.getEventManager().register(this, new BTRBlockBreakEvent());
			game.getEventManager()
					.register(this, new BTRAsyncPlayerChatEvent());
		} catch (Exception e) {
			// This should pretty much never ever happen. Ever.
			logger.info("Event registration error, please submit bug report to Volition21 with your log files.");
			logger.info(e.toString());
			Track = false;
		}

		/*
		 * Check Configuration and SQL feasibility.
		 */
		logger.info("Checking Config...");
		if (BTRConfiguration.readConfig()) {
			logger.info("Checking SQL DB...");
			if (BTRSQL.checkDB()) {
				logger.info("Checking SQL Table...");
				if (BTRSQL.checkTable()) {
					logger.info("Everything appears OK");
					logger.info("Debugging: " + debug);
					logger.info("Enabled!");
					Track = true;
					if (version_check.equals("true")) {
						BTRvc.versionCheck();
					}
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

	public static String getTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
