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

import org.spongepowered.api.Server;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

import com.google.common.base.Optional;

public class BTRMainCommand implements CommandCallable {

	@SuppressWarnings("unused")
	private Server server;

	Player p;

	public BTRMainCommand(Server server) {
		this.server = server;
	}

	BTRVersionCheckCommand BTRVCC = new BTRVersionCheckCommand();
	BTRDebugCommand BTRDC = new BTRDebugCommand();
	BTRRetriveCommand BTRRC = new BTRRetriveCommand();

	@Override
	public Optional<CommandResult> process(CommandSource cs, String arguments)
			throws CommandException {
		String[] args = arguments.split("\\s|\\s+");
		if (args[0].equals("debug")) {
			CommandSource css = cs;
			BTRDC.ToggleDebug(css);
		} else if (args[0].equals("version")) {
			BTRVCC.VersionCheckCommand(cs);
		} else if (args[0].equals("retrive")) {
			BTRRC.ToggleDebug(cs, args);
		} else {
			cs.sendMessage(Texts.of("BTR Commands:", TextColors.RED));
			cs.sendMessage(Texts.of("/BTR version - Check for updates.",
					TextColors.RED));
			cs.sendMessage(Texts.of("/BTR debug - Toggle debug output.",
					TextColors.RED));
			cs.sendMessage(Texts
					.of("/BTR retrive [x] [y] [z] - Retrive records at X Y Z coordaintes.",
							TextColors.RED));
		}
		return Optional.of(CommandResult.success());
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
		// TODO Auto-generated method stub
		return false;
	}

}