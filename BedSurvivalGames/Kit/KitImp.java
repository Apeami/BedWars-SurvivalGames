package io.github.Apeami.BedSurvivalGames.Kit;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

class KitSpeedy implements Kit {

	@Override
	public void activateKitOnStart(Player player) {
		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, 2));

	}
	@Override
	public KitList getName() {
		return KitList.SPEEDY;
	}

	@Override
	public void removeKitOnEnd(Player player) {
		player.removePotionEffect(PotionEffectType.SPEED);
		
	}

}

class KitNone implements Kit {

	@Override
	public void activateKitOnStart(Player player) {
		return;
	}
	@Override
	public KitList getName() {
		return KitList.NONE;
	}

	@Override
	public void removeKitOnEnd(Player player) {
		return;
	}
}
