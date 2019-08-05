package io.github.Apeami.BedSurvivalGames.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.Apeami.BedSurvivalGames.GameJoiner;

public class JoinCommand implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private JavaPlugin plugin;
	private GameJoiner joiner;

	public JoinCommand(JavaPlugin plugin, GameJoiner joiner) {
		this.plugin=plugin;
		this.joiner=joiner;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("join")) {
			Player player=(Player)sender;
		
			if (this.joiner.addPlayer(player)) {
				player.sendMessage(ChatColor.GREEN+"You have joined a arena");
			}
			else {
				player.sendMessage(ChatColor.RED+"There is no arena ready to join");
			}
			return true;
		}
		return false;
	}

}
