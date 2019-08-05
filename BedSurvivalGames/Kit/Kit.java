package io.github.Apeami.BedSurvivalGames.Kit;

import org.bukkit.entity.Player;

public interface Kit {

	void activateKitOnStart(Player player);
	
	void removeKitOnEnd(Player player);
	
	KitList getName();
}
