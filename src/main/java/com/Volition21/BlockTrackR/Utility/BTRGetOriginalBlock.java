package com.Volition21.BlockTrackR.Utility;

import java.util.List;

import org.spongepowered.api.block.BlockTransaction;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.google.common.base.Optional;

public class BTRGetOriginalBlock {

	public static Location<World> getOriginalBlock(List<BlockTransaction> transactions) {
		for (BlockTransaction block : transactions) {
			Optional<Location<World>> optLoc = block.getOriginal().getLocation();

			if (!optLoc.isPresent()) {
				continue;
			}

			return optLoc.get();
		}
		return null;
	}

}
