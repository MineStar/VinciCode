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

package de.minestar.vincicode.core;

import java.io.File;

import de.minestar.minestarlibrary.AbstractCore;
import de.minestar.minestarlibrary.commands.CommandList;
import de.minestar.minestarlibrary.messages.Message;
import de.minestar.vincicode.command.vinci.cmdMessage;
import de.minestar.vincicode.command.vinci.cmdVinci;
import de.minestar.vincicode.database.DatabaseHandler;
import de.minestar.vincicode.manager.MessageManager;

public class VinciCodeCore extends AbstractCore {
//public class VinciCodeCore extends AbstractCore implements Listener {

    public static final String NAME = "VinciCode";

    public static MessageManager messageManger;
    public static DatabaseHandler dbHandler;

    public VinciCodeCore() {
        super(NAME);
    }

    @Override
    protected boolean createManager() {

        dbHandler = new DatabaseHandler(NAME, new File(getDataFolder(), "sqlconfig.yml"));
        if (dbHandler == null || !dbHandler.hasConnection())
            return false;

        messageManger = new MessageManager();

        return true;
    }

    @Override
    protected boolean createCommands() {
        //@formatter:off
        this.cmdList = new CommandList(
                
                new cmdVinci(   "/vinci",       "",             "",
                
                        new cmdMessage(    "message",     "<Target> <Text>",     "vincicode.command.sendmessage")
                )
            );
                
        //@formatter:on
        return true;
    }

    @Override
    protected boolean commonDisable() {
        if (dbHandler != null)
            dbHandler.closeConnection();

        return !dbHandler.hasConnection();
    }
//
//    @Override
//    protected boolean registerEvents(PluginManager pm) {
//        pm.registerEvents(this, this);
//        return true;
//    }

//    @EventHandler
//    public void onPlayerRespawn(PlayerRespawnEvent event) {
//        List<String> pages = new ArrayList<String>();
//        pages.add(ChatColor.RED + "Willkommen auf " + ChatColor.BLUE + "Minestar.de" + ChatColor.RED + "!");
//        pages.add(ChatColor.RED + "Seite 2");
//        pages.add(ChatColor.RED + "Seite 3");
//        MinestarBook myBook = MinestarBook.createWrittenBook("AUTHOR", "TITLE", pages);
//        event.getPlayer().setItemInHand(myBook.getBukkitItemStack());
//    }

    public static void sendMessage(Message message) {
        messageManger.handleMessage(message);
    }
}
