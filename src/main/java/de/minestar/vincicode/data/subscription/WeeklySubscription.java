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

package de.minestar.vincicode.data.subscription;

import java.util.LinkedList;

import org.bukkit.inventory.Inventory;

import de.minestar.minestarlibrary.bookapi.MinestarBook;

public class WeeklySubscription implements Subscription {

    private static WeeklySubscription instance;

    private WeeklySubscription() {
        // SINGLE TON
    }

    public static WeeklySubscription getInstance() {
        if (instance == null)
            instance = new WeeklySubscription();

        return instance;
    }

    @Override
    public void deliver(Inventory inv) {
        // TODO: Load Current Weekly as Text
        MinestarBook mb = MinestarBook.createWrittenBook("Minestar", "Weekly", new LinkedList<String>());
        inv.addItem(mb.getBukkitItemStack());
    }
}
