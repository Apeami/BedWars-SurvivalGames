package io.github.Apeami.BedSurvivalGames.Kit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.Apeami.BedSurvivalGames.Config.RawConfig;

public class KitInfo extends RawConfig {
	
	   public void addPlugin(JavaPlugin plugin) {
			this.plugin=plugin;
	   }
	
	   public void writeData(Object object, String path) {
		   config.set(path, object);
	       try {
			config.save(this.configFile);
		} 
	       catch (IOException e) {e.printStackTrace();}
	   }
	   
	   public List<KitList> getOwnedKit(Player player) {
		   List<String> playerKitListString=this.getStringList("owned."+player.getUniqueId());
		   List<KitList> playerKitList= new ArrayList<KitList>();
		   
		   for (String kitName:playerKitListString) {
			   playerKitList.add(KitList.getKitType(kitName));
		   }
		   return playerKitList;
		   
	   }
	   
	   public Kit getCurrentKit(Player player) {
		   String kitName = this.getString("current."+player.getUniqueId());
		   
		   try {
			return KitList.getKitType(kitName).getLoader().newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
		   
	   }
	   
	   public void setCurrentKit(Player player, KitList kit) {
		   this.writeData(kit.getName(), "current."+player.getUniqueId());
	   }
	   
	   public void addKit(Player player, KitList kit) {
		   List<String> kits=this.getStringList("owned."+player.getUniqueId());
		   kits.add(kit.getName());
		   this.writeData(kits, "owned."+player.getUniqueId());
	   }
	   
	   public int getKitPrice(KitList kit) {
		   return this.getInt("kits."+kit.getName()+".price");
	   }
	   
	   public List<String> getKitInfo(KitList kit){
		   return this.getStringList("kits."+kit.getName()+".info");
	   }
	   
	   public String getKitItem(KitList kit) {
		   return this.getString("kits."+kit.getName()+".item");
	   }
	   
	   public List<KitList> getAllKits(){
		   List<KitList> allKits = new ArrayList<KitList>();
		   for (String kitName: this.getStringList("kitlist")) {
			   allKits.add(KitList.getKitType(kitName));
		   }
		   return allKits;
	   }
}
