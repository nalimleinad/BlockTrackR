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

import com.Volition21.BlockTrackR.BlockTrackR;
import com.Volition21.BlockTrackR.SQL.BTRSQL;
import com.Volition21.BlockTrackR.Utility.BTRDebugger;
import com.Volition21.BlockTrackR.Utility.BTRExecutorService;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerQuitEvent;

public class BTRPlayerQuitEvent {

	@Subscribe
	public void PlayerQuitEvent(PlayerQuitEvent event) {
		if (BlockTrackR.Track) {
			/**
			 * Initialize a Player object with the event's source cast as a
			 * Player object.
			 */
			Player player = event.getPlayer();

			/**
			 * Initialize a String object with the IP address of the connecting
			 * player.
			 */
			final String IP = event.getPlayer().getConnection().getAddress()
					.toString();

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
					Thread.currentThread().setName("BTRPQE");
					// Debug output controlled by switch in configuration file.
					BTRDebugger.DLog("BTRPlayerQuitEvent");
					BTRDebugger.DLog("IP Address: " + IP);
					BTRDebugger.DLog("Player: " + Player);
					BTRDebugger.DLog("PlayerUUID: " + PlayerUUID);
					BTRDebugger.DLog("X: " + X);
					BTRDebugger.DLog("Y: " + Y);
					BTRDebugger.DLog("Z: " + Z);
					BTRDebugger.DLog("World: " + world);

					// Insert to DB
					BTRSQL.insertPlayerQuit(Player, PlayerUUID, X, Y, Z, world,
							BlockTrackR.getTime(), IP);
				}
			});

		}
	}

}
