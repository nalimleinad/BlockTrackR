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
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import com.Volition21.BlockTrackR.BTRVersionCheck;

public class TestCommand implements CommandExecutor {

	@SuppressWarnings("unused")
	private Server server;

	public TestCommand(Server server) {
		this.server = server;
	}

	BTRVersionCheck BTRvc = new BTRVersionCheck();

	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {
		// server.broadcastMessage(Texts.of(BTRvc.getJSONasString("description")));
		// forces a version check.
		// This will probably become the version checking command.
		BTRvc.versionCheck();

		return CommandResult.success();
	}

}