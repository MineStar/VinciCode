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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.minestar.minestarlibrary.messages.Message;
import de.minestar.minestarlibrary.messages.OfficialMessage;
import de.minestar.minestarlibrary.utils.ConsoleUtils;
import de.minestar.vincicode.core.VinciCodeCore;
import de.minestar.vincicode.util.BookHelper;

public class Formatter {

    private static Map<Class<? extends Message>, MessageFormat> formatMap = new HashMap<Class<? extends Message>, MessageFormat>();
    private static Map<Class<? extends MessageFormat>, MessageFormat> instanceMap = new HashMap<Class<? extends MessageFormat>, MessageFormat>();

    static {
        formatMap.put(Message.class, getInstance(DefaultFormat.class));

        formatMap.put(OfficialMessage.class, getInstance(OfficialFormat.class));
    }

    private static MessageFormat getInstance(Class<? extends MessageFormat> format) {
        MessageFormat instance = instanceMap.get(format);
        if (instance == null) {
            try {
                instance = format.newInstance();
                instanceMap.put(format, instance);
            } catch (Exception e) {
                ConsoleUtils.printException(e, VinciCodeCore.NAME, "Can't create an instance of the message format '" + format.getClass() + "'!");
            }

        }
        return instance;
    }

    public static List<String> format(Message message) {
        MessageFormat format = formatMap.get(message.getClass());
        if (format == null) {
            format = getInstance(DefaultFormat.class);
            if (format == null) {
                List<String> list = new LinkedList<String>();
                list.add("Fehler beim Laden des Standard Formats!");
                return list;
            } else
                ConsoleUtils.printError(VinciCodeCore.NAME, "The message class '" + message.getClass() + "' has no formatter! Using default formatter!");
        }

        StringBuilder sBuilder = new StringBuilder(format.formatHead(message));
        sBuilder.append(format.formatBody(message));

        return getPages(sBuilder);

    }

    private static List<String> getPages(StringBuilder sBuilder) {
        List<String> pages = new ArrayList<String>();
        // MAYBE SPLIT MESSAGE INTO MULTIPLE PAGES?
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
                }
                // SPLIT BY SPACE
                else {
                    pages.add(sBuilder.substring(0, index));
                    sBuilder = new StringBuilder(sBuilder.substring(index + 1));
                }
            }
            // ADD LAST PAGE
            if (sBuilder.length() != 0)
                pages.add(sBuilder.toString());
        }

        return pages;
    }
}
