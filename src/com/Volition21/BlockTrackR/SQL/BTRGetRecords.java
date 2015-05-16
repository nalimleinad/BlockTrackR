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
package com.Volition21.BlockTrackR.SQL;

import java.util.List;

import org.spongepowered.api.event.entity.player.PlayerEvent;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import com.Volition21.BlockTrackR.Utility.BTRDebugger;

public class BTRGetRecords {

	BTRSQL BTRsql = new BTRSQL();

	List<String> listresults;
	public String[] results;

	public void getRecords(String X, String Y, String Z, PlayerEvent event) {

		BTRDebugger.DLog("BTRGetRecords");
		BTRDebugger.DLog("X: " + X);
		BTRDebugger.DLog("Y: " + Y);
		BTRDebugger.DLog("Z: " + Z);

		listresults = BTRsql.getBlockRecord(X, Y, Z);
		results = new String[listresults.size()];

		results = listresults.toArray(results);

		int length = results.length;
		if (length == 0) {
			event.getPlayer().sendMessage(
					Texts.of(TextColors.RED, "No Results"));
		} else {
			event.getPlayer().sendMessage(
					Texts.of(TextColors.DARK_AQUA, "BlockTrackR Results @ " + X
							+ "," + Y + "," + Z));
			event.getPlayer().sendMessage(
					Texts.of(TextColors.DARK_RED,
							"------------------------------"));
			for (int i = 0; i < results.length; i++) {
				event.getPlayer().sendMessage(
						Texts.of(TextColors.RED, results[i]));
			}
			event.getPlayer().sendMessage(
					Texts.of(TextColors.DARK_RED,
							"----------------------------"));
		}

	}
}