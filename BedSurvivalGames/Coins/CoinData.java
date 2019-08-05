package io.github.Apeami.BedSurvivalGames.Coins;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.Apeami.BedSurvivalGames.Config.RawConfig;


public class CoinData extends RawConfig {
	
		public void addPlugin(JavaPlugin plugin) {
			this.plugin=plugin;
		}
	
	   public void writeData(Object object, String path) {
		   Bukkit.getLogger().info("writing data");
		   config.set(path, object);
	       try {
			config.save(this.configFile);
		} 
	       catch (IOException e) {e.printStackTrace();}
	   }
	   
	   public void addCoins(Player player,int amount) {
		   int current= this.getCoins(player);
		   this.writeData(current+amount, player.getUniqueId().toString());
	   }
	   
	   public void addCoins(Player player,RewardReason amount) {
		   this.addCoins(player, amount.getReward());
	   }
	   
	   public void removeCoins(Player player, int amount) {
		   int current= this.getCoins(player);
		   this.writeData(current-amount, player.getUniqueId().toString());
	   }
	   
	   public int getCoins(Player player) {
		   return this.getInt(player.getUniqueId().toString());
	   }
}
