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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.minestar.minestarlibrary.bookapi.MinestarBook;
import de.minestar.minestarlibrary.messages.Message;
import de.minestar.minestarlibrary.messages.MessageComparator;

public class MailBox {

    private static final MessageComparator comparator = new MessageComparator();
    public static final String MAIL_BOX_HEAD = "Ugly Post";

    public static int findMailBox(Player player) {
        Inventory inventory = player.getInventory();

        ItemStack[] stacks = inventory.getContents();
        for (int i = 0; i < stacks.length; i++) {
            ItemStack itemStack = stacks[i];
            if (itemStack != null && itemStack.getType().equals(Material.WRITTEN_BOOK)) {
                MinestarBook book = MinestarBook.loadBook(itemStack);
                if (book.getAuthor().equalsIgnoreCase(MailBox.MAIL_BOX_HEAD))
                    return i;
            }
        }
        return -1;
    }

    private int index = 0;
    private List<Message> mailBox;

    private boolean newMessages;

    // NEW MAIL BOX
    public MailBox() {
        mailBox = new ArrayList<Message>();
        this.newMessages = false;
    }

    public boolean hasNext() {
        return index >= 0 && index < this.mailBox.size() - 1;
    }

    public Message next() {
        if (this.hasNext()) {
            this.setIndex(this.index + 1);
            return this.mailBox.get(index);
        }
        return null;
    }

    public boolean hasPrev() {
        return index > 0 && index < this.mailBox.size();
    }

    public Message prev() {
        if (this.hasPrev()) {
            this.setIndex(this.index - 1);
            return this.mailBox.get(index);
        }
        return null;
    }

    public void add(Message message) {
        mailBox.add(message);
        this.newMessages = this.newMessages || !message.isRead();
        Collections.sort(mailBox, MailBox.comparator);
    }

    public void deleteCurrentMessage() {
        if (this.index >= 0 && this.index < this.mailBox.size()) {
            mailBox.remove(this.index);
            if (this.index >= this.mailBox.size()) {
                this.setIndex(this.mailBox.size() - 1);
            }
        }
    }

    public Message getCurrentMessage() {
        if (this.index >= 0 && this.index < this.mailBox.size()) {
            return this.mailBox.get(this.index);
        }
        return null;
    }

    public boolean hasNewMessages() {
        return this.getNewMessageCount() > 0;
    }

    public void markAsRead(Message message) {
        message.setRead(true);
        searchForNewMessages();
    }

    public void searchForNewMessages() {
        for (Message message : mailBox) {
            if (!message.isRead()) {
                this.newMessages = true;
                break;
            }
        }
    }

    public List<Message> getAllMessages() {
        return new ArrayList<Message>(mailBox);
    }

    public int getMessageCount() {
        return this.mailBox.size();
    }

    public void setIndex(int index) {
        if (this.index >= 0 && this.index < this.mailBox.size()) {
            this.index = index;
        } else {
            this.index = 0;
        }
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        if (mailBox.isEmpty())
            return "[]";
        StringBuilder sBuilder = new StringBuilder(mailBox.size() * 32);
        sBuilder.append('[');
        for (Message msg : mailBox) {
            sBuilder.append(msg);
            sBuilder.append(", ");
        }

        sBuilder.deleteCharAt(sBuilder.length() - 1);
        sBuilder.setCharAt(sBuilder.length() - 1, ']');

        return sBuilder.toString();
    }

    public int getNewMessageCount() {
        int count = 0;
        for (Message message : mailBox) {
            if (!message.isRead()) {
                count++;
            }
        }
        return count;
    }
}
