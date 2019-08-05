package io.github.Apeami.BedSurvivalGames.Kit;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.minuskube.inv.SmartInventory;
import io.github.Apeami.BedSurvivalGames.GameJoiner;
import io.github.Apeami.BedSurvivalGames.Game.RunningStatus;

public class KitCommand implements CommandExecutor {

	private SmartInventory inventory;
	private GameJoiner joiner;

	public KitCommand(SmartInventory inventory, GameJoiner joiner) {
		this.inventory=inventory;
		this.joiner=joiner;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (this.joiner.getGame((Player)sender)==null) {
			this.inventory.open((Player)sender);
			return true;
		}
		if (this.joiner.getGame((Player)sender).isRunning()!=RunningStatus.PLAYING) {
			this.inventory.open((Player)sender);
			return true;
		}
		else {
			sender.sendMessage(ChatColor.RED+"You can not select your kit while playing");
		}
		
		return true;
	}

}
