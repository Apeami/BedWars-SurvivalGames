package io.github.Apeami.BedSurvivalGames;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.minuskube.inv.SmartInventory;
import io.github.Apeami.BedSurvivalGames.Coins.CoinData;
import io.github.Apeami.BedSurvivalGames.Commands.JoinCommand;
import io.github.Apeami.BedSurvivalGames.Commands.LeaveCommand;
import io.github.Apeami.BedSurvivalGames.Event.AllowBlock;
import io.github.Apeami.BedSurvivalGames.Event.BedBreakEvent;
import io.github.Apeami.BedSurvivalGames.Event.PlayerDeath;
import io.github.Apeami.BedSurvivalGames.Event.PlayerJoinServer;
import io.github.Apeami.BedSurvivalGames.Event.PlayerPVPAllow;
import io.github.Apeami.BedSurvivalGames.Kit.KitCommand;
import io.github.Apeami.BedSurvivalGames.Kit.KitInfo;
import io.github.Apeami.BedSurvivalGames.Kit.KitInventoryProvider;
import io.github.Apeami.BedSurvivalGames.ScoreBoard.ScoreBoardLoader;
import me.missionary.board.BoardManager;
import me.missionary.board.settings.BoardSettings;
import me.missionary.board.settings.ScoreDirection;

public class BedSurvivalGames extends JavaPlugin{
	
	private SmartInventory INVENTORY;
	private GameJoiner wholegame;
	private BoardManager boardManager;
	private CoinData coinData;
	private KitInfo kitData;
	
	public void onEnable(){
		this.getLogger().info("Minigame Server Starting Up");
		
		this.coinData=new CoinData();
		this.coinData.addPlugin(this);
		this.coinData.loadFromFile("data", "data");
		
		this.kitData=new KitInfo();
		this.kitData.addPlugin(this);
		this.kitData.loadFromFile("kit", "kit");
		
		this.wholegame= new GameJoiner(this, coinData, kitData);
		this.wholegame.loadAllGames();
		
		this.loadKitInventory();
		this.registerCommands();
		this.loadScoreBoard();
	

	}
	public void onDisable(){
		this.getLogger().info("Minigame Server Shutting Down");
		this.boardManager.onDisable();
	}
	
	private void registerCommands() {
		this.getCommand("join").setExecutor(new JoinCommand(this, this.wholegame));
		this.getCommand("leave").setExecutor(new LeaveCommand(this, this.wholegame));
		this.getCommand("kit").setExecutor(new KitCommand(INVENTORY, this.wholegame));
		
		Bukkit.getPluginManager().registerEvents(new PlayerDeath(this.wholegame), this);
		Bukkit.getPluginManager().registerEvents(new BedBreakEvent(this.wholegame), this);
		Bukkit.getPluginManager().registerEvents(new AllowBlock(this.wholegame), this);
		Bukkit.getPluginManager().registerEvents(new PlayerPVPAllow(this.wholegame), this);
		Bukkit.getPluginManager().registerEvents(new PlayerJoinServer(this.wholegame), this);
	}
	
	private void loadScoreBoard() {
		this.boardManager = new BoardManager(this, BoardSettings.builder().boardProvider(new ScoreBoardLoader(this.wholegame)).scoreDirection(ScoreDirection.UP).build());
	}
	
	private void loadKitInventory() {
		int row= ((kitData.getAllKits().size())/9)+1;
		int column=9;
		
		this.INVENTORY = SmartInventory.builder()
				.id("kitInventory")
		        .provider(new KitInventoryProvider(coinData, kitData))
		        .size(row, column)
		        .title("Choose Your Kit")
		        .closeable(true)
		        .build();
	}
	
	
}
