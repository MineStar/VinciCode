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

package de.minestar.vincicode.command.vinci;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import de.minestar.minestarlibrary.commands.AbstractExtendedCommand;
import de.minestar.minestarlibrary.messages.Message;
import de.minestar.minestarlibrary.utils.ChatUtils;
import de.minestar.minestarlibrary.utils.PlayerUtils;
import de.minestar.vincicode.core.VinciCodeCore;

public class cmdMessage extends AbstractExtendedCommand {

    public cmdMessage(String syntax, String arguments, String node) {
        super(VinciCodeCore.NAME, syntax, arguments, node);
    }

    @Override
    public void execute(String[] args, Player player) {
        sendMessage(player, args);
    }

    @Override
    public void execute(String[] args, ConsoleCommandSender console) {
        sendMessage(console, args);
    }

    private void sendMessage(CommandSender commandSender, String[] args) {

        String target = PlayerUtils.getCorrectPlayerName(args[0]);
        if (target == null) {
            ChatUtils.writeError(commandSender, pluginName, "Spieler '" + args[0] + "' nicht gefunden!");
            return;
        }
        String text = ChatUtils.getMessage(args, 1);
        String sender = "";
        if (commandSender instanceof ConsoleCommandSender)
            sender = "Console";
        else if (commandSender instanceof Player)
            sender = ((Player) commandSender).getName();

        Message msg = new Message(sender, target, ChatColor.WHITE, text);
        VinciCodeCore.messageManger.handleMessage(msg);
    }

}
