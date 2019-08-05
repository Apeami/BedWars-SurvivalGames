package io.github.Apeami.BedSurvivalGames.Game;

public enum TpLocations {
	@Deprecated
	GAME("game"),
	SPAWN("spawn"),
	LOBBY("lobby");
	
	private String name;
	TpLocations(String n){
		name = n;
	}
	
	public String getName() {
		return name;
	}
	
}
