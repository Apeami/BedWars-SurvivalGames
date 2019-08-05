package io.github.Apeami.BedSurvivalGames.Game;

import java.util.HashMap;
import java.util.Map;

import io.github.Apeami.BedSurvivalGames.Config.FourPlayerConfig;
import io.github.Apeami.BedSurvivalGames.Config.MinigameConfig;


public enum MinigameType {

	FOURPLAYER(FourPlayer.class, "Four_Player", FourPlayerConfig.class);
	
	private String name;
	private Class<? extends Minigame> clazz;
	private Class<? extends MinigameConfig> config;
	
	private static final Map<String, MinigameType> NAME_MAP = new HashMap<String, MinigameType>();

	    static {
	        for (MinigameType type : values()) {
	            if (type.name != null) {
	                NAME_MAP.put(type.name.toLowerCase(), type);
	            }
	        }
	    }
	MinigameType(Class<? extends Minigame> c,String n, Class<? extends MinigameConfig> con) {
		clazz = c;
		name = n;
		config=con;
		
		}
	
	public Class<? extends Minigame> getClazz(){
		return clazz;
	}
	
	public String getName() {
		return name;
	}
	
	public Class<? extends MinigameConfig> getConfig(){
		return config;
	}
	
	public static MinigameType getType(String type) {
        if (type == null) {
            return null;
        }
        return NAME_MAP.get(type.toLowerCase());
    }
	
}
