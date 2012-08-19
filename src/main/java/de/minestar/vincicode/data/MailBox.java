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

import de.minestar.minestarlibrary.messages.InfoMessage;
import de.minestar.minestarlibrary.messages.Message;
import de.minestar.minestarlibrary.messages.OfficialMessage;
import de.minestar.minestarlibrary.messages.SuccessMessage;

public class MailBox {

    private LinkedList<Message> mailBox;
    private ListIterator<Message> iter;

    // NEW MAIL BOX
    public MailBox() {
        mailBox = new LinkedList<Message>();
        iter = mailBox.listIterator();
    }

    // MAIL BOX FROM DATABASE
    public MailBox(List<Message> messages) {
        mailBox = new LinkedList<Message>(messages);
        iter = mailBox.listIterator();
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

    // JUST SOME TESTS - THEY WILL FLY AWAY
    public static void main(String[] args) {
        MailBox box = new MailBox();
        Message msg1 = new OfficialMessage("VinciCode", "Meldanor", "Deine erste Nachricht!");
        Message msg2 = new SuccessMessage("VinciCode", "Falake", "Der Mel hat seine erste Nachricht erhalten!");

        box.add(msg1);

        System.out.println("Added msg1 ");
        System.out.println(box);
        box.add(msg2);
        System.out.println("Added msg2 ");
        System.out.println(box);

        System.out.println(box.hasNext());
        System.out.println(box.next().getCompleteMessage());
        System.out.println(box.hasPrev());

        Message msg3 = new InfoMessage("VinciCode", "GeMoschen", "Schau, eine dritte Nachricht!");

        box.add(msg3);
        System.out.println(box.hasNext());
        System.out.println(box.next().getCompleteMessage());
    }
}
