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

package de.minestar.vincicode.data;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import de.minestar.minestarlibrary.messages.Message;

public class MailBox {

    private LinkedList<Message> mailBox;
    private ListIterator<Message> iter;

    private boolean newMessages;

    // NEW MAIL BOX
    public MailBox() {
        mailBox = new LinkedList<Message>();
        iter = mailBox.listIterator();

        this.newMessages = false;
    }

    // MAIL BOX FROM DATABASE
    public MailBox(List<Message> messages) {
        mailBox = new LinkedList<Message>(messages);
        iter = mailBox.listIterator();

        searchForNewMessages();
    }

    public boolean hasNext() {
        return iter.hasNext();
    }

    public Message next() {
        return iter.next();
    }

    public boolean hasPrev() {
        return iter.hasPrevious();
    }

    public Message prev() {
        return iter.previous();
    }

    public void add(Message message) {
        // HAS TO RECREATE THE ITERATOR
        int pos = iter.previousIndex();

        mailBox.add(message);
        iter = mailBox.listIterator(pos + 1);
    }

    public void deleteCurrent() {
        iter.remove();
    }

    public boolean hasNewMessages() {
        return newMessages;
    }

    public void markAsRead(Message message) {
        message.setRead(false);
        searchForNewMessages();
    }

    private void searchForNewMessages() {
        for (Message message : mailBox) {
            if (!message.isRead()) {
                this.newMessages = true;
                break;
            }
        }
    }

    public List<Message> getAllMessages() {
        return new LinkedList<Message>(mailBox);
    }

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder(mailBox.size() * 32);
        sBuilder.append('[');
        for (Message msg : mailBox) {
            sBuilder.append(msg.getCompleteMessage());
            sBuilder.append(", ");
        }

        sBuilder.deleteCharAt(sBuilder.length() - 1);
        sBuilder.setCharAt(sBuilder.length() - 1, ']');

        return sBuilder.toString();
    }
}
