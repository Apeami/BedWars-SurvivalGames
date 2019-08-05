package io.github.Apeami.BedSurvivalGames.Kit;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import io.github.Apeami.BedSurvivalGames.Coins.CoinData;
import net.md_5.bungee.api.ChatColor;

public class KitInventoryProvider implements InventoryProvider {
	
	private CoinData coinData;
	private KitInfo kitInfo;

	public KitInventoryProvider(CoinData coinData, KitInfo kitInfo) {
		this.coinData=coinData;
		this.kitInfo=kitInfo;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void init(Player player, InventoryContents contents) {
		List<KitList> allKits = this.kitInfo.getAllKits();
		int i=0;
		
		for (KitList kit:allKits) {
			int row= i/9;
			int column=i%9;
			String KitName=kit.getName();
			Material material;
			material = Material.matchMaterial(this.kitInfo.getKitItem(kit));
			if (material == null) {
			  material= Bukkit.getUnsafe().getMaterial(this.kitInfo.getKitItem(kit), 14);
			}
			
			ItemStack mat = new ItemStack(material);
	        ItemMeta itemMeta = mat.getItemMeta();
	        itemMeta.setDisplayName(KitName);
	        
	        List<String> lore;
	        if (this.kitInfo.getOwnedKit(player).contains(kit)) {
	        	lore=new LinkedList<String>(Arrays.asList("You own this Kit"));
	        }
	        else {
	        	lore=new LinkedList<String>(Arrays.asList("Buy this Kit for: "+Integer.toString(this.kitInfo.getKitPrice(kit))));
	        }
	        for (String descRow : this.kitInfo.getKitInfo(kit)) {
	        	lore.add(descRow);
	        }
	        itemMeta.setLore(lore);
	        mat.setItemMeta(itemMeta);
			
			
			contents.set(row,column, ClickableItem.of(mat, e -> {
				if(e.isLeftClick()) {
					if (this.kitInfo.getOwnedKit(player).contains(kit)) {
						this.kitInfo.setCurrentKit(player, kit);
						player.sendMessage(ChatColor.GOLD+"You have selected the kit: "+ KitName);
					}			
					else {
						//Else the player will buy the kit.
						if (this.coinData.getCoins(player)>this.kitInfo.getKitPrice(kit)) {
							this.coinData.removeCoins(player, this.kitInfo.getKitPrice(kit));
							this.kitInfo.addKit(player, kit);
							this.kitInfo.setCurrentKit(player, kit);
							player.sendMessage(ChatColor.GOLD+"You have bought the kit: "+ KitName);
						}
						else {
							player.sendMessage(ChatColor.RED+"You don't have enough coins");
						}
					}
					player.closeInventory();
				}
				
			}));
			i++;
		}	
	}

	@Override
	public void update(Player player, InventoryContents contents) {
		// TODO Auto-generated method stub

	}

}
