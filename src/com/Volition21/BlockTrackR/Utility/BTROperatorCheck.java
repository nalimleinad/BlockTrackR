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

import java.util.Map;
import java.util.Map.Entry;

public class BTROperatorCheck {

	static BTRJSONTools BTRJSONT = new BTRJSONTools();

	static Map<?, ?> mm = BTRJSONT.getOpsJSONasMap();

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
		for (Entry<?, ?> entry : mm.entrySet()) {
			value = entry.getValue().toString();
			values = entry.getValue().toString()
					.substring(2, value.length() - 2).split(",");
			OP_UUID = values[2].replace("uuid=", "");
			OP_UUID = OP_UUID.replaceAll("\\s|\\s+", "");
			if (OP_UUID.equals(UUID)) {
				return true;
			}
		}
		return false;

	}

}
