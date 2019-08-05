package io.github.Apeami.BedSurvivalGames.Config;

import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.Apeami.BedSurvivalGames.Coins.CoinData;
import io.github.Apeami.BedSurvivalGames.Game.Arena;
import io.github.Apeami.BedSurvivalGames.Game.TpLocations;
import io.github.Apeami.BedSurvivalGames.Kit.KitInfo;

public interface MinigameConfig {
	
	
	JavaPlugin getPlugin();

	Arena getArena();
	
	World getWorld();
	
	String getName();
	
	int getMaxPlayers();
	
	Location getLocation(TpLocations loc);
	
	List<Location> getStartPoints();
	
	List<Material> getBreakableMaterial();
	
	CoinData getCoinData();
	
	KitInfo getKitData();

	
	/*
	 * The following methods in this interface can be gotten by extending the RawConfig class also
	 * 	provided. They are to get raw config values from the yaml file.
	 * */
	
	void loadConfig(JavaPlugin plugin, String name,  CoinData data, KitInfo kitInfo);
	
	public Boolean getBoolean(String key);

	public int getInt(String key);
	    
	public List<Integer> getIntList(String key);

	public Long getLong(String key);

	public List<String> getStringList(String key);
	    
	public String getString(String key);
	
	public Set<String> getKeys();

	double getDouble(String string);
	
	
}
