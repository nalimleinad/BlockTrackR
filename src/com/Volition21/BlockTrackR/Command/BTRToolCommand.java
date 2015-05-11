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
package com.Volition21.BlockTrackR.Command;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.util.command.CommandSource;

import com.Volition21.BlockTrackR.Utility.BTRItemManipulation;

public class BTRToolCommand {
	BTRItemManipulation BTRIM = new BTRItemManipulation();

	/*
	 * TODO
	 * 
	 * - Send message if the CommandSource is not a player. - Send message if
	 * the player's inventory rejects the offer. - Check for permission.
	 * 
	 * The InvetoryAPI is not yet finished so this code is A) Untestable B)
	 * Useless, at this time.
	 */

	public void giveTool(Game game, final CommandSource cs, final String[] args) {
		ItemStack i = BTRIM.createCustomItem(game, "Log Block", ItemTypes.LOG);
		if (cs instanceof Player) {
			((Player) cs).getInventory().offer(i);
		}
	}
}
