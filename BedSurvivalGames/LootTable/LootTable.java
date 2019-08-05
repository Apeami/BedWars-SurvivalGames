package io.github.Apeami.BedSurvivalGames.LootTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import io.github.Apeami.BedSurvivalGames.Config.MinigameConfig;

@SuppressWarnings("deprecation")
public class LootTable {
	
	public static ItemStack[] getChest(MinigameConfig config,Inventory inventory ) {
		LootTable t=new LootTable(config);
		return t.genInventory(inventory);
	}

	private MinigameConfig config;
	
	private LootTable(MinigameConfig config) {
		this.config=config;
	}
	
	private List<itemChanceWrapper> getChances() {
		int numLoot= this.config.getInt("amount");
		List<itemChanceWrapper> chances= new ArrayList<itemChanceWrapper>();
		
		for (int i=1 ; i<=numLoot ; i++) {
			double chance= this.config.getDouble("loot."+i+".chance");
			int amount = this.config.getInt("loot."+i+".amount");
			Material f=this.getmaterial((this.config.getString("loot."+i+".name")));
			ItemStack item = new ItemStack(f,amount);
			chances.add(new itemChanceWrapper(item,chance));
			
		}
		return chances;
	}
	
	private ItemStack addChances(List<itemChanceWrapper> chances) {
		Random r = new Random();
		double totalchance=0.0;
		
		for (itemChanceWrapper chance : chances) {
			double c=chance.getChance();
			totalchance=totalchance+c;
		}
		double randomAir = 100 * r.nextDouble();
		
		if (randomAir>totalchance) {
			return new ItemStack(Material.AIR);
		}
		else {
			double randomMaterial =totalchance * r.nextDouble();
			double lastchance=0;
			for (itemChanceWrapper chanceWrapper : chances) {
				double chance = chanceWrapper.getChance()+lastchance;
				if (randomMaterial>=lastchance && randomMaterial<chance) {
					return new ItemStack(chanceWrapper.getItem());
				}
				lastchance=chance;
			}
			
			
		}
		return new ItemStack(Material.AIR);

	}
	
	private ItemStack[] genInventory(Inventory inv) {
		ItemStack[] newinv=new ItemStack[27];
		inv.clear();
		List<itemChanceWrapper> a=this.getChances();
	
		
		for(int i = 0; i<27;i++) {
			newinv[i]=this.addChances(a);
		}
		
		return newinv;
		
	}
	
	private Material getmaterial(String name) {
		switch(name) {//Pretend this isn't here :)
		case "DIAMOND":
			return Material.DIAMOND;
		case "IRON":
			return Material.IRON_INGOT;
		case "STONE_PICKAXE":
			return Material.STONE_PICKAXE;
		case "IRON_PICKAXE":
			return Material.IRON_PICKAXE;
		case "DIAMOND_PICKAXE":
			return Material.DIAMOND_PICKAXE;
		case "OAK_PLANKS":
			return Material.OAK_PLANKS;
		case "STONE":
			return Material.STONE;
		case "OBSIDIAN":
			return Material.OBSIDIAN;
		case "WHITE_WOOL":
			return Material.WHITE_WOOL;
		case "LEATHER_BOOTS":
			return Material.LEATHER_BOOTS;
		case "LEATHER_CHESTPLATE":
			return Material.LEATHER_CHESTPLATE;
		case "LEATHER_LEGGINGS":
			return Material.LEATHER_LEGGINGS;
		case "LEATHER_HELMET":
			return Material.LEATHER_HELMET;
		case "GOLDEN_BOOTS":
			return Material.GOLDEN_BOOTS;
		case "GOLDEN_LEGGINGS":
			return Material.GOLDEN_LEGGINGS;
		case "GOLDEN_CHESTPLATE":
			return Material.GOLDEN_CHESTPLATE;
		case "GOLDEN_HELMET":
			return Material.GOLDEN_HELMET;
		case "CHAINMAIL_BOOTS":
			return Material.CHAINMAIL_BOOTS;
		case "CHAINMAIL_LEGGINGS":
			return Material.CHAINMAIL_LEGGINGS;
		case "CHAINMAIL_CHESTPLATE":
			return Material.CHAINMAIL_CHESTPLATE;
		case "CHAINMAIL_HELMET":
			return Material.CHAINMAIL_HELMET;
		case "IRON_BOOTS":
			return Material.DIAMOND;
		case "IRON_LEGGINGS":
			return Material.DIAMOND;
		case "IRON_CHESTPLATE":
			return Material.DIAMOND;
		case "IRON_HELMET":
			return Material.DIAMOND;
		case "WOODEN_SWORD":
			return Material.WOODEN_SWORD;
		case "STONE_SWORD":
			return Material.STONE_SWORD;
		case "IRON_SWORD":
			return Material.IRON_SWORD;
		case "DIAMOND_SWORD":
			return Material.DIAMOND_SWORD;
		case "WOODEN_AXE":
			return Material.WOODEN_AXE;
		case "STONE_AXE":
			return Material.STONE_AXE;
		case "GOLDEN_APPLE":
			return Material.GOLDEN_APPLE;
		case "HEALTH_POTION":  //there is literally nothing to do with the deprecated stuff
			Potion potion = new Potion( PotionType.INSTANT_HEAL, 1, true, false);//splash,extended 
			ItemStack potionstack = potion.toItemStack( 1 );
			return potionstack.getType();
		case "SPEED_POTION":
			Potion potion1 = new Potion( PotionType.SPEED, 1, true, false);//splash,extended 
			ItemStack potionstack1 = potion1.toItemStack( 1 );
			return potionstack1.getType();
		case "FLINT_STEEL":
			return Material.FLINT_AND_STEEL;
		case "WATER_BUCKET":
			return Material.WATER_BUCKET;
		case "LAVA_BUCKET":
			return Material.LAVA_BUCKET;
		
		default:
			return Material.AIR;
		}
		
		
	}

}

class itemChanceWrapper{
	private ItemStack item;
	private double chance;

	public itemChanceWrapper(ItemStack item, double chance) {
		this.item=item;
		this.chance=chance;
	}
	
	public double getChance() {
		return this.chance;
	}
	
	public ItemStack getItem() {
		return this.item;
	}
	
}
