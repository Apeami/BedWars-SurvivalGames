package io.github.Apeami.BedSurvivalGames.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.Apeami.BedSurvivalGames.Config.MinigameConfig;
import io.github.Apeami.BedSurvivalGames.LootTable.LootTable;

public class Arena{
	
	private MinigameConfig config;
	private String name;
	private List<Integer> save;
	private List<Integer> play;
	private ArrayList<Block> bList;
	private final int MAX_HEIGHT= 100;
	@SuppressWarnings("unused")
	private HashMap<Block, Location> locList;

	public Arena(MinigameConfig config, String name) {
		this.config=config;
		this.name=name;
		this.play= this.config.getIntList("bounds.play");
		this.save= this.config.getIntList("bounds.save");
	}
	
	String getName() {
		return this.name;
	}
	
	List<Integer> getBounds(){
		return this.play;
		
	}
	
	void tpPlayer(TpLocations location, Player player) {
		Location loc=this.config.getLocation(location);
		player.teleport(loc);
	}
	
	public void getBlocks() {
		this.config.getPlugin().getLogger().info("resetting");
		ArrayList<Block> bList= new ArrayList<Block>();
		HashMap<Block,Location> locList = new HashMap<Block,Location>();
		this.bList=bList;
		this.locList=locList;
		Block b;
		for (int y=0; y<MAX_HEIGHT; y++) {
			for (int x=this.save.get(0); x<this.save.get(2); x++) {
				for (int z=this.save.get(1); z<this.save.get(3); z++) {
					b=config.getWorld().getBlockAt(x,y,z);
					bList.add(b);
					locList.put(b,b.getLocation());
				}
			}
		}
		
	}

	
	 public void placeBlocks(final JavaPlugin plugin, final List<Integer> play, final MinigameConfig config){
	        new BukkitRunnable(){

				public void run(){
	        		plugin.getLogger().info("resetting running later");
	        		int a=0;
	        		for (int y=0; y<MAX_HEIGHT; y++) {
	        			for (int x=play.get(0); x<play.get(2); x++) {
	        				for (int z=play.get(1); z<play.get(3); z++) {
	        					Block nm = bList.get(a);
	        					config.getWorld().getBlockAt(x,y,z).setType(nm.getType());
	        					config.getWorld().getBlockAt(x,y,z).setBlockData(nm.getBlockData());
	        					config.getWorld().getBlockAt(x,y,z).getState().update(false);
	        					a++;
	        				}
	        			}
	        		}
	            	
	            	this.cancel();
	            }
	        }.runTaskLater(this.config.getPlugin(), 200);

	    }
	 
	 public void placeChests(final JavaPlugin plugin, final List<Integer> play, final MinigameConfig config){
	        new BukkitRunnable(){

				public void run(){
	        		plugin.getLogger().info("resetting chests later");
	        		int a=0;
	        		for (int y=0; y<MAX_HEIGHT; y++) {
	        			for (int x=play.get(0); x<play.get(2); x++) {
	        				for (int z=play.get(1); z<play.get(3); z++) {
	        					Block nm = bList.get(a);
	        					if (nm.getType()==Material.CHEST) {
	        						Chest chest = (Chest) config.getWorld().getBlockAt(x,y,z).getState();
	        						ItemStack[] inv = LootTable.getChest(config, chest.getBlockInventory());
	        						
	        						chest.getBlockInventory().setContents(inv);
	        						
	        					}
	        					a++;
	        				}
	        			}
	        		}
	            	
	            	this.cancel();
	            }
	        }.runTaskLater(this.config.getPlugin(), 10);
	 }
	 
	 public void reset() {
		 this.getBlocks();
		 this.placeBlocks(this.config.getPlugin(), this.play, this.config);
	 }
	 
	 public void resetChests() {
		 this.getBlocks();
		 this.placeChests(this.config.getPlugin(), this.play, this.config);
	 }
	 
}

