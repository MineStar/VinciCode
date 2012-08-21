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

package de.minestar.vincicode.manager;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.minestar.minestarlibrary.messages.Message;
import de.minestar.minestarlibrary.utils.ConsoleUtils;
import de.minestar.minestarlibrary.utils.PlayerUtils;
import de.minestar.vincicode.core.VinciCodeCore;
import de.minestar.vincicode.data.MailBox;

public class MessageManager {

    private Map<String, MailBox> mailBoxMap = new HashMap<String, MailBox>();

    public MessageManager() {
        loadMailBoxes();
    }

    private void loadMailBoxes() {
        mailBoxMap = VinciCodeCore.dbHandler.loadMailBoxes();
        ConsoleUtils.printInfo(VinciCodeCore.NAME, "Loaded " + mailBoxMap.size() + " mail boxes!");
    }

    public void handleMessage(Message message) {
        String targetName = message.getTarget();
        Player player = Bukkit.getPlayerExact(targetName);
        if (player != null && player.isOnline()) {
            PlayerUtils.sendBlankMessage(player, message.getCompleteMessage());
        } else {
            MailBox mailBox = mailBoxMap.get(targetName.toLowerCase());

            // TODO: Implement an else statement or create a new mailbox for
            // every new user
            if (mailBox != null)
                mailBox.add(message);
        }
    }

    public boolean hasNewMessage(String player) {
        return false;
    }
}
