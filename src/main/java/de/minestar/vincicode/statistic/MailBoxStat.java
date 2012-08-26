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

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;

import de.minestar.minestarlibrary.stats.Statistic;
import de.minestar.minestarlibrary.stats.StatisticType;
import de.minestar.vincicode.core.VinciCodeCore;

public class MailBoxStat implements Statistic {

    private String player;
    private boolean wasInInventory;
    private Timestamp date;

    public MailBoxStat() {
        // EMPTY CONSTRUCTOR FOR REFLECTION ACCESS
    }

    public MailBoxStat(String player, boolean wasInInventory) {
        this.player = player;
        this.wasInInventory = wasInInventory;
        this.date = new Timestamp(System.currentTimeMillis());
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

        head.put("player,", StatisticType.STRING);
        head.put("wasInInventory", StatisticType.INT);
        head.put("date", StatisticType.DATETIME);

        return head;
    }

    @Override
    public Queue<Object> getData() {

        Queue<Object> data = new LinkedList<Object>();

        data.add(player);
        data.add(wasInInventory ? 1 : 0);
        data.add(date);

        return data;
    }
}