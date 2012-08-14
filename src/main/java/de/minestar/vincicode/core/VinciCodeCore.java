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

package de.minestar.vincicode.core;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.PluginManager;

import de.minestar.minestarlibrary.AbstractCore;
import de.minestar.minestarlibrary.bookapi.Book;
import de.minestar.minestarlibrary.bookapi.CraftBookBuilder;

public class VinciCodeCore extends AbstractCore implements Listener {

    public static final String NAME = "VinciCode";

    public VinciCodeCore() {
        super(NAME);
    }

    @Override
    protected boolean registerEvents(PluginManager pm) {
        pm.registerEvents(this, this);
        return true;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        CraftItemStack book = new CraftItemStack(Material.WRITTEN_BOOK);
        Book b = new CraftBookBuilder().getBook(book);
        b.setTitle(ChatColor.GRAY + "GenericTitle");
        b.setAuthor(ChatColor.DARK_RED + "Administration");
        List<String> pages = new ArrayList<String>();
        pages.add(ChatColor.RED + "Willkommen auf " + ChatColor.BLUE + "Minestar.de" + ChatColor.RED + "!");
        b.setPages(pages);
        event.getPlayer().setItemInHand(book);
    }
}
