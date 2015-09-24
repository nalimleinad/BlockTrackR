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

import java.util.Arrays;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;

import com.Volition21.BlockTrackR.BlockTrackR;
import com.Volition21.BlockTrackR.SQL.BTRGetRecords;
import com.Volition21.BlockTrackR.SQL.BTRSQL;
import com.Volition21.BlockTrackR.Utility.BTRDebugger;
import com.Volition21.BlockTrackR.Utility.BTRExecutorService;
import com.Volition21.BlockTrackR.Utility.BTRGetPlayer;
import com.Volition21.BlockTrackR.Utility.BTRPermissionTools;

public class BTRPlayerInteractBlockEvent {

	BTRPermissionTools BTRPT = new BTRPermissionTools();
	BTRGetRecords BTRGR = new BTRGetRecords();

	String[] Blocks = { "minecraft:lever", "minecraft:wooden_button", "minecraft:fence_gate", "minecraft:wooden_door",
			"minecraft:dark_oak_door", "minecraft:spruce_door", "minecraft:birch_door", "minecraft:jungle_door",
			"minecraft:acacia_door", "minecraft:dispenser", "minecraft:dropper", "minecraft:hopper",
			"minecraft:furnace", "minecraft:chest", "minecraft:jukebox ", "minecraft:enchanting_table",
			"minecraft:crafting_table", "minecraft:ender_chest" };

	@Listener
	public void PlayerInteractBlockEvent(InteractBlockEvent sourcePlayerData) {
		/*
		 * Get the Player object.
		 */
		Player player = BTRGetPlayer.getPlayer(sourcePlayerData.getCause().first(Player.class));
		if (player == null) {
			return;
		}

		if (BTRPT.isTooled(player.getUniqueId().toString())) {
			sourcePlayerData.setCancelled(true);
			BTRExecutorService.ThreadPool.execute(new Runnable() {
				public void run() {
					Thread.currentThread().setName("BTRPIBE");
					String X = String.valueOf(sourcePlayerData.getTargetBlock().getLocation().get().getBlockX());
					String Y = String.valueOf(sourcePlayerData.getTargetBlock().getLocation().get().getBlockY());
					String Z = String.valueOf(sourcePlayerData.getTargetBlock().getLocation().get().getBlockZ());
					BTRGR.getRecords(X, Y, Z, player);
				}
			});

		} else if (BlockTrackR.Track) {
			if (Arrays.asList(Blocks)
					.contains(sourcePlayerData.getTargetBlock().getLocation().get().getBlockType().getName())) {
				/*
				 * Initialize a String object with the name of the affected
				 * block.
				 */
				final String InteractionType = "Hopefully usage, I'll sort this out later.";

				/*
				 * Extrapolates the X,Y,and Z coordinates from the Player
				 * object.
				 */
				final int X = player.getTransform().getLocation().getBlockX();
				final int Y = player.getTransform().getLocation().getBlockY();
				final int Z = player.getTransform().getLocation().getBlockZ();

				/*
				 * Isolates the player's name and UUID from the MessageEvent
				 * object.
				 */
				final String PlayerUUID = player.getIdentifier();
				final String Player = player.getName();

				/*
				 * Extrapolates the world name from the Player object.
				 */
				final String world = player.getWorld().getName();

				/*
				 * Add to queue for insertion to SQL database.
				 */
				BTRExecutorService.ThreadPool.execute(new Runnable() {

					public void run() {
						// Name this thread for debug purposes.
						Thread.currentThread().setName("BTRPIBE");
						// Debug output controlled by switch in configuration
						// file.
						BTRDebugger.DLog("BTRPlayerInteractBlockEvent");
						BTRDebugger.DLog("InteractionType: " + InteractionType);
						BTRDebugger.DLog("Player: " + Player);
						BTRDebugger.DLog("PlayerUUID: " + PlayerUUID);
						BTRDebugger.DLog("X: " + X);
						BTRDebugger.DLog("Y: " + Y);
						BTRDebugger.DLog("Z: " + Z);
						BTRDebugger.DLog("World: " + world);

						// Insert to DB
						BTRSQL.insertPlayerInteract(Player, PlayerUUID, X, Y, Z, world, BlockTrackR.getTime(),
								InteractionType);
					}
				});
			}

		}
	}
}
