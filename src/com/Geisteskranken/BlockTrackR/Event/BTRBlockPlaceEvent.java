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
package com.Geisteskranken.BlockTrackR.Event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.Geisteskranken.BlockTrackR.BTRDebugger;
import com.Geisteskranken.BlockTrackR.BTRExecutorService;
import com.Geisteskranken.BlockTrackR.BlockTrackR;
import com.Geisteskranken.BlockTrackR.SQL.BTRSQL;

public class BTRBlockPlaceEvent implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void BlockPlaceEvent(BlockPlaceEvent event) {
		if (BlockTrackR.Track) {
			final String BlockType = String.valueOf(event.getBlock().getType());

			// Extrapolates the X,Y,and Z coordinates from the broken block
			// object.
			final int X = event.getBlock().getX();
			final int Y = event.getBlock().getY();
			final int Z = event.getBlock().getZ();

			// Isolates the playername from the player object.
			final String Player = event.getPlayer().getName();
			final String PlayerUUID = event.getPlayer().getUniqueId()
					.toString();

			// Get player's world.
			final String world = event.getPlayer().getWorld().getName();

			// Insert to DB
			BTRExecutorService.ThreadPool.execute(new Runnable() {
				public void run() {
					Thread currentThread = Thread.currentThread();
					currentThread
							.setName("BlockTrackR SQL Insert (PlaceEvent)- "
									+ Player + ":" + BlockType + "@" + X + ","
									+ Y + "," + Z + ":" + world);
					BTRSQL.insertBlockPlace(Player, PlayerUUID, X, Y, Z, world,
							BlockTrackR.getTime(), BlockType);
					BTRDebugger.DLog(currentThread.getName());

				}
			});
		}
	}

}
