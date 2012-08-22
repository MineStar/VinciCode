/*
 * Copyright (C) 2012 MineStar.de 
 * 
 * This file is part of VinciCode.
 * 
 * VinciCode is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 * 
 * VinciCode is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with VinciCode.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.minestar.vincicode.command;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.minestar.minestarlibrary.commands.AbstractCommand;
import de.minestar.minestarlibrary.utils.PlayerUtils;
import de.minestar.vincicode.core.VinciCodeCore;

public class cmdMailbox extends AbstractCommand {

    public cmdMailbox(String syntax, String arguments, String node) {
        super(VinciCodeCore.NAME, syntax, arguments, node);
    }

    @Override
    public void execute(String[] args, Player player) {

        if (hasMailBoxInInv(player)) {
            PlayerUtils.sendError(player, pluginName, "Du hast bereits eine MailBox in deinem Inventar");
            return;
        }

        if (inventoryIsFull(player)) {
            PlayerUtils.sendError(player, pluginName, "Deine Inventar ist voll!");
            return;
        }

        ItemStack mailBoxItem = VinciCodeCore.messageManger.getMailBoxItem(player.getName());
        player.setItemInHand(mailBoxItem);
        PlayerUtils.sendSuccess(player, pluginName, "Deine Mailbox");
    }

    private boolean hasMailBoxInInv(Player player) {
        return player.getInventory().contains(Material.WRITTEN_BOOK);
    }

    private boolean inventoryIsFull(Player player) {
        return player.getInventory().firstEmpty() == -1;
    }

}
