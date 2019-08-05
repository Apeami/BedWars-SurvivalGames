package io.github.Apeami.BedSurvivalGames.Game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.Apeami.BedSurvivalGames.GameJoiner;
import io.github.Apeami.BedSurvivalGames.Coins.RewardReason;
import io.github.Apeami.BedSurvivalGames.Config.MinigameConfig;

public class FourPlayer extends BukkitRunnable implements Minigame{
	
	private JavaPlugin plugin;
	private MinigameConfig config;
	private RunningStatus running;
	private List<Player> playerList = new ArrayList<Player>();
	private static final MinigameType gameType= MinigameType.FOURPLAYER;
	private int starttimer=0;
	private int gametick=0;
	private BedWarsTracker tracker;
	public static final int MinPlayers=1;

	public void setValues(JavaPlugin plugin, MinigameConfig config) {
		this.plugin=plugin;
		this.config=config;
		this.running=RunningStatus.WAITING;
		this.runTaskTimer(this.plugin, 0, 10);
		
	}
	
	public void run() {
		if (this.running==RunningStatus.COUNTING) {
			if (this.starttimer==0) {
				start();
			}
			if (this.starttimer==10) {
				this.sendMessage(ChatColor.YELLOW+"Game starting in 10 Seconds");
			}
			this.starttimer--;
		}

		if (this.running==RunningStatus.PLAYING) {
			this.gametick++;
			//fill in for game events
			if (this.gametick==BedWarsTracker.timeToBed) {
				this.sendMessage(ChatColor.AQUA+"Bed-Breaking is now enabled");
			}
			if (this.gametick==BedWarsTracker.timeToPVP) {
				this.sendMessage(ChatColor.AQUA+"PVP is now enabled");
			}
			
			if (this.gametick==1000) {
				stop();
			}
			if (getNumPlayers()==0) {
				Player winner = null;
				for (Player player : getPlayers()) {
					if (player!=null) {
						winner = player;
					}
				}
				if (winner!=null) {
				sendMessage(ChatColor.YELLOW+winner.getDisplayName()+" has won.");
				this.config.getCoinData().addCoins(winner, RewardReason.WIN);
				}
				stop();
			}
		}
	}	

	public int timeSinceStart() {
		return gametick;
	}

	public int getMaxPlayers() {
		return this.config.getMaxPlayers();
	}

	public List<Player> getPlayers() {
		return this.playerList;
	}

	public void removePlayer(Player player) {
		if (player!=null) {
			int i= this.playerList.indexOf(player);
			this.playerList.set(i,null);
			player.teleport(this.config.getLocation(TpLocations.SPAWN));
			this.config.getKitData().getCurrentKit(player).removeKitOnEnd(player);
		}
		
		if (getNumPlayers()==0 && this.running==RunningStatus.PLAYING) {
			Bukkit.getLogger().info("stopping the game");
			stop();
		}
		GameJoiner.prepareLobbyInventory(player);
	}

	public boolean addPlayer(Player player) {
		if (this.running!=RunningStatus.PLAYING) {
			if (getNumPlayers()<getMaxPlayers()) {
				this.playerList.add(player);
				Bukkit.dispatchCommand(player, "kit");
				this.sendMessage(player.getDisplayName()+" has joined");
				player.teleport(this.config.getLocation(TpLocations.LOBBY));
				if (getNumPlayers()==MinPlayers) {
					setStartTimer(60);
					this.running=RunningStatus.COUNTING;
					this.sendMessage(ChatColor.YELLOW+"Game Starting in 60 seconds");
				}
				if (getNumPlayers()==getMaxPlayers()) {
					setStartTimer(10);
					this.sendMessage(ChatColor.YELLOW+"Game Starting in 10 seconds");
				}
			}
			return true;
		}
		return false;
	}

	public MinigameType getType() {
		return gameType;
	}

	public String getName() {
		return this.config.getName();
	}

	public void start() {
		this.gametick=0;
		this.running=RunningStatus.PLAYING;
		List<Location> start=this.config.getStartPoints();
		
		int i=0;
		for (Player player : this.playerList) {
			player.getInventory().clear();
			this.config.getKitData().getCurrentKit(player).activateKitOnStart(player);
			Location a= start.get(i);
			player.teleport(a);
			i++;
		}
		
		if (this.playerList.size()<getMaxPlayers()) {
			
			while (this.playerList.size()<getMaxPlayers()){
				this.playerList.add(null);
			}
		}
		this.sendMessage(ChatColor.GREEN+"Minigame is Starting");
		
		this.getArena().resetChests();
		
		this.tracker=new BedWarsTracker(this.playerList, this.config);
		
	}

	public void stop() {
		if (this.running==RunningStatus.PLAYING) {
			this.sendMessage(ChatColor.RED+"Game Has Stopped");
			ArrayList<Player> pl = new ArrayList<Player>(getPlayers());
			for (Player player: pl) {
				if (player!=null) {
					player.getInventory().clear();
					removePlayer(player);
				}
			}
			this.running=RunningStatus.WAITING;
			this.playerList=new ArrayList<Player>();
			this.config.getArena().reset();
		
			this.tracker=null;
		}
	}

	public int getNumPlayers() {
		int i=0;
				
		for (Player player: playerList) {
			if (player!=null) {
				i++;
			}
		}
		return i;
	}

	public void setStartTimer(int time) {
		//Can only set it if running = false;
		starttimer=time;	
	}
	
	public int getStartTimer() {
		return starttimer;
	}

	public RunningStatus isRunning() {
		return this.running;
	}

	public Arena getArena() {
		return this.config.getArena();
	}

	public MinigameConfig getConfig() {
		return this.config;
	}

	public void sendMessage(String message) {
		for (Player player : getPlayers()) {
			if (player!=null) {
				player.sendMessage(message);
			}
		}
	}
	
	public BedWarsTracker getTracker() {
		return this.tracker;
	}
	
	public void respawnPlayer(Player player) {
		int i=0;
		for (Player pCheck :this.playerList) {
			if (pCheck!=null) {
				if (pCheck==player) {
					break;
				}
			}
			i++;
		}
		List<Location> respawn=this.config.getStartPoints();
		Location playerTp = respawn.get(i);
		player.teleport(playerTp);
		
	}
	

}
