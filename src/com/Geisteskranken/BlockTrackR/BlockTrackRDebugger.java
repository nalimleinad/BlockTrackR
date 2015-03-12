package com.Geisteskranken.BlockTrackR;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;

public class BlockTrackRDebugger {

	public static Logger DebugLogger = Bukkit.getLogger();
			
	public static void DLog(String msg) {
		if (BlockTrackR.debug.equals("true")) {
			DebugLogger.log(Level.INFO, "[BlockTrackRDebugger] " + msg);
		}

	}

}
