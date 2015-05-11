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

import org.apache.commons.lang.StringEscapeUtils;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;

import com.Volition21.BlockTrackR.SQL.BTRSQL;
import com.Volition21.BlockTrackR.Utility.BTRDebugger;
import com.Volition21.BlockTrackR.Utility.BTRExecutorService;

public class BTRRetriveCommand {

	BTRSQL BTRsql = new BTRSQL();

	List<String> listresults;

	public String[] results;

	/**
	 * Retries all SQL records at the provided X, Y, and Z coordinates and sends
	 * them back to the CommandSource. Requires three arguments (args[1] = Z,
	 * args[2] = Y, args[3] = Z)
	 * 
	 * @param cs
	 *            The CommandSource.
	 * @param args
	 *            The commands arguments.
	 */
	public void retriveCommand(final CommandSource cs, final String[] args) {
		BTRExecutorService.ThreadPool.execute(new Runnable() {
			String X;
			String Y;
			String Z;

			public void run() {
				Thread.currentThread().setName("BTRRR");
				try {
					String X_ = args[1];
					String Y_ = args[2];
					String Z_ = args[3];
					X = StringEscapeUtils.escapeSql(X_);
					Y = StringEscapeUtils.escapeSql(Y_);
					Z = StringEscapeUtils.escapeSql(Z_);
				} catch (IndexOutOfBoundsException e) {
					cs.sendMessage(Texts.of(TextColors.RED,
							"Inncorrect Syntax."));
					cs.sendMessage(Texts.of(TextColors.RED,
							"/BTR retrive [x] [y] [z]"));
				}
				if (!(X == null || Y == null || Z == null)) {
					BTRDebugger.DLog("BTRRetriveRecord");
					BTRDebugger.DLog("X: " + X);
					BTRDebugger.DLog("Y: " + Y);
					BTRDebugger.DLog("Z: " + Z);
					listresults = BTRsql.getBlockRecord(X, Y, Z);
					results = new String[listresults.size()];

					results = listresults.toArray(results);

					int length = results.length;
					if (length == 0) {
						cs.sendMessage(Texts.of(TextColors.RED, "No Results"));
					} else {
						cs.sendMessage(Texts.of(TextColors.RED,
								"BlockTrackR Results @ " + X + "," + Y + ","
										+ Z));
						cs.sendMessage(Texts.of(TextColors.RED,
								"----------------------------"));
						for (int i = 0; i < results.length; i++) {
							cs.sendMessage(Texts.of(TextColors.RED, results[i]));
						}
						cs.sendMessage(Texts.of(TextColors.RED,
								"----------------------------"));
					}
				}
			}
		});
	}
}
