package io.github.Apeami.BedSurvivalGames.Kit;

import java.util.HashMap;
import java.util.Map;


public enum KitList {
	SPEEDY(KitSpeedy.class, "speedy"),
	NONE(KitNone.class, "none");
	
	private Class<? extends Kit> clazz;
	private String name;

	private static final Map<String, KitList> NAME_MAP = new HashMap<String, KitList>();
	
    static {
        for (KitList kit : values()) {
            if (kit.name != null) {
                NAME_MAP.put(kit.name.toLowerCase(), kit);
            }
        }
    }
	
	KitList(Class<? extends Kit> clazz, String name){
		this.clazz=clazz;
		this.name=name;
	}
	
	public Class<? extends Kit> getLoader(){
		return this.clazz;
	}
	
	public String getName() {
		return this.name;
	}
	
	public static KitList getKitType(String name) {
		 if (name == null) {
	            return null;
	        }
		 return NAME_MAP.get(name.toLowerCase());
	   }
}
