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
package com.Volition21.BlockTrackR.Event;

//import org.apache.commons.lang.StringEscapeUtils;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.Tamer;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.entity.player.User;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.message.MessageEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextBuilder;
import org.spongepowered.api.text.TextBuilder.Literal;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.Identifiable;

import com.Volition21.BlockTrackR.BTRDebugger;
import com.Volition21.BlockTrackR.BTRExecutorService;
import com.Volition21.BlockTrackR.BlockTrackR;
import com.Volition21.BlockTrackR.SQL.BTRSQL;

@SuppressWarnings("unused")
public class BTRAsyncPlayerChatEvent {

	@Subscribe
	public void AsyncPlayerChatEvent(MessageEvent event) {
		if (BlockTrackR.Track) {
			/**
			 * Initialize a Player object with the event's source cast as a
			 * Player object.
			 */
			Player player = (org.spongepowered.api.entity.player.Player) event
					.getSource();

			/**
			 * Initialize a String object with the Text object converted to a
			 * plain String. Sanitize the String for insertion to SQL database.
			 */
			final String MSG = Texts.toPlain(event.getMessage());
			// TODO
			// final String SanatizedMSG = StringEscapeUtils.escapeSql(MSG);

			/**
			 * Extrapolates the X,Y,and Z coordinates from the Player object.
			 */
			final int X = player.getLocation().getBlockX();
			final int Y = player.getLocation().getBlockY();
			final int Z = player.getLocation().getBlockZ();

			/**
			 * Isolates the player's name and UUID from the MessageEvent object.
			 */
			final String PlayerUUID = player.getIdentifier();
			final String Player = player.getName();

			/**
			 * Extrapolates the world name from the Player object.
			 */
			final String world = player.getWorld().getName();

			/**
			 * Add to queue for insertion to SQL database.
			 */
			BTRExecutorService.ThreadPool.execute(new Runnable() {
				public void run() {
					// Name this thread for debug purposes.
					Thread.currentThread().setName("BTRAPCE");
					// Debug output controlled by switch in configuration file.
					BTRDebugger.DLog("BTRAsyncPlayerChatEvent");
					BTRDebugger.DLog("MSG: " + MSG);
					BTRDebugger.DLog("Player: " + Player);
					BTRDebugger.DLog("PlayerUUID: " + PlayerUUID);
					BTRDebugger.DLog("X: " + X);
					BTRDebugger.DLog("Y: " + Y);
					BTRDebugger.DLog("Z: " + Z);
					BTRDebugger.DLog("World: " + world);

					// Insert to DB
					BTRSQL.insertPlayerChat(Player, PlayerUUID, X, Y, Z, world,
							BlockTrackR.getTime(), MSG);
				}
			});

		}
	}
}
