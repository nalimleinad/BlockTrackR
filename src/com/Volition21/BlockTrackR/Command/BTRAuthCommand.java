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

import org.spongepowered.api.Server;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.source.ConsoleSource;

import com.Volition21.BlockTrackR.BlockTrackR;
import com.Volition21.BlockTrackR.Utility.BTRConfiguration;
import com.Volition21.BlockTrackR.Utility.BTRDebugger;
import com.Volition21.BlockTrackR.Utility.BTRExecutorService;
import com.Volition21.BlockTrackR.Utility.BTRPermissionCheck;
import com.google.common.base.Optional;

public class BTRAuthCommand {

	BTRPermissionCheck BTROC = new BTRPermissionCheck();
	BTRConfiguration BTRC = new BTRConfiguration();
	BTRPermissionCheck BTRPC = new BTRPermissionCheck();

	public void authCommand(final CommandSource cs, final String[] args,
			final Server server) {
		BTRDebugger.DLog("authCommand - preAuth");
		if (BTRPC.isOPOrConsole(cs)) {
			BTRDebugger.DLog("authCommand - isAuthed");
			BTRExecutorService.ThreadPool.execute(new Runnable() {
				public void run() {
					String PlayerName = null;
					Thread.currentThread().setName("BTRAC");
					try {
						PlayerName = args[1];
					} catch (IndexOutOfBoundsException e) {
						cs.sendMessage(Texts.of(TextColors.RED,
								"Inncorrect Syntax."));
						cs.sendMessage(Texts
								.of(TextColors.RED,
										"/BTR auth [Playername] - Player must be online."));
					}
					if (PlayerName != null) {
						Optional<Player> player = server.getPlayer(PlayerName);
						if (player.isPresent()) {
							if (cs instanceof Player) {
								if (BTROC.isOP(((Player) cs).getIdentifier()
										.toString())) {
									authorizeUser(cs, player.get());
								} else {
									cs.sendMessage(Texts
											.of(TextColors.RED,
													"BTR authorization requires OP privilages."));
								}
							} else if (cs instanceof ConsoleSource) {
								authorizeUser(cs, player.get());
							} else {
								cs.sendMessage(Texts
										.of("Command cannot be called by anything other than ConsoleSource or Player"));
							}
						} else {
							cs.sendMessage(Texts.of(TextColors.RED, PlayerName
									+ " is not online."));
						}

					}
				}
			});
		} else {
			BTRDebugger.DLog("authCommand - notAuthed");
		}
	}

	/**
	 * Adds or removes a user from the list of authorized users.
	 * 
	 * @param cs The CommandSource.
	 * @param player The player to add or remove as an authorized user.
	 */
	public void authorizeUser(CommandSource cs, Player player) {
		String PlayerName = player.getName();
		int status = BTRC.authorizeUser("authorized_players", player
				.getUniqueId().toString());

		if (status == 1) {
			cs.sendMessage(Texts.of(TextColors.GREEN, PlayerName
					+ " Added to the list of authorized users."));
		} else if (status == 2) {
			cs.sendMessage(Texts.of(TextColors.RED, PlayerName
					+ " Removed from the list of authorized users."));
		} else if ((status != 1) || (status != 2)) {
			cs.sendMessage(Texts
					.of("There was an error of somekind, please inform your administrator or Volition21."));
			BlockTrackR.logger
					.info("There was an error, setConfigValue has returned a value other than 1 or 2");
		}
	}
}
