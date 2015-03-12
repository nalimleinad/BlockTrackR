package com.Geisteskranken.BlockTrackR;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.Geisteskranken.BlockTrackR.BlockTrackR;

public class BTRAsyncPlayerChatEvent implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void AsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
		if (BlockTrackR.Track) {
			final String MSG = String.valueOf(event.getMessage());

			// Extrapolates the X,Y,and Z coordinates from the broken block
			// object.
			final int X = event.getPlayer().getLocation().getBlockX();
			final int Y = event.getPlayer().getLocation().getBlockY();
			final int Z = event.getPlayer().getLocation().getBlockZ();

			// Isolates the playername from the player object.
			final String Player = event.getPlayer().getName();
			final String PlayerUUID = event.getPlayer().getUniqueId().toString();

			// Insert to DB
			BlockTrackRExecutorService.ThreadPool.execute(new Runnable() {
				public void run() {
					Thread currentThread = Thread.currentThread();
					currentThread.setName("BlockTrackR SQL Insert (AsyncPlayerChatEvent)- " + Player
							+ ":" + MSG + "@" + X + "," + Y + "," + Z);
					BlockTrackRSQL.insertPlayerChat(Player,PlayerUUID ,X, Y, Z,
							BlockTrackR.getTime(), MSG);
					BlockTrackRDebugger.DLog(currentThread.getName());

				}
			});
		}
	}

}
