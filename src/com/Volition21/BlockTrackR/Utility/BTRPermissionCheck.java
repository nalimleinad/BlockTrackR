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
package com.Volition21.BlockTrackR.Utility;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.source.ConsoleSource;

import com.Volition21.BlockTrackR.BlockTrackR;

public class BTRPermissionCheck {

	

	private String value;
	private String[] values;
	private String OP_UUID;

	/**
	 * Takes a UUID and compares it to all the UUIDs in the ops.JSON file.
	 * 
	 * @param UUID
	 *            The UUID of the player.
	 * @return True if the UUID is found in ops.JSON.
	 */
	public boolean isOP(String UUID) {
		BTRJSONTools BTRJSONT = new BTRJSONTools();
		Map<?, ?> opsJSONasMAP = BTRJSONT.getOpsJSONasMap();
		if (!(opsJSONasMAP == null)) {
			for (Entry<?, ?> entry : opsJSONasMAP.entrySet()) {
				value = entry.getValue().toString();
				values = entry.getValue().toString()
						.substring(2, value.length() - 2).split(",");
				OP_UUID = values[2].replace("uuid=", "");
				OP_UUID = OP_UUID.replaceAll("\\s|\\s+", "");
				BTRDebugger.DLog("UUID___: " + UUID);
				BTRDebugger.DLog("OP_UUID: " + OP_UUID);
				if (OP_UUID.equals(UUID)) {
					BTRDebugger.DLog("Is op.");
					return true;
				} else {
					BTRDebugger.DLog("Is not op.");
					return false;
				}
			}
			BTRDebugger.DLog("Is not op.");
			return false;
		} else {
			BTRDebugger.DLog("opsJSONasMAP has returned null");
			BTRDebugger.DLog("UUID: " + UUID);
			BTRDebugger.DLog("Is not op.");
			return false;
		}
	}

	/**
	 * Takes a UUID and checks if it is contained within the array
	 * "BlockTrackR.authorized_player".
	 * 
	 * @param UUID
	 *            The UUID of the player.
	 * @return True if the UUID is found in the array
	 *         "BlockTrackR.authorized_players".
	 */
	public boolean isAuthed(String UUID) {
		if (isOP(UUID)) {
			return true;
		} else {
			BTRDebugger.DLog("isOP returned false");
			if (BlockTrackR.authorized_players != null) {
				BTRDebugger.DLog("authorized_players is not null");
				if (Arrays.asList(BlockTrackR.authorized_players)
						.contains(UUID)) {
					BTRDebugger.DLog("UUID found in authorized_players");
					return true;
				} else {
					BTRDebugger.DLog("UUID NOT found in authed_players");
					return false;
				}
			} else {
				BTRDebugger.DLog("authorized_players is null");
				return false;
			}
		}
	}

	/**
	 * Takes a UUID and checks if it is contained within
	 * BlockTrackR.authorized_players.
	 * 
	 * @param player
	 *            The player object.
	 * @return True if the player's UUID is found in the array
	 *         "BlockTrackR.authorized_players".
	 */
	public boolean isAuthed(Player player) {
		if (isAuthed(player.getUniqueId().toString())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Takes a CommandSource and returns true if it's a player and is
	 * authorized, or if it's a ConsoleSource.
	 * 
	 * @param cs
	 *            The CommandSource
	 * @return True if the source is a player and is authorized or is a
	 *         ConsoleSource.
	 */
	public boolean isAuthed(CommandSource cs) {
		if (cs instanceof Player) {
			if (isAuthed(((Player) cs).getUniqueId().toString())) {
				return true;
			} else {
				return false;
			}

		} else if (cs instanceof ConsoleSource) {
			return true;
		} else {
			return false;
		}
	}

}
