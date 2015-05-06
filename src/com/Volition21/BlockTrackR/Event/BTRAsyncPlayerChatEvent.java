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
package com.Volition21.BlockTrackR.Event;

//import org.apache.commons.lang.StringEscapeUtils;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.Tamer;
import org.spongepowered.api.entity.player.User;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.message.MessageEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextBuilder;
import org.spongepowered.api.text.TextBuilder.Literal;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.Identifiable;

import com.Volition21.BlockTrackR.BTRDebugger;
import com.Volition21.BlockTrackR.BTRExecutorService;
import com.Volition21.BlockTrackR.BlockTrackR;
import com.Volition21.BlockTrackR.SQL.BTRSQL;

@SuppressWarnings("unused")
public class BTRAsyncPlayerChatEvent {

	@Subscribe
	public void AsyncPlayerChatEvent(MessageEvent event) {
		if (BlockTrackR.Track) {
			final String MSG = Texts.toPlain(event.getMessage());
			
			// final String SanatizedMSG = StringEscapeUtils.escapeSql(MSG);

			// Extrapolates the X,Y,and Z coordinates from the broken block
			// object.
			// final int X = event.getSource()
			// final int Y = event.getSource()
			// final int Z = event.getSource()

			// Isolates the playername from the player object.
			// final String Player = event.getSource()
			// final String PlayerUUID = event.getSource()

			// Get player's world.
			// final String world = event.getSource()

			// Insert to DB
			BTRDebugger.DLog("Message Event: " + MSG);
			/**
			 * BTRExecutorService.ThreadPool.execute(new Runnable() { public
			 * void run() { Thread currentThread = Thread.currentThread();
			 * currentThread
			 * .setName("BlockTrackR SQL Insert (AsyncPlayerChatEvent)- " +
			 * Player + ":" + SanatizedMSG + "@" + X + "," + Y + "," + Z + ":" +
			 * world); BTRSQL.insertPlayerChat(Player, PlayerUUID, X, Y, Z,
			 * world, BlockTrackR.getTime(), SanatizedMSG);
			 * BTRDebugger.DLog(currentThread.getName());
			 * 
			 * } });
			 */
		}
	}
}
