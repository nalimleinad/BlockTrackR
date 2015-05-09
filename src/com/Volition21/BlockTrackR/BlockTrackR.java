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

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import com.Volition21.BlockTrackR.Command.TestCommand;
import com.Volition21.BlockTrackR.Event.*;
import com.Volition21.BlockTrackR.SQL.BTRSQL;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;
import com.google.inject.Inject;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerJoinEvent;
import org.spongepowered.api.event.entity.player.PlayerPickUpItemEvent;
import org.spongepowered.api.event.state.ServerStartedEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.event.EventManager;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.args.CommandElement;
import org.spongepowered.api.util.command.spec.CommandSpec;

@SuppressWarnings("unused")
@Plugin(id = "BTR", name = "BlockTrackR", version = "1.0")
public class BlockTrackR {

	public static String id = "BTR";
	public static String version = "1.0";

	public static Boolean Track = false;
	public static String debug = "false";

	public static String host;
	public static String port;
	public static String connector;
	public static String database;
	public static String dbuser;
	public static String dbpass;

	@Inject
	public Game game;

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
	public void onServerStart(ServerStartedEvent event) {
		logger.info("BlockTracker Starting Up...");
		logger.info("Minecraft: v1.8 - Sponge");
		/**
		 * Initialize the Server object with the game's server instance. Must
		 * not be attempted before the server has started - for obvious reasons.
		 */
		server = game.getServer();

		/**
		 * Initialize Commands.
		 */
		CommandSpec TestCommand = CommandSpec.builder()
				.setDescription(Texts.of("Short Desc."))
				.setExtendedDescription(Texts.of("Long Desc."))
				// .setChildren(subcommands)
				// .setPermission("BlockTrackR.command")
				.setExecutor(new TestCommand(server)).build();

		/**
		 * Register Commands.
		 */
		game.getCommandDispatcher().register(this, TestCommand, "test");

		/**
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

		/**
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
					// Do a quick version check.
					BTRvc.versionCheck();
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
