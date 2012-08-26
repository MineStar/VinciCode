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

import org.bukkit.plugin.PluginManager;

import de.minestar.minestarlibrary.AbstractCore;
import de.minestar.minestarlibrary.commands.CommandList;
import de.minestar.minestarlibrary.messages.Message;
import de.minestar.minestarlibrary.stats.StatisticHandler;
import de.minestar.vincicode.command.cmdMailbox;
import de.minestar.vincicode.command.cmdMessage;
import de.minestar.vincicode.command.cmdReply;
import de.minestar.vincicode.database.DatabaseHandler;
import de.minestar.vincicode.listener.ActionListener;
import de.minestar.vincicode.manager.MessageManager;
import de.minestar.vincicode.statistic.MailBoxStat;
import de.minestar.vincicode.statistic.WhisperStat;

public class VinciCodeCore extends AbstractCore {
//public class VinciCodeCore extends AbstractCore implements Listener {

    public static final String NAME = "VinciCode";

    public static MessageManager messageManger;
    public static DatabaseHandler dbHandler;

    public static ActionListener actionListener;

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

                new cmdMessage(     "/message",      "<Target> <Text>",     "vincicode.command.sendmessage"),
                new cmdMessage(     "/m",            "<Target> <Text>",     "vincicode.command.sendmessage"),
                new cmdMessage(     "/w",            "<Target> <Text>",     "vincicode.command.sendmessage"),
                new cmdMessage(     "/tell",         "<Target> <Text>",     "vincicode.command.sendmessage"),
                
                new cmdReply(       "/r",            "<Target> <Text>",     "vincicode.command.reply"),
                new cmdReply(       "/reply",        "<Target> <Text>",     "vincicode.command.reply"),
                
                new cmdMailbox(     "/mailbox",     "",                     "vincicode.command.mailbox")
                
            );
                
        //@formatter:on
        return true;
    }

    @Override
    protected boolean createListener() {
        actionListener = new ActionListener();
        return true;
    }

    @Override
    protected boolean registerStatistics() {
        StatisticHandler.registerStatistic(WhisperStat.class);
        StatisticHandler.registerStatistic(MailBoxStat.class);
        return true;
    }

    @Override
    protected boolean registerEvents(PluginManager pm) {
        pm.registerEvents(actionListener, this);
        return true;
    }

    @Override
    protected boolean commonDisable() {
        if (dbHandler != null)
            dbHandler.closeConnection();

        return !dbHandler.hasConnection();
    }

    public static void sendMessage(Message message) {
        messageManger.handleMessage(message);
    }
}
