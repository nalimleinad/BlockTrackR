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
package com.Volition21.BlockTrackR.Tool;

import org.spongepowered.api.Game;
import org.spongepowered.api.data.manipulator.DisplayNameData;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Texts;

public class BTRItemManipulation {

	/**
	 * Creates an ItemStack with a custom name.
	 * 
	 * @param name
	 *            The custom name for this item.
	 * @param itemtype
	 *            The type of item. EX: ItemTypes.LOG
	 * @return Returns an ItemStack of the requested ItemType with the Custom
	 *         Name.
	 */
	public ItemStack createCustomItem(Game game, String name, ItemType itype) {
		ItemStack specialItem = game.getRegistry().getItemBuilder()
				.itemType(itype).build();
		DisplayNameData nameData = specialItem.getOrCreate(
				DisplayNameData.class).get();
		specialItem.offer(nameData.setDisplayName(Texts.of(name)));
		specialItem.setQuantity(1);
		return specialItem;
	}

}
