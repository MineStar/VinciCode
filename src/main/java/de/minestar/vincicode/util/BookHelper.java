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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bukkit.ChatColor;

import de.minestar.minestarlibrary.messages.Message;

public class BookHelper {

    // FORMAT MESSAGES
    private final static SimpleDateFormat DATE = new SimpleDateFormat("dd.MM.yyyy");
    private final static SimpleDateFormat TIME = new SimpleDateFormat("HH:mm:ss");

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

    public static ArrayList<String> getPages(Message message) {
        ArrayList<String> pages = new ArrayList<String>();

        StringBuilder stringBuilder = new StringBuilder(256);

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

        // append text
        String text = stringBuilder.toString();
        ArrayList<String> words = BookHelper.getWords(message.getMessage());
        int count = 1;
        for (String word : words) {
            if (text.length() + word.length() > 256) {
                pages.add(text);
                text = "";
            }
            text += word;
            if (count < words.size()) {
                text += " ";
            }
        }
        if (text.length() > 0) {
            pages.add(text);
        }
        return pages;
    }

    private static ArrayList<String> getWords(String text) {
        ArrayList<String> words = new ArrayList<String>();
        String[] split = text.split(" ");
        for (String txt : split) {
            words.add(txt);
        }
        return words;
    }
}
