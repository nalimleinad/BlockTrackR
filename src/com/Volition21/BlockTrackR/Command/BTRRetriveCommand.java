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

import java.util.List;

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.CommandSource;

import com.Volition21.BlockTrackR.BlockTrackR;
import com.Volition21.BlockTrackR.SQL.BTRSQL;
import com.Volition21.BlockTrackR.Utility.BTRDebugger;
import com.Volition21.BlockTrackR.Utility.BTRExecutorService;

public class BTRRetriveCommand {

	BTRSQL BTRsql = new BTRSQL();

	String X;
	String Y;
	String Z;

	List<String> listresults;

	public String[] results;

	public void ToggleDebug(final CommandSource cs, final String[] args) {
		BTRExecutorService.ThreadPool.execute(new Runnable() {
			public void run() {
				Thread.currentThread().setName("BTRRC");
				try {
					X = args[1];
					Y = args[2];
					Z = args[3];
				} catch (IndexOutOfBoundsException e) {
					cs.sendMessage(Texts.of("Not enough arguments"));
				}
				if (!(X == null || Y == null || Z == null)) {
					BTRDebugger.DLog("BTRRetriveCommand");
					BTRDebugger.DLog("X: " + X);
					BTRDebugger.DLog("Y: " + Y);
					BTRDebugger.DLog("Z: " + Z);
					listresults = BTRsql.getBlockRecord(X, Y, Z);
					results = new String[listresults.size()];

					results = listresults.toArray(results);

					int length = results.length;
					if (length == 0) {
						cs.sendMessage(Texts.of("No Results"));
					} else {
						for (int i = 0; i < results.length; i++) {
							cs.sendMessage(Texts.of(results[i]));
						}
					}
				}
			}
		});
	}
}
