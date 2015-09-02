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

import java.util.Arrays;
import java.util.List;

import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

import com.google.common.base.Optional;

public class BTRMainCommand implements CommandCallable {

	private Server server;
	private Game game;

	Player p;

	public BTRMainCommand(Server server, Game game) {
		this.server = server;
		this.game = game;
	}

	BTRVersionCheckCommand BTRVCC = new BTRVersionCheckCommand();
	BTRDebugCommand BTRDC = new BTRDebugCommand();
	BTRRetrieveCommand BTRRC = new BTRRetrieveCommand();
	BTRAuthCommand BTRAC = new BTRAuthCommand();
	BTRToolCommand BTRTC = new BTRToolCommand();

	@Override
	public CommandResult process(CommandSource cs, String arguments)
			throws CommandException {
		String[] args = arguments.split("\\s|\\s+");
		switch (args[0]) {
		case "version":
			BTRVCC.VersionCheckCommand(cs);
			break;
		case "retrieve":
			BTRRC.retrieveCommand(cs, args);
			break;
		case "debug":
			BTRDC.ToggleDebug(cs);
			break;
		case "auth":
			BTRAC.authCommand(cs, args, server);
			break;
		case "tool":
			BTRTC.giveTool(game, cs, args);
			break;
		default:
			cs.sendMessage(Texts.of(TextColors.RED, "BTR Commands:"));
			cs.sendMessage(Texts.of(TextColors.GREEN,
					"/BTR version - Check for updates."));
			cs.sendMessage(Texts.of(TextColors.GREEN,
					"/BTR debug - Toggle debug output."));
			cs.sendMessage(Texts.of(TextColors.GREEN,
					"/BTR tool - Enables or disables tool mode on you."));
			cs.sendMessage(Texts
					.of(TextColors.GREEN,
							"/BTR retrieve [x] [y] [z] - Retrive records at the specified X,Y,Z coordinsate."));
			cs.sendMessage(Texts.of(TextColors.GREEN,
					"/BTR auth [Playername] - Player must be online."));
			break;
		}
		return CommandResult.success();
	}

	@Override
	public Optional<Text> getHelp(CommandSource source) {
		return Optional
				.of(Texts.of("Test Command get Help.", TextColors.WHITE));
	}

	@Override
	public Optional<Text> getShortDescription(CommandSource source) {
		return Optional.of(Texts.of("Test Command Short Desc.",
				TextColors.WHITE));
	}

	@Override
	public List<String> getSuggestions(CommandSource source, String arguments)
			throws CommandException {
		List<String> l = Arrays.asList("SuggA", "SuggB", "SuggC");
		return l;
	}

	@Override
	public Text getUsage(CommandSource source) {
		return Texts.of("Test Command Usage.", TextColors.WHITE);
	}

	@Override
	public boolean testPermission(CommandSource source) {
		if (source.hasPermission("test.test")) {
			source.sendMessage(Texts.of("Perm - True"));
			return true;
		}
		source.sendMessage(Texts.of("Perm - False"));
		return false;
	}

}