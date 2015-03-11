package com.Geisteskranken.BlockTrackR;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.Geisteskranken.BlockTrackR.BlockTrackR;

public class BTEvent implements Listener {

	public static final ExecutorService SQLQueue = Executors
			.newFixedThreadPool(4);

	@EventHandler(priority = EventPriority.MONITOR)
	public void BlockBreakEvent(BlockBreakEvent event) {
		BlockTrackR.logger.info("EventTriggered");
		if (BlockTrackR.Track) {
				final String BlockType = String.valueOf(event.getBlock().getType());

				// Extrapolates the X,Y,and Z coordinates from the broken block object.
				final int X = event.getBlock().getX();
				final int Y = event.getBlock().getY();
				final int Z = event.getBlock().getZ();

				// Isolates the playername from the player object.
				final String Player = event.getPlayer().getName();
				
				BlockTrackR.logger.info(BlockType);
				BlockTrackR.logger.info(String.valueOf(X));
				BlockTrackR.logger.info(String.valueOf(Y));
				BlockTrackR.logger.info(String.valueOf(Z));
				BlockTrackR.logger.info(Player);
				BlockTrackR.logger.info(BlockTrackR.getTime());
				
				// Insert to DB
				//SQLQueue.execute(new Runnable() {
					//public void run() {
						//BlockTrackRSQL.insertBlockBreak(Player, X, Y, Z,
							//	BlockTrackR.getTime(), BlockType);
					//}
				//});
		}
	}

}
