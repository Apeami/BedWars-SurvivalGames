package io.github.Apeami.BedSurvivalGames.Game;

public enum RunningStatus {
	//a simple enum to define the stages of waiting and ticking.
	WAITING, // this is when there is no countdown and the game is waiting fo base players
	COUNTING,// this is when the game is counting for start
	PLAYING; // play time
}
