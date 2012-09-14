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

package de.minestar.vincicode.database;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import de.minestar.minestarlibrary.database.AbstractMySQLHandler;
import de.minestar.minestarlibrary.database.DatabaseUtils;
import de.minestar.minestarlibrary.messages.Message;
import de.minestar.minestarlibrary.messages.MessageType;
import de.minestar.minestarlibrary.utils.ConsoleUtils;
import de.minestar.vincicode.core.VinciCodeCore;
import de.minestar.vincicode.data.MailBox;

public class DatabaseHandler extends AbstractMySQLHandler {

    public DatabaseHandler(String pluginName, File SQLConfigFile) {
        super(pluginName, SQLConfigFile);
    }

    @Override
    protected void createStructure(String pluginName, Connection con) throws Exception {
        DatabaseUtils.createStructure(this.getClass().getResourceAsStream("/structure.sql"), con, pluginName);
    }

    @Override
    protected void createStatements(String pluginName, Connection con) throws Exception {
        addMessage = con.prepareStatement("INSERT INTO message (sender, receiver, timestamp, type, text, isRead) VALUES (?, ?, ?, ?, ?, ?)");

        deleteMessage = con.prepareStatement("DELETE FROM message WHERE timestamp = ? AND sender = ? AND receiver = ?");

        updateMessageRead = con.prepareStatement("UPDATE message SET isRead = ? WHERE timestamp = ? AND sender = ? AND receiver = ?");
    }

    // MESSAGES //
    private PreparedStatement addMessage;
    private PreparedStatement deleteMessage;
    private PreparedStatement updateMessageRead;

    public Map<String, MailBox> loadMailBoxes() {
        Map<String, MailBox> mailBoxMap = new HashMap<String, MailBox>();

        try {
            Statement st = dbConnection.getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT sender, receiver, timestamp, type, text, isRead FROM message ORDER BY receiver, timestamp");

            String sender;
            String receiver;
            long timestamp;
            MessageType type;
            String text;
            boolean isRead;

            MailBox mailBox;

            while (rs.next()) {
                sender = rs.getString(1);
                receiver = rs.getString(2);
                timestamp = rs.getLong(3);
                type = MessageType.get(rs.getInt(4));
                text = rs.getString(5);
                isRead = rs.getBoolean(6);

                mailBox = mailBoxMap.get(receiver.toLowerCase());
                if (mailBox == null) {
                    mailBox = new MailBox();
                    mailBoxMap.put(receiver.toLowerCase(), mailBox);
                }
                mailBox.add(new Message(sender, receiver, text, type, timestamp, isRead));

            }
        } catch (Exception e) {
            ConsoleUtils.printException(e, VinciCodeCore.NAME, "Can't load mailboxes from database!");
            mailBoxMap.clear();
        }

        return mailBoxMap;
    }

    public boolean addMessage(Message message) {
        try {
            addMessage.setString(1, message.getSender());
            addMessage.setString(2, message.getReceiver());
            addMessage.setLong(3, message.getTimestamp());
            addMessage.setInt(4, message.getType().ordinal());
            addMessage.setString(5, message.getText());
            addMessage.setBoolean(6, message.isRead());

            return addMessage.executeUpdate() == 1;
        } catch (Exception e) {
            ConsoleUtils.printException(e, VinciCodeCore.NAME, "Can't add message to database! Message=" + message);
            return false;
        }
    }

    public boolean deleteMessage(Message message) {
        try {
            deleteMessage.setLong(1, message.getTimestamp());
            deleteMessage.setString(2, message.getSender());
            deleteMessage.setString(3, message.getReceiver());
            return deleteMessage.executeUpdate() == 1;
        } catch (Exception e) {
            ConsoleUtils.printException(e, VinciCodeCore.NAME, "Can't delete message from database! Message =" + message);
            return false;
        }
    }

    public boolean setMessageRead(Message message) {
        try {
            updateMessageRead.setBoolean(1, message.isRead());
            updateMessageRead.setLong(2, message.getTimestamp());
            updateMessageRead.setString(3, message.getSender());
            updateMessageRead.setString(4, message.getReceiver());
            return updateMessageRead.executeUpdate() == 1;
        } catch (Exception e) {
            ConsoleUtils.printException(e, VinciCodeCore.NAME, "Can't set message read status to database! Message =" + message);
            return false;
        }
    }
}
