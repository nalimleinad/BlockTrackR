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

import com.Volition21.BlockTrackR.Utility.BTRExecutorService;
import com.Volition21.BlockTrackR.Utility.BTROperatorCheck;
import com.google.common.base.Optional;

public class BTRAuthCommand {

	BTROperatorCheck BTROC = new BTROperatorCheck();

	public void authCommand(final CommandSource cs, final String[] args,
			final Server server) {
		BTRExecutorService.ThreadPool.execute(new Runnable() {

			public void run() {
				String PlayerName = null;
				Thread.currentThread().setName("BTRAC");
				try {
					PlayerName = args[1];
				} catch (IndexOutOfBoundsException e) {
					cs.sendMessage(Texts.of(TextColors.RED,
							"Inncorrect Syntax."));
					cs.sendMessage(Texts.of(TextColors.RED,
							"/BTR auth [Playername] - Player must be online."));
				}
				if (PlayerName != null) {
					Optional<Player> player = server.getPlayer(PlayerName);
					if (player.isPresent()) {
						Player p = player.get();
						String UUID = p.getUniqueId().toString();
						if (BTROC.isOP(UUID)) {
							cs.sendMessage(Texts.of(TextColors.GREEN,
									"Player IS OP"));
						} else {
							cs.sendMessage(Texts.of(TextColors.RED,
									"Player is NOT OP"));
						}
					} else {
						cs.sendMessage(Texts.of(TextColors.RED, PlayerName
								+ " is not online."));
					}

				}
			}
		});
	}
}
