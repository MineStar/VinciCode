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

package de.minestar.vincicode.statistic;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;

import de.minestar.minestarlibrary.stats.Statistic;
import de.minestar.minestarlibrary.stats.StatisticType;
import de.minestar.vincicode.core.VinciCodeCore;

public class WhisperStat implements Statistic {

    private String sender;
    private String receiver;
    private String message;

    public WhisperStat() {
        // EMPTY CONSTRUCTOR FOR REFLECTION ACCESS
    }

    public WhisperStat(String sender, String receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    @Override
    public String getPluginName() {
        return VinciCodeCore.NAME;
    }

    @Override
    public String getName() {
        return "Whisper";
    }

    @Override
    public LinkedHashMap<String, StatisticType> getHead() {

        LinkedHashMap<String, StatisticType> head = new LinkedHashMap<String, StatisticType>();

        head.put("sender,", StatisticType.STRING);
        head.put("receiver,", StatisticType.STRING);
        head.put("message,", StatisticType.STRING);

        return head;
    }

    @Override
    public Queue<Object> getData() {

        Queue<Object> data = new LinkedList<Object>();

        data.add(sender);
        data.add(receiver);
        data.add(message);

        return data;
    }
}
