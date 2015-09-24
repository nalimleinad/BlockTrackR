package com.Volition21.BlockTrackR.Utility;

import org.spongepowered.api.entity.living.player.Player;

import com.google.common.base.Optional;

public class BTRGetPlayer {

	
	/**
	 * Takes an Optional object and checks if the Player object is present.
	 * If it is, return the actual Player object.
	 * 
	 * @param optionalplayer An Optional object.
	 * @return Player object.
	 */
	public static Player getPlayer(Optional<Player> optional) {
		if (optional.isPresent()) {
			Player player = optional.get();
			return player;
		} else {
			return null;
		}
	}

}
