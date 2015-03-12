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
package com.Geisteskranken.BlockTrackR.Event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.Geisteskranken.BlockTrackR.BTRDebugger;
import com.Geisteskranken.BlockTrackR.BTRExecutorService;
import com.Geisteskranken.BlockTrackR.BlockTrackR;
import com.Geisteskranken.BlockTrackR.SQL.BTRSQL;

public class BTRPlayerPickupItemEvent implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void PlayerPickupItemEvent(PlayerPickupItemEvent event) {
		if (BlockTrackR.Track) {
			final String ItemType = event.getItem().getItemStack().getType().toString();

			// Extrapolates the X,Y,and Z coordinates from the broken block
			// object.
			final int X = event.getItem().getLocation().getBlockX();
			final int Y = event.getItem().getLocation().getBlockY();
			final int Z = event.getItem().getLocation().getBlockZ();

			// Isolates the playername from the player object.
			final String Player = event.getPlayer().getName();
			final String PlayerUUID = event.getPlayer().getUniqueId().toString();

			// Insert to DB
			BTRExecutorService.ThreadPool.execute(new Runnable() {
				public void run() {
					Thread currentThread = Thread.currentThread();
					currentThread.setName("BlockTrackR SQL Insert (PickupItemEvent) - " + Player
							+ ":" + ItemType + "@" + X + "," + Y + "," + Z);
					BTRSQL.insertPickupItem(Player,PlayerUUID ,X, Y, Z,
							BlockTrackR.getTime(), ItemType);
					BTRDebugger.DLog(currentThread.getName());

				}
			});
		}
	}

}
