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
import java.util.regex.Pattern;

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

        // TODO: Implement a method without a unneccessary split
        // Use instead a search for space and substrings(mel has worked on one,
        // but it was ugly as hell)
        // Will be very more performant and use less memory
        ArrayList<String> words = BookHelper.getWords(message.getMessage());
        int count = 1;
        for (String word : words) {
            // MORE WORDS THAT CAN FIT ON ONE PAGE
            if (stringBuilder.length() + word.length() > 256) {
                // STORE CURRENT PAGE
                pages.add(stringBuilder.toString());
                // RESET STRING BUILDER
                stringBuilder.setLength(0);
                stringBuilder.setLength(256);
            }
            // APPEND WORD
            stringBuilder.append(word);
            // TODO : WHY WE CHECK THIS?!?
            if (count < words.size()) {
                stringBuilder.append(' ');
            }
        }

        if (stringBuilder.length() > 0) {
            pages.add(stringBuilder.toString());
        }
        return pages;
    }

    private final static Pattern SPLIT_SPACE = Pattern.compile(" ");

    private static ArrayList<String> getWords(String text) {
        ArrayList<String> words = new ArrayList<String>();
        String[] split = SPLIT_SPACE.split(text);
        for (int i = 0; i < split.length; ++i)
            words.add(split[i]);

        return words;
    }
}
