package fr.jas14.megacraft;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.golde.bukkit.corpsereborn.CorpseAPI.CorpseAPI;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import fr.jas14.megacraft.Listeners.ConnectionDeconectionManager;
import fr.jas14.megacraft.Listeners.PlayerTakeDomage;
import fr.jas14.megacraft.Listeners.blockBreakingEvent;
import fr.jas14.megacraft.Listeners.chatListener;
import fr.jas14.megacraft.Listeners.interactionListener;
import fr.jas14.megacraft.Listeners.signClick;
import fr.jas14.megacraft.database.DataBaseManager;
import fr.jas14.megacraft.database.SQLGetter;
import fr.jas14.megacraft.others.messagesSystem;
import fr.jas14.megacraft.others.inventory.CustomInventory;
import fr.jas14.megacraft.others.inventory.InventoryManager;
import fr.jas14.megacraft.players.RPlayer;
import fr.jas14.megacraft.players.jobs.job;
import fr.jas14.megacraft.players.jobs.jobManager;
import fr.jas14.megacraft.players.jobs.policeCommand;
import fr.jas14.megacraft.players.money.moneyCommand;
import fr.jas14.megacraft.players.rank.modoCommand;
import fr.jas14.megacraft.players.rank.rank;
import fr.jas14.megacraft.shop.shopBase;
import fr.jas14.megacraft.shop.shopCommand;
import fr.jas14.megacraft.terrains.TResources;
import fr.jas14.megacraft.terrains.jregionCommand;


public class Main extends JavaPlugin{
	
	private static Main instance;
	
	public messagesSystem sysmsg = new messagesSystem();
	public String sname = sysmsg.servername;
	public InventoryManager mn = new InventoryManager(this);
	public DataBaseManager database;

	public int hour = 0;
	
	// POUR LE NOMMAGE D'UN RANK "create rank"
	
	public HashMap<Player, String> chatlistener = new HashMap<>();
	public HashMap<Player, shopBase> playeropenShop = new HashMap<>();
	public HashMap<Player, RPlayer> playertoRplayers = new HashMap<>();
	
	public List<RPlayer> rplayers = new ArrayList<>(); // SAVED
	public List<rank> ranks = new ArrayList<>(); // SAVED
	public List<job> jobs = new ArrayList<>(); // SAVED
	public List<shopBase> shops = new ArrayList<>(); // NOTTTTTTTTTTTTTTT SAVED
	public List<TResources> tregions = new ArrayList<>(); // SAVED
	public HashMap<String, Integer> playerforPoliceWhitlists = new HashMap<>();
	
	public List<job> policejobs = new ArrayList<>();



	public Map<Class<? extends CustomInventory>, CustomInventory> registeredMenus = new HashMap<>();
	
	public SQLGetter sql = new SQLGetter(this);
	@Override
	public void onEnable() {
		instance = this;
		database = new DataBaseManager("jdbc:mysql://", "217.182.51.144:3306" , "s11_save", "u11_55RcDhrEdy" , "M9xqUO4mEy@F^s+O1Xah=qCX");
		database.connection();
		sql.createTable();
		new jobManager().loadPoliceJobs();
		
		try {
			sql.loadAll();
		} catch (IOException e) {
		
			e.printStackTrace();
		}

		PluginManager pl = Bukkit.getPluginManager();
		pl.registerEvents(new chatListener(), this);
		pl.registerEvents(new blockBreakingEvent(), this);
		pl.registerEvents(new interactionListener(), this);
		pl.registerEvents(new InventoryManager(this), this);
		pl.registerEvents(new PlayerTakeDomage(), this);
		pl.registerEvents(new ConnectionDeconectionManager(), this);
		pl.registerEvents(new signClick(), this);
		getCommand("money").setExecutor(new moneyCommand());
		getCommand("jregion").setExecutor(new jregionCommand());
		getCommand("police").setExecutor(new policeCommand());
		getCommand("modo").setExecutor(new modoCommand());
		getCommand("rank").setExecutor(new modoCommand());
		getCommand("job").setExecutor(new modoCommand());
		getCommand("skick").setExecutor(new modoCommand());
		getCommand("spardon").setExecutor(new modoCommand());
		getCommand("sban").setExecutor(new modoCommand());
		getCommand("itemname").setExecutor(new modoCommand());
		getCommand("shop").setExecutor(new shopCommand());

		startCount();
		
	}
	
	@Override
	public void onDisable() {
		Bukkit.getOnlinePlayers().forEach(p->{
			RPlayer rp = playertoRplayers.get(p);
			if (rp.isDead()) {
				p.setGameMode(GameMode.SURVIVAL);
				p.setHealth(0);
			}
		});
		sql.saveAll();
		database.deconnection();
		
		
	}
	
	public static Main getInstance() {
		return instance;
	}
	
	@SuppressWarnings("deprecation")
	public void startCount() {		
		
	Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.getInstance(), new Runnable() {
				
		@Override
		public void run() {
				hour++;
				Bukkit.getOnlinePlayers().forEach(p -> {
					
					RPlayer rp = playertoRplayers.get(p);
					rp.setPlayhour(rp.getPlayhour() + 1);
					rp.addMoney(rp.getJob().getSalaire());
					sql.saveAll();
					
					p.sendMessage(sysmsg.banque + "§fVous venez de recevoir votre salaire de : §2" + String.valueOf(rp.getJob().getSalaire())+ "€");
				
				});
				
			
			
		}
		

		
		
	}, 0, (20*60)*60);
		

	}
	
	public int getHour() {
		return hour;
	}
	
	public void setHour(int hour) {
		this.hour = hour;
	}
	
	public WorldGuardPlugin getWorldGuard() {
		Plugin plugin = this.getServer().getPluginManager().getPlugin("WorldGuard");
		if(plugin == null || !(plugin instanceof WorldGuardPlugin)) {
			return null;
		}
		return (WorldGuardPlugin)plugin;
	}
	
	public CorpseAPI getCorpseApi() {
		Plugin plugin = this.getServer().getPluginManager().getPlugin("CorpseAPI");
		if(plugin == null || !(plugin instanceof CorpseAPI)) {
			return null;
		}
		return (CorpseAPI)plugin;
	}
	

}
