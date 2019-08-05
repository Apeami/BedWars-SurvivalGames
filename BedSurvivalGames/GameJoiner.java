package io.github.Apeami.BedSurvivalGames;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.Apeami.BedSurvivalGames.Coins.CoinData;
import io.github.Apeami.BedSurvivalGames.Config.MinigameConfig;
import io.github.Apeami.BedSurvivalGames.Config.RawConfig;
import io.github.Apeami.BedSurvivalGames.Game.Minigame;
import io.github.Apeami.BedSurvivalGames.Game.MinigameType;
import io.github.Apeami.BedSurvivalGames.Game.RunningStatus;
import io.github.Apeami.BedSurvivalGames.Kit.KitInfo;
import net.md_5.bungee.api.ChatColor;

public class GameJoiner {
	private JavaPlugin plugin;
	private RawConfig config;
	private ArrayList<Minigame> minigameList=new ArrayList<Minigame>();
	private CoinData coindata;
	private KitInfo kitInfo;;

	public GameJoiner(JavaPlugin plugin, CoinData coindata, KitInfo kitInfo) {
		this.coindata=coindata;
		this.kitInfo=kitInfo;
		this.plugin=plugin;
		this.config= new RawConfig();
		this.config.addPlugin(plugin, coindata,kitInfo );
		this.config.loadFromFile("config", "config");
	}
	
	public void loadGame(String name) {
		String nametype= this.config.getString("type."+name);
		MinigameType type= MinigameType.getType(nametype);
		MinigameConfig miniconfig;
		Minigame game;
		
		try {
			miniconfig=type.getConfig().newInstance();
			game= type.getClazz().newInstance();
		} catch (InstantiationException e) {
			this.plugin.getLogger().warning(name+" could not be loaded!");
			e.printStackTrace();
			return;
		} catch (IllegalAccessException e) {
			this.plugin.getLogger().warning(name+" could not be loaded!");
			e.printStackTrace();
			return;
		}
		
		//this minigame is allready to go
		miniconfig.loadConfig(this.plugin, name, this.coindata, this.kitInfo);
		game.setValues(this.plugin, miniconfig);
		
		minigameList.add(game);
	}
	
	public void loadAllGames() {
		//loads all the minigames one by one
		List<String> allGames= this.config.getStringList("minigames");
		for (String game : allGames) {
			this.loadGame(game);
		}
	}
	
	public Minigame getGame(String name) {
		for (Minigame game : minigameList) {
			if (game.getName().equals(name)) {
				return game;
			}
		}
		return null;
	}
	
	public Minigame getGame(Player player) {
		for (Minigame game : minigameList) {
			List<Player> a = game.getPlayers();
			
			for (Player player1: a) {
				if (player1!=null) {
				}
			}
			
			if (a.contains(player)) {
				return game;
			}
		}
		return null;
	}
	
	public void start(String name) {
		for (Minigame game : minigameList) {
			if (game.getName().equals(name)) {
				game.start();
			}
		}
	}
	
	
	public boolean addPlayer(Player player) {
		int max=0;
		Minigame best=null;
		
		for (Minigame game : minigameList) {
			if (!(game.isRunning()==RunningStatus.PLAYING)) {
				if (game.getNumPlayers()>=max && !(game.getPlayers().contains(player))) {
					max=game.getNumPlayers();
					best=game;
				}
			}
		}
		if (best!=null) {
			return best.addPlayer(player);
		}
		else {
			return false;
		}
	}
		
	public void removePlayer(Player player) {
		for (Minigame game : minigameList) {
			List<Player> a = game.getPlayers();
			if (a.contains(player)) {
				game.removePlayer(player);
				return;
			}
		}
	}
	
	public void stop(String name) {
		for (Minigame game : minigameList) {
			if (game.getName().equals(name)){
				game.stop();
				return;
			}
		}
	}
	
	public ArrayList<Minigame> getAllGames() {
		return minigameList;
	}
	
	public JavaPlugin getPlugin() {
		return this.plugin;
	}
	
	public CoinData getCoinData() {
		return this.coindata;
	}
	
	public static void prepareLobbyInventory(Player player) {
		player.getInventory().clear();
		
		ItemStack kit = new ItemStack(Material.CHEST) ;
	
        ItemMeta itemMetaKit = kit.getItemMeta();
        itemMetaKit.setDisplayName(ChatColor.BLUE+"Kit Selector");
        kit.setItemMeta(itemMetaKit);
        
		ItemStack join = new ItemStack(Material.END_PORTAL_FRAME) ;
		
        ItemMeta itemMetaJoin = join.getItemMeta();
        itemMetaJoin.setDisplayName(ChatColor.YELLOW+"Join Game");
        join.setItemMeta(itemMetaJoin);
        
        player.getInventory().addItem(join);
        player.getInventory().addItem(kit);
	}
}
