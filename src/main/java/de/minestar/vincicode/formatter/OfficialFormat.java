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

package de.minestar.vincicode.formatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.ChatColor;

import de.minestar.minestarlibrary.messages.Message;
import de.minestar.vincicode.util.BookHelper;

public class OfficialFormat implements MessageFormat {

    private static OfficialFormat instance;

    public static OfficialFormat getInstance() {
        if (instance == null)
            instance = new OfficialFormat();

        return instance;
    }

    private OfficialFormat() {

    }

    @Override
    public List<String> format(Message message) {
        StringBuilder sBuilder = formatHead(message);
        return formatBody(sBuilder, message);
    }

    @Override
    public StringBuilder formatHead(Message message) {
        StringBuilder stringBuilder = new StringBuilder(BookHelper.CHARS_PER_PAGE);

        BookHelper.appendColoredText(stringBuilder, ChatColor.DARK_RED, "Offiziel");
        BookHelper.newLine(stringBuilder);

        // append sender
        BookHelper.appendColoredText(stringBuilder, ChatColor.DARK_GREEN, "Absender: ");
        stringBuilder.append(message.getSender());
        BookHelper.newLine(stringBuilder);

        // append date
        BookHelper.appendColoredText(stringBuilder, ChatColor.DARK_GREEN, "Datum: ");
        stringBuilder.append(BookHelper.DATE.format(new Date(message.getTimestamp())));
        BookHelper.newLine(stringBuilder);

        // append time
        BookHelper.appendColoredText(stringBuilder, ChatColor.DARK_GREEN, "Uhrzeit: ");
        stringBuilder.append(BookHelper.TIME.format(new Date(message.getTimestamp())));
        BookHelper.newLine(stringBuilder);

        // append empty line
        BookHelper.emptyLine(stringBuilder);

        return stringBuilder;
    }

    @Override
    public List<String> formatBody(StringBuilder sBuilder, Message message) {

        List<String> pages = new ArrayList<String>();
        // MAYBE SPLIT MESSAGE INTO MULTIPLE PAGES?
        sBuilder.append(message.getMessage());
        // NO SPLIT
        if (sBuilder.length() < BookHelper.CHARS_PER_PAGE) {
            pages.add(sBuilder.toString());
        } else {
            // SPLIT BY SPACE
            while (sBuilder.length() >= BookHelper.CHARS_PER_PAGE) {
                // SEARCH FOR FIRST SPACE
                int index = BookHelper.CHARS_PER_PAGE - 1;
                for (; index >= 0; --index) {
                    if (sBuilder.charAt(index) == ' ') {
                        break;
                    }
                }
                // NO SPACE FOUND -> SPLIT AT FIXED POSITION
                if (index == -1) {
                    pages.add(sBuilder.substring(0, index));
                    sBuilder = new StringBuilder(sBuilder.substring(BookHelper.CHARS_PER_PAGE - 1));
                    // FIRST CHAr OF NEXT PAGE IS CAPITAL LETTER
                    if (sBuilder.length() > 0)
                        sBuilder.setCharAt(0, Character.toLowerCase(sBuilder.charAt(0)));
                }
                // SPLIT BY SPACE
                else {
                    pages.add(sBuilder.substring(0, index));
                    sBuilder = new StringBuilder(sBuilder.substring(index + 1));
                    // FIRST CHAr OF NEXT PAGE IS CAPITAL LETTER
                    if (sBuilder.length() > 0)
                        sBuilder.setCharAt(0, Character.toLowerCase(sBuilder.charAt(0)));
                }
            }
            // ADD LAST PAGE
            if (sBuilder.length() != 0)
                pages.add(sBuilder.toString());
        }

        return pages;
    }

}
