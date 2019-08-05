package io.github.Apeami.BedSurvivalGames.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.Apeami.BedSurvivalGames.Coins.CoinData;
import io.github.Apeami.BedSurvivalGames.Kit.KitInfo;

public class RawConfig {
	protected JavaPlugin plugin;
    protected YamlConfiguration config;
	protected File configFile;
	private CoinData coinData;
	protected KitInfo kitInfo; 

    
	public void addPlugin(JavaPlugin plugin, CoinData coinData, KitInfo kitInfo) {
        this.plugin = plugin;
        this.coinData=coinData;
        this.kitInfo=kitInfo;
	}
    
    public synchronized void loadFromFile(String defName, String name) {
        //this.plugin.getLogger().info("Loading config...");
        File configFile = new File(plugin.getDataFolder(), name+".yml");
        if(!configFile.exists()) {
            this.plugin.getLogger().info("Config does not exist, copying default from jar...");
            try {
                this.plugin.getDataFolder().mkdirs();
                Files.copy(plugin.getResource("resources/"+defName+".yml"), configFile.toPath());
                configFile.renameTo(new File(plugin.getDataFolder(), name+".yml"));
            } catch (IOException e) {
                this.plugin.getLogger().warning("There was a problem copying the config:");
                config = YamlConfiguration.loadConfiguration(new BufferedReader(new InputStreamReader(plugin.getResource("resources/config.yml"))));
                e.printStackTrace();
                this.plugin.getLogger().info("We may still be able to run...");
                return;
            }
        }
        config = YamlConfiguration.loadConfiguration(configFile);
        this.plugin.getLogger().info("Config successfully loaded.");
        this.configFile=configFile;
    }
    public Boolean getBoolean(String key) {
        return config.getBoolean(key);
    }

    public int getInt(String key) {
        return config.getInt(key);
    }
    
    public List<Integer> getIntList(String key) {
        return config.getIntegerList(key);
    }


    public Long getLong(String key) {
        return config.getLong(key);
    }

    public List<String> getStringList(String key) {
        return config.getStringList(key);
    }
    
    public String getString(String key) {
    	return config.getString(key);
    }
    public Set<String> getKeys(){
 	   return config.getKeys(true);
    }
    public double getDouble(String key) {
    	return config.getDouble(key);
    }

    
    public CoinData getCoinData() {
    	return this.coinData;
    }
    
    public KitInfo getKitInfo() {
    	return this.kitInfo;
    }

}
