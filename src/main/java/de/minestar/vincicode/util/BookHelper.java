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
import java.util.List;

import org.bukkit.ChatColor;

import de.minestar.minestarlibrary.messages.Message;
import de.minestar.vincicode.formatter.DefaultFormat;
import de.minestar.vincicode.formatter.MessageFormat;

public class BookHelper {

    public final static int CHARS_PER_PAGE = 256;

    // FORMAT MESSAGES
    public final static DateFormat DATE = DateFormat.getDateInstance();
    public final static DateFormat TIME = DateFormat.getTimeInstance();

    public static void appendText(StringBuilder stringBuilder, ChatColor chatColor, String extraFormat, String text) {
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

        }
    }

    public static void appendColoredText(StringBuilder sBuilder, ChatColor color, String text) {
        sBuilder.append(color);
        sBuilder.append(text);
        sBuilder.append(ChatColor.BLACK);
    }

    public static char LINE_SEPARATOR = '\n';

    public static void newLine(StringBuilder sBuilder) {
        sBuilder.append(LINE_SEPARATOR);
    }

    public static void emptyLine(StringBuilder sBuilder) {
        sBuilder.append("§r");
        sBuilder.append(LINE_SEPARATOR);
    }

    public static List<String> format(Message message) {
        return format(message, DefaultFormat.getInstance());
    }

    public static List<String> format(Message message, MessageFormat format) {
        return format.format(message);
    }
}
