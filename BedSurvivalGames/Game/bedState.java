package io.github.Apeami.BedSurvivalGames.Game;

public enum bedState{
	BROKEN("X"),
	INTACT("\\/");
	
	private String symbol;

	bedState(String symbol) {
		this.symbol=symbol;
	}
	
	public String getSymbol() {
		return this.symbol;
	}
}
