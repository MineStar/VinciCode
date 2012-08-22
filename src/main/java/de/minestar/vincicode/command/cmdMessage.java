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

package de.minestar.vincicode.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import de.minestar.minestarlibrary.commands.AbstractExtendedCommand;
import de.minestar.minestarlibrary.messages.Message;
import de.minestar.minestarlibrary.stats.StatisticHandler;
import de.minestar.minestarlibrary.utils.ChatUtils;
import de.minestar.minestarlibrary.utils.PlayerUtils;
import de.minestar.vincicode.core.VinciCodeCore;
import de.minestar.vincicode.statistic.WhisperStat;

public class cmdMessage extends AbstractExtendedCommand {

    public cmdMessage(String syntax, String arguments, String node) {
        super(VinciCodeCore.NAME, syntax, arguments, node);
    }

    @Override
    public void execute(String[] args, Player player) {
        String targetName = args[0];
        Player target = PlayerUtils.getOnlinePlayer(targetName);
        // POSSIBLE OFFLINE PLAYER
        if (target == null) {
            targetName = PlayerUtils.getOfflinePlayerName(targetName);
            if (targetName == null) {
                PlayerUtils.sendError(player, pluginName, "Spieler '" + args[0] + "' nicht gefunden!");
                return;
            }
            // SAVE MESSAGE TO SEND HIM WHEN PLAYER IS ONLINE
            Message message = new Message(player.getName(), targetName, ChatColor.WHITE, ChatUtils.getMessage(args, 1));
            if (VinciCodeCore.messageManger.handleOfflineMessage(message))
                PlayerUtils.sendInfo(player, pluginName, "Spieler '" + targetName + "' ist offline. Er erhält die Nachricht, wenn er online ist.");
            else
                PlayerUtils.sendError(player, pluginName, "Spieler '" + targetName + "' ist offline, aber es konnte ihm wegen eines Datenbankfehlers keine Nachricht gesendet werden!");

            return;
        }

        // PLAYER IS WHISPERING HIMSELF ...
        if (target.equals(player)) {
            PlayerUtils.sendMessage(player, ChatColor.LIGHT_PURPLE, pluginName, "Du schreibst dich selber an? Schreib doch Leif_Ericsson an :)");
            return;
        }

        // SEND MESSAGES INGAME
        String text = ChatUtils.getMessage(args, 1);
        PlayerUtils.sendBlankMessage(player, ChatColor.GOLD + "[Ich -> " + target.getName() + "] : " + ChatColor.GRAY + text);
        PlayerUtils.sendBlankMessage(player, ChatColor.GOLD + "[" + target.getName() + " -> Mich" + "] : " + ChatColor.GRAY + text);

        // SAVE FOR REPLIES WITH /R
        VinciCodeCore.messageManger.setLastSend(player, target);

        // FIRE STATISTIC
        StatisticHandler.handleStatistic(new WhisperStat(player.getName(), target.getName(), ChatUtils.getMessage(args, 1)));
    }

}
