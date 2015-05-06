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

import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerPickUpItemEvent;
import com.Volition21.BlockTrackR.BTRDebugger;
import com.Volition21.BlockTrackR.BTRExecutorService;
import com.Volition21.BlockTrackR.BlockTrackR;
import com.Volition21.BlockTrackR.SQL.BTRSQL;

@SuppressWarnings("unused")
public class BTRPlayerPickUpItemEvent {

	@Subscribe
	public void PlayerPickUpItemEvent(PlayerPickUpItemEvent event) {
		BTRDebugger.DLog("BTRPPUIE instance");
		// if (BlockTrackR.Track) {

		final String ItemType = event.getInventory().getName().toString();

		final int X = event.getPlayer().getLocation().getBlockX();
		final int Y = event.getPlayer().getLocation().getBlockY();
		final int Z = event.getPlayer().getLocation().getBlockZ();

		// Isolates the playername from the player object.
		final String Player = event.getPlayer().getName();
		final String PlayerUUID = event.getPlayer().getUniqueId().toString();

		// Get player's world.
		final String world = event.getPlayer().getWorld().getName();

		// Insert to DB
		BTRDebugger.DLog("Drop Item Event: " + ItemType + "&" + X + "&" + Y
				+ "&" + Z + "&" + Player + "&" + PlayerUUID + "&" + world);
		/**
		 * BTRExecutorService.ThreadPool.execute(new Runnable() { public void
		 * run() { Thread currentThread = Thread.currentThread(); currentThread
		 * .setName("BlockTrackR SQL Insert (PickupItemEvent) - " + Player + ":"
		 * + ItemType + "@" + X + "," + Y + "," + Z + ":" + world);
		 * BTRSQL.insertPickupItem(Player, PlayerUUID, X, Y, Z, world,
		 * BlockTrackR.getTime(), ItemType); } });
		 */
		// }
	}

}
