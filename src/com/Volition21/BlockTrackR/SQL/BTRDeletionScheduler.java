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

import java.util.Date;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.Volition21.BlockTrackR.BlockTrackR;
import com.Volition21.BlockTrackR.Utility.BTRDebugger;

public class BTRDeletionScheduler {

	BTRSQL BTRSQL = new BTRSQL();

	public void initDeletionScheduler() {
		BTRDebugger.DLog("initDeletionScheduler");
		long StoreRecordsFor = getOldUNIXTime(BlockTrackR.StoreRecordsFor);
		BTRDebugger.DLog("StoreRecordsFor: " + StoreRecordsFor);
		recordDeletionScheduler(StoreRecordsFor);
	}

	public long getOldUNIXTime(int days) {
		Calendar cal = Calendar.getInstance();
		BTRDebugger.DLog("getOldUNIXTime");
		cal.add(Calendar.DAY_OF_MONTH, -days);
		BTRDebugger.DLog("int days: " + days);
		Date thendate = cal.getTime();
		long unixTime = thendate.getTime() / 1000;
		BTRDebugger.DLog("unixTime: " + unixTime);
		return unixTime;
	}

	public void recordDeletionScheduler(final Long age) {
		Timer timer = new Timer();
		TimerTask hourlyTask = new TimerTask() {
			@Override
			public void run() {
				Thread.currentThread().setName("BTRDS");
				BTRDebugger.DLog("recordDeletionScheduler");
				BTRDebugger.DLog("Deleting records older than: " + age);
				BTRSQL.delRecords(age);
			}
		};

		timer.schedule(hourlyTask, 0l, 1000 * 60 * 60 * 4);
	}
}
