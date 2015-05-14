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

import com.Volition21.BlockTrackR.BlockTrackR;

public class BTRVersionCheck {

	BTRJSONTools BTRJSONT = new BTRJSONTools();

	/**
	 * Compares the current version of the plugin to the version reported in the
	 * mapped JSON data.
	 */
	public void versionCheck() {
		BTRExecutorService.ThreadPool.execute(new Runnable() {
			public void run() {
				Thread.currentThread().setName("BTRVC");
				if (BlockTrackR.id.equals(BTRJSONT.getValueFromJSON("id"))) {
					String tempversion = BTRJSONT.getValueFromJSON("version");
					if (!(BlockTrackR.version.equals(tempversion))) {
						// Version mismatch.
						BlockTrackR.logger
								.info("This version of BlockTrackR may be outdated!");
						BlockTrackR.logger.info("Current Version: "
								+ BlockTrackR.version);
						BlockTrackR.logger.info("Available Version: "
								+ tempversion);
					} else {
						// Version Match.
						BlockTrackR.logger.info("BlockTrackR is up-to-date.");
					}
				} else {
					// This should not happen, means the ID of the plugin did
					// not match the meta.data ID.
					BTRDebugger.DLog("Version Checking Error.");
				}
			}
		});
	}

}
