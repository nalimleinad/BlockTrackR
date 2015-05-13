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

import com.Volition21.BlockTrackR.BlockTrackR;
import com.Volition21.BlockTrackR.Utility.BTRDebugger;
import com.Volition21.BlockTrackR.Utility.BTRExecutorService;
import com.Volition21.BlockTrackR.Utility.BTRJSONTools;
import com.Volition21.BlockTrackR.Utility.BTRPermissionCheck;

public class BTRVersionCheckCommand {

	BTRJSONTools BTRvc = new BTRJSONTools();
	BTRPermissionCheck BTRPC = new BTRPermissionCheck();

	public void VersionCheckCommand(final CommandSource cs) {
		BTRDebugger.DLog("VersionCheckCommand - preAuth");
		if (BTRPC.isAuthed(cs)) {
			BTRDebugger.DLog("VersionCheckCommand - isAuthed");
			BTRExecutorService.ThreadPool.execute(new Runnable() {
				public void run() {
					Thread.currentThread().setName("BTRVCC");
					if (BlockTrackR.id.equals(BTRvc.getValueFromJSON("id"))) {
						String tempversion = BTRvc.getValueFromJSON("version");
						if (!(BlockTrackR.version.equals(tempversion))) {
							cs.sendMessage(Texts
									.of("This version of BlockTrackR may be outdated!"));
							cs.sendMessage(Texts.of("Current Version: "
									+ BlockTrackR.version));
							cs.sendMessage(Texts.of("Available Version: "
									+ tempversion));
						} else {
							cs.sendMessage(Texts
									.of("BlockTrackR is up-to-date."));
						}
					} else {
						BTRDebugger.DLog("Version Checking Error.");
					}

				}
			});
		} else {
			BTRDebugger.DLog("VersionCheckCommand - notAuthed");
		}
	}
}
