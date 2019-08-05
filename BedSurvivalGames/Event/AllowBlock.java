package io.github.Apeami.BedSurvivalGames.Event;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import io.github.Apeami.BedSurvivalGames.GameJoiner;

public class AllowBlock implements Listener{
	private GameJoiner joiner;

	public AllowBlock(GameJoiner joiner) {
		this.joiner=joiner;
	}
	
	@EventHandler
	void BlockBreakEvent(BlockBreakEvent event) {
		Material mat= event.getBlock().getType();
		if (this.joiner.getGame(event.getPlayer())!=null){
			if (this.joiner.getGame(event.getPlayer()).getConfig().getBreakableMaterial().contains(mat)){
				return;
			}
			else {
				event.setCancelled(true);
			}
		}
	}
}
