package io.github.Apeami.BedSurvivalGames.Event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import io.github.Apeami.BedSurvivalGames.GameJoiner;
import io.github.Apeami.BedSurvivalGames.Coins.RewardReason;
import io.github.Apeami.BedSurvivalGames.Game.BedWarsTracker;
import io.github.Apeami.BedSurvivalGames.Game.Minigame;
import io.github.Apeami.BedSurvivalGames.Game.bedState;


public class PlayerDeath implements Listener{
	
	private GameJoiner joiner;

	public PlayerDeath(GameJoiner joiner) {
		this.joiner=joiner;
	}
	
	@EventHandler
	void DeathEvent(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player=(Player) event.getEntity();
		
			Minigame game=this.joiner.getGame(player);
			BedWarsTracker tracker= game.getTracker();
		
			
			if (((player.getHealth() - event.getFinalDamage()) <= 0)) {
		        event.setCancelled(true);//This ensures that the player will die
		        player.setHealth(20);
		        
		        if (event.getDamager()instanceof Player) {
		        	Player killer = (Player) event.getDamager();
		        	this.joiner.getCoinData().addCoins(killer, RewardReason.KILL);
		        }
		        
		        if (tracker.isBedBroken(player)==bedState.BROKEN) {
		        	game.sendMessage(ChatColor.GOLD+player.getDisplayName()+" has died with a broken bed. He is now kicked from the game");
		        	game.removePlayer(player); //if the player bed is broken kick them from the game.
		        }
		        else {
		        	game.respawnPlayer(player); //Respawn player in arena if bed is not broken.
		        	game.getConfig().getPlugin().getLogger().info("bedNotBroken");
		        }
			}
		}
	}
}
