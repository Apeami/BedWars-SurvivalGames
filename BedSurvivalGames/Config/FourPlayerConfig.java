package io.github.Apeami.BedSurvivalGames.Config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.Apeami.BedSurvivalGames.Coins.CoinData;
import io.github.Apeami.BedSurvivalGames.Game.Arena;
import io.github.Apeami.BedSurvivalGames.Game.TpLocations;
import io.github.Apeami.BedSurvivalGames.Kit.KitInfo;

public class FourPlayerConfig extends RawConfig implements MinigameConfig{
	private Arena arena;
	private String name;
	private CoinData data;
	private KitInfo kitInfo; 

	public Arena getArena() {
		return this.arena;
	}

	public int getMaxPlayers() {
		return this.getInt("maxplayers");
	}

	public Location getLocation(TpLocations loc) {
		List<Integer> loclist=this.getIntList("locations."+loc.getName());		
		return new Location(getWorld(), loclist.get(0), loclist.get(1), loclist.get(2));
	}

	public List<Location> getStartPoints() {
		List<Location> allloclist=new ArrayList<Location>();
		for (int i=1 ; i<=getMaxPlayers(); i++) {
			List<Integer> loclist =this.getIntList("locations.game."+Integer.toString(i));
			allloclist.add(new Location(getWorld(), loclist.get(0), loclist.get(1), loclist.get(2)));
		}
		return allloclist;
	}

	public void loadConfig(JavaPlugin plugin, String name, CoinData data, KitInfo kitInfo) {
		this.data=data;
		this.kitInfo=kitInfo;
		this.plugin = plugin;
		this.name=name;
		this.loadFromFile("minigame", name);
		this.arena=new Arena(this, name);
	}

	public JavaPlugin getPlugin() {
		return this.plugin;
	}

	public World getWorld() {
		return Bukkit.getWorld(this.getString("world"));
	}

	public String getName() {
		return this.name;
	}

	public List<Material> getBreakableMaterial() {
		List<Material> materials=new ArrayList<Material>();
		List<String> matName= this.getStringList("breakableBlock");
				
		for (String name : matName) {
			materials.add(this.getmaterial(name));
		}
		return materials;
	}
	
	private Material getmaterial(String name) {
		switch(name) {//Pretend this isn't here :)
		case "OAK_PLANKS":
			return Material.OAK_PLANKS;
		case "STONE":
			return Material.STONE;
		case "OBSIDIAN":
			return Material.OBSIDIAN;
		case "WHITE_WOOL":
			return Material.WHITE_WOOL;
		case "WHITE_BED":
			return Material.WHITE_BED;
		default:
			return Material.AIR;
		}
	}

	public KitInfo getKitData() {
		return this.kitInfo;
	}
	
	public CoinData getCoinData() {
		return this.data;
	}
}
