package io.github.Apeami.BedSurvivalGames.ScoreBoard;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import io.github.Apeami.BedSurvivalGames.GameJoiner;
import io.github.Apeami.BedSurvivalGames.Game.BedWarsTracker;
import io.github.Apeami.BedSurvivalGames.Game.FourPlayer;
import io.github.Apeami.BedSurvivalGames.Game.Minigame;
import io.github.Apeami.BedSurvivalGames.Game.RunningStatus;
import me.missionary.board.provider.BoardProvider;

public class ScoreBoardLoader implements BoardProvider {
	
	private GameJoiner joiner;

	public ScoreBoardLoader(GameJoiner joiner) {
		this.joiner=joiner;
	}

	@Override
	public String getTitle(Player player) {
		return "Welcome to BedWars";
	}

	@Override
	public List<String> getLines(Player player) {
            List<String> lines = new ArrayList<>();
            Minigame game =this.joiner.getGame(player);
            lines.add("&7&m-----------------");
            
            if (isInGame(player)==null) {
            	lines.add(ChatColor.LIGHT_PURPLE +"Name: "+player.getDisplayName());
            	lines.add(ChatColor.LIGHT_PURPLE +"Coins: "+Integer.toString(this.joiner.getCoinData().getCoins(player)));
            }
            if (isInGame(player)==RunningStatus.WAITING) {
            	lines.add(ChatColor.LIGHT_PURPLE +"Start: Waiting for "+Integer.toString(FourPlayer.MinPlayers)+" players.");
            	lines.add(ChatColor.LIGHT_PURPLE +"Players :"+Integer.toString(game.getNumPlayers()));
            	lines.add(ChatColor.LIGHT_PURPLE +"Mode :"+ game.getType().getName());
            }
            if (isInGame(player)==RunningStatus.COUNTING) {
            	lines.add(ChatColor.LIGHT_PURPLE +"Start: "+Integer.toString(game.getStartTimer()));
            	lines.add(ChatColor.LIGHT_PURPLE +"Players :"+Integer.toString(game.getNumPlayers()));
            	lines.add(ChatColor.LIGHT_PURPLE +"Mode :"+ game.getType().getName());
            }
            if (isInGame(player)==RunningStatus.PLAYING) {
            	BedWarsTracker tracker= game.getTracker();
            	String event="";
            	if (game.timeSinceStart()<BedWarsTracker.timeToPVP) {
            		event= "PvP Enabled";
            	}
            	if (game.timeSinceStart()<BedWarsTracker.timeToBed) {
            		event= "Bed Breaking Enabled";
            	}
            	lines.add(ChatColor.LIGHT_PURPLE +"Next Event: "+event);
            	lines.add(ChatColor.RED +"Red: "+tracker.getPlayerBed(0)+" "+tracker.isBedBroken(0).getSymbol());
            	lines.add(ChatColor.GREEN +"Green: "+tracker.getPlayerBed(1)+" "+tracker.isBedBroken(1).getSymbol());
            	lines.add(ChatColor.YELLOW +"Yellow"+tracker.getPlayerBed(2)+" "+tracker.isBedBroken(2).getSymbol());
            	lines.add(ChatColor.BLUE +"Blue: "+tracker.getPlayerBed(3)+" "+tracker.isBedBroken(3).getSymbol());
            }
            
            
            lines.add("&7&m-----------------");
            return lines;
	}
	
	private RunningStatus isInGame(Player player) {
		if (this.joiner.getGame(player)!=null) {
			return this.joiner.getGame(player).isRunning();
		}
		else {
			return null;
		}
	}
}
