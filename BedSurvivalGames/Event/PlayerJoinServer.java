package io.github.Apeami.BedSurvivalGames.Event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import io.github.Apeami.BedSurvivalGames.GameJoiner;

public class PlayerJoinServer implements Listener{
	@SuppressWarnings("unused")
	private GameJoiner joiner;

	public PlayerJoinServer(GameJoiner joiner) {
		this.joiner=joiner;
	}
	
	@EventHandler
	void PlayerJoin(PlayerJoinEvent event) {
		GameJoiner.prepareLobbyInventory(event.getPlayer());
		event.setJoinMessage("");
		
	}
}