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

package de.minestar.vincicode.util;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.ChatColor;

import de.minestar.minestarlibrary.messages.Message;

public class BookHelper {

    // FORMAT MESSAGES
    private final static DateFormat DATE = DateFormat.getDateInstance();
    private final static DateFormat TIME = DateFormat.getTimeInstance();

    private static void appendText(StringBuilder stringBuilder, ChatColor chatColor, String extraFormat, String text) {
        // set color
        if (chatColor != null) {
            stringBuilder.append(chatColor);
        }

        // set style
        if (extraFormat != null) {
            stringBuilder.append(extraFormat);
        }
        // append text
        stringBuilder.append(text);

        // reset style
        if (extraFormat != null) {
            stringBuilder.append("§r");
        }
        if (chatColor != null) {
            stringBuilder.append(ChatColor.BLACK);
        }
    }

    private final static int CHARS_PER_PAGE = 256;

    public static List<String> getPages(Message message) {

        List<String> pages = new ArrayList<String>();

        StringBuilder stringBuilder = new StringBuilder(CHARS_PER_PAGE);

        // append sender
        BookHelper.appendText(stringBuilder, ChatColor.DARK_GREEN, null, "Absender: ");
        stringBuilder.append(message.getSender());
        stringBuilder.append("\n");

        // append date
        BookHelper.appendText(stringBuilder, ChatColor.DARK_GREEN, null, "Datum: ");
        stringBuilder.append(DATE.format(new Date(message.getTimestamp())));
        stringBuilder.append("\n");

        // append time
        BookHelper.appendText(stringBuilder, ChatColor.DARK_GREEN, null, "Uhrzeit: ");
        stringBuilder.append(TIME.format(new Date(message.getTimestamp())));
        stringBuilder.append("\n");

        // append empty line
        stringBuilder.append("§r");
        stringBuilder.append("\n");

        // MAYBE SPLIT MESSAGE INTO MULTIPLE PAGES?
        stringBuilder.append(message.getMessage());
        // NO SPLIT
        if (stringBuilder.length() < CHARS_PER_PAGE) {
            pages.add(stringBuilder.toString());
        } else {
            // SPLIT BY SPACE
            while (stringBuilder.length() >= CHARS_PER_PAGE) {
                // SEARCH FOR FIRST SPACE
                int index = CHARS_PER_PAGE - 1;
                for (; index >= 0; --index) {
                    if (stringBuilder.charAt(index) == ' ') {
                        break;
                    }
                }
                // NO SPACE FOUND -> SPLIT AT FIXED POSITION
                if (index == -1) {
                    pages.add(stringBuilder.substring(0, index));
                    stringBuilder = new StringBuilder(stringBuilder.substring(CHARS_PER_PAGE - 1));
                }
                // SPLIT BY SPACE
                else {
                    pages.add(stringBuilder.substring(0, index));
                    stringBuilder = new StringBuilder(stringBuilder.substring(index + 1));
                }
            }
            // ADD LAST PAGE
            if (stringBuilder.length() != 0)
                pages.add(stringBuilder.toString());
        }

        return pages;
    }
}
