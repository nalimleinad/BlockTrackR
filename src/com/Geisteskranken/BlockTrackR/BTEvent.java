package com.Geisteskranken.BlockTrackR;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.Geisteskranken.BlockTrackR.BlockTrackR;

public class BTEvent implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void BlockBreakEvent(BlockBreakEvent event) {
		if (BlockTrackR.Track) {
			final String BlockType = String.valueOf(event.getBlock().getType());

			// Extrapolates the X,Y,and Z coordinates from the broken block
			// object.
			final int X = event.getBlock().getX();
			final int Y = event.getBlock().getY();
			final int Z = event.getBlock().getZ();

			// Isolates the playername from the player object.
			final String Player = event.getPlayer().getName();

			// Insert to DB
			BlockTrackRExecutorService.ThreadPool.execute(new Runnable() {
				public void run() {
					Thread currentThread = Thread.currentThread();
					currentThread.setName("BlockTrackR SQL Insert - " + Player
							+ ":" + BlockType + "@" + X + "," + Y + "," + Z);
					BlockTrackRSQL.insertBlockBreak(Player, X, Y, Z,
							BlockTrackR.getTime(), BlockType);
					BlockTrackRDebugger.DLog(currentThread.getName());

				}
			});
		}
	}

}
