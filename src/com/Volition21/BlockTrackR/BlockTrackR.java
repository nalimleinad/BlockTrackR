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

import com.Volition21.BlockTrackR.Command.TestCommand;
import com.Volition21.BlockTrackR.Event.*;
import com.Volition21.BlockTrackR.SQL.BTRSQL;
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
import org.spongepowered.api.util.command.spec.CommandSpec;

@SuppressWarnings("unused")
@Plugin(id = "BTR", name = "BlockTrackR", version = "1.0")
public class BlockTrackR {

	@Inject
	public Game game;

	public Server server;
	public static Logger logger;

	@Inject
	private void setLogger(Logger logger) {
		BlockTrackR.logger = logger;
	}

	public static Boolean Track = true;
	public static String debug = "false";

	public static String host;
	public static String port;
	public static String connector;
	public static String database;
	public static String dbuser;
	public static String dbpass;

	@Subscribe
	public void onServerStart(ServerStartedEvent event) {

		server = game.getServer(); // Have to getServer() onServerStart and not
		// before for obvious reasons.

		logger.debug("This is a log message.");

		// Command format
		CommandSpec TestCommand = CommandSpec.builder()
				.setDescription(Texts.of("Short Desc."))
				.setExtendedDescription(Texts.of("Long Desc."))
				// .setChildren(subcommands)
				// .setPermission("BlockTrackR.command")
				.setExecutor(new TestCommand(server)).build();

		game.getCommandDispatcher().register(this, TestCommand, "test");

		BTRDebugger.DLog("Reg BTRPJE");
		game.getEventManager().register(this, new BTRPlayerJoinEvent());
		BTRDebugger.DLog("Reg BTRPQE");
		game.getEventManager().register(this, new BTRPlayerQuitEvent());
		BTRDebugger.DLog("Reg BTRPPUIE");
		game.getEventManager().register(this, new BTRPlayerPickUpItemEvent());
		BTRDebugger.DLog("Reg BTRPDIE");
		game.getEventManager().register(this, new BTRPlayerDropItemEvent());
		BTRDebugger.DLog("Reg BTRBPE");
		game.getEventManager().register(this, new BTRBlockPlaceEvent());
		BTRDebugger.DLog("Reg BTRBBE");
		game.getEventManager().register(this, new BTRBlockBreakEvent());
		BTRDebugger.DLog("Reg BTRAPCE");
		game.getEventManager().register(this, new BTRAsyncPlayerChatEvent());

		logger.info("BlockTracker 1.0");
		logger.info("Server: v1.8");
		logger.info("Checking Config...");
		/**
		 * if (BTRConfiguration.readConfig()) { if (BTRSQL.checkDB()) {
		 * logger.info("Checking SQL Table..."); if (BTRSQL.checkTable()) {
		 * logger.info("Everything appears OK"); logger.info("Debugging: " +
		 * debug); logger.info("Enabled!"); Track = true; } else { Track =
		 * false; } } else { Track = false; } } else { Track = false; }
		 */
		Track = true;
	}

	public static String getTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
