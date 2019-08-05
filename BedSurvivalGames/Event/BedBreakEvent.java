package io.github.Apeami.BedSurvivalGames.Event;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import io.github.Apeami.BedSurvivalGames.GameJoiner;
import io.github.Apeami.BedSurvivalGames.Coins.RewardReason;
import io.github.Apeami.BedSurvivalGames.Game.BedWarsTracker;
import io.github.Apeami.BedSurvivalGames.Game.Minigame;
import io.github.Apeami.BedSurvivalGames.Game.bedState;


public class BedBreakEvent implements Listener{
	
	private GameJoiner joiner;

	public BedBreakEvent(GameJoiner joiner) {
		this.joiner=joiner;
	}
	
	@EventHandler
	void BreakEvent(BlockBreakEvent event) {
		if(event.getBlock().getType()==Material.WHITE_BED) {
			Player player= event.getPlayer();
			Minigame game=this.joiner.getGame(player);
			BedWarsTracker tracker=game.getTracker();
			Player bedowner= tracker.getPlayerBed(event.getBlock().getLocation());
			if (game.timeSinceStart()>BedWarsTracker.timeToBed) {
				if (bedowner!=null) {
					//game.getConfig().getPlugin().getLogger().info("Bedowner:"+bedowner.getDisplayName());
					//game.getConfig().getPlugin().getLogger().info("Player:"+player.getDisplayName());
					if (bedowner!=null) {
						//if (bedowner!=player)
						tracker.breakBed(bedowner, bedState.BROKEN);
						game.sendMessage(ChatColor.AQUA+bedowner.getDisplayName()+"'s bed has been broken by "+player.getDisplayName());
						
						this.joiner.getCoinData().addCoins(player, RewardReason.BEDBREAK);
						return;
						//}
					}
				}
			}
			else {
				event.setCancelled(true);
				player.sendMessage(ChatColor.GOLD+"Bed Breaking is not Enabled Yet");
			}
		}
	}
}
