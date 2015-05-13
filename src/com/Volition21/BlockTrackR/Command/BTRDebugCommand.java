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

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.source.ConsoleSource;

import com.Volition21.BlockTrackR.BlockTrackR;
import com.Volition21.BlockTrackR.Utility.BTRConfiguration;
import com.Volition21.BlockTrackR.Utility.BTRExecutorService;
import com.Volition21.BlockTrackR.Utility.BTRJSONTools;
import com.Volition21.BlockTrackR.Utility.BTRPermissionCheck;

public class BTRDebugCommand {

	BTRJSONTools BTRvc = new BTRJSONTools();
	BTRConfiguration BTRc = new BTRConfiguration();
	BTRPermissionCheck BTRPC = new BTRPermissionCheck();

	public void ToggleDebug(final CommandSource cs) {
		BlockTrackR.logger.info("ToggleDebug - preAuth");
		if (BTRPC.isAuthed(cs)) {
			BlockTrackR.logger.info("ToggleDebug - isAuthed");
			BTRExecutorService.ThreadPool.execute(new Runnable() {
				public void run() {
					Thread.currentThread().setName("BTRDC");
					if (BlockTrackR.debug.equals("true")) {
						BlockTrackR.debug = "false";
						BlockTrackR.logger.info("Debugging: "
								+ BlockTrackR.debug);
						if (!(cs instanceof ConsoleSource)) {
							cs.sendMessage(Texts.of("Debugging: "
									+ BlockTrackR.debug));
						}
						BTRc.setConfigValue("debug", "false");
					} else {
						BlockTrackR.debug = "true";
						BTRc.setConfigValue("debug", "true");
						BlockTrackR.logger.info("Debugging: "
								+ BlockTrackR.debug);
						if (!(cs instanceof ConsoleSource)) {
							cs.sendMessage(Texts.of("Debugging: "
									+ BlockTrackR.debug));
						}
					}
				}
			});
		} else {
			BlockTrackR.logger.info("ToggleDebug - notAuthed");
			cs.sendMessage(Texts.of("You are not an authorized user."));
			cs.sendMessage(Texts
					.of("/btr auth [Playername] - Requires OP privilages."));
		}
	}
}
