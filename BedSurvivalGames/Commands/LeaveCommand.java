package io.github.Apeami.BedSurvivalGames.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.Apeami.BedSurvivalGames.GameJoiner;
import net.md_5.bungee.api.ChatColor;

public class LeaveCommand implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private JavaPlugin plugin;
	private GameJoiner joiner;

	public LeaveCommand(JavaPlugin plugin, GameJoiner joiner) {
		this.plugin=plugin;
		this.joiner=joiner;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("leave")) {
			Player player=(Player)sender;
		
			this.joiner.removePlayer(player);
			player.sendMessage(ChatColor.RED+"You have left the Minigame");
			return false;
		}
		return false;
	}

}
