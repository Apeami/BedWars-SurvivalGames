package io.github.Apeami.BedSurvivalGames.Coins;

public enum RewardReason {
	KILL(20),
	WIN(100),
	BEDBREAK(50);
	
	private int coins;
	RewardReason(int coins){
		this.coins=coins;
	}
	
	public int getReward() {
		return this.coins;
	}
}
