package io.github.Apeami.BedSurvivalGames.Game;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.Apeami.BedSurvivalGames.Config.MinigameConfig;

public class BedWarsTracker{
	
	private MinigameConfig config;
	private LinkedHashMap<Player, bedState> bedBroke= new LinkedHashMap<Player, bedState>();
	private List<Player> playerList;
	
	public static final int timeToBed = 60;
	public static final int timeToPVP = 30;

	public BedWarsTracker(List<Player> playerList, MinigameConfig config) {
		this.config=config;
		this.playerList=playerList;
		
		for (Player player: playerList) {
			this.bedBroke.put(player, bedState.INTACT);
		}
	}
	
	public void breakBed (Player player,bedState broken) {
		if (this.bedBroke.containsKey(player)){
			this.bedBroke.put(player, broken);
		}
	}
	
	public bedState isBedBroken(Player player) {
		if (this.bedBroke.containsKey(player)){
			return this.bedBroke.get(player);
		}
		return bedState.BROKEN;
	}
	
	public bedState isBedBroken(int id) {
		try {
			Player player = this.playerList.get(id);
			if (player==null) {
				return bedState.BROKEN;
			}
			return this.bedBroke.get(player);
		}
		catch(IndexOutOfBoundsException e) {
			e.printStackTrace();
			return bedState.BROKEN;
		}
	}
	public String getPlayerBed(int id) {
		try {
			Player player = this.playerList.get(id);
			if (player==null) {
				return "Not owned";
			}
			else {
				return player.getDisplayName();
			}
		}
		catch(IndexOutOfBoundsException e) {
			e.printStackTrace();
			return "Not owned";
		}
	}
	
	public Player getPlayerBed(Location loc) {
		
		for (int i=1 ; i<=this.bedBroke.size(); i++) {
			for (String s : Arrays.asList("a","b")){
				
				List<Integer> bedloccood=this.config.getIntList("bed."+Integer.toString(i)+"."+s);
				
				if ((loc.getWorld().equals(this.config.getWorld()))
						&& (bedloccood.get(0)==loc.getBlockX())
						&& (bedloccood.get(1)==loc.getBlockY())
						&& (bedloccood.get(2)==loc.getBlockZ())) {
				
					return this.playerList.get(i-1);
				}
				
			}
		}
		return null;
	}
	
	
}
