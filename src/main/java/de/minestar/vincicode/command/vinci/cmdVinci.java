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

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import de.minestar.minestarlibrary.commands.AbstractCommand;
import de.minestar.minestarlibrary.commands.AbstractSuperCommand;
import de.minestar.vincicode.core.VinciCodeCore;

public class cmdVinci extends AbstractSuperCommand {

    public cmdVinci(String syntax, String arguments, String node, AbstractCommand... subCommands) {
        super(VinciCodeCore.NAME, syntax, arguments, node, subCommands);
    }

    @Override
    public void execute(String[] args, Player player) {
        // DO NOTHING
    }

    @Override
    public void execute(String[] args, ConsoleCommandSender console) {
        // DO NOTHING
    }

}
