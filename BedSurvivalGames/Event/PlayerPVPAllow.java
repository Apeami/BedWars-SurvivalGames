package io.github.Apeami.BedSurvivalGames.Event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import io.github.Apeami.BedSurvivalGames.GameJoiner;
import io.github.Apeami.BedSurvivalGames.Game.BedWarsTracker;


public class PlayerPVPAllow implements Listener{
	
	private GameJoiner joiner;

	public PlayerPVPAllow(GameJoiner joiner) {
		this.joiner=joiner;
	}
	
	@EventHandler
	void PVPEvent(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player killer = (Player) event.getDamager();
			if (this.joiner.getGame(killer)!=null) {
				if (this.joiner.getGame(killer).timeSinceStart()<BedWarsTracker.timeToPVP) {
					event.setCancelled(true);
					killer.sendMessage(ChatColor.RED+"PVP is not Enabled Yet");
				}
			}
		}
	}
}
