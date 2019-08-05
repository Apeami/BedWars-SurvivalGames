package io.github.Apeami.BedSurvivalGames.Game;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.Apeami.BedSurvivalGames.Config.MinigameConfig;

public interface Minigame {
	
	void setValues(JavaPlugin plugin, MinigameConfig config);
	
	int timeSinceStart();
	
	void run();//extend bukkit runnable.
	
	int getMaxPlayers();
	
	List<Player> getPlayers();
	
	void removePlayer(Player player);
	
	boolean addPlayer(Player player);
	
	MinigameType getType();
	
	String getName();
	
	void start();
	
	void stop();
	
	int getNumPlayers();
	
	void setStartTimer(int time);
	
	RunningStatus isRunning();
	
	Arena getArena();
	
	MinigameConfig getConfig();
	
	void sendMessage(String message);
	
	BedWarsTracker getTracker();
	
	void respawnPlayer(Player player);

	int getStartTimer();
}
