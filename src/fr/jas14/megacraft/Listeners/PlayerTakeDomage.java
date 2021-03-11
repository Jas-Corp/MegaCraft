package fr.jas14.megacraft.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.golde.bukkit.corpsereborn.CorpseAPI.CorpseAPI;
import org.golde.bukkit.corpsereborn.CorpseAPI.events.CorpseClickEvent;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import fr.jas14.megacraft.Main;
import fr.jas14.megacraft.others.messagesSystem;
import fr.jas14.megacraft.players.RPlayer;

public class PlayerTakeDomage implements Listener {
	
	private Main main = Main.getInstance();
	private CorpseAPI corpAPI = main.getCorpseApi();
	private messagesSystem sysmsg = new messagesSystem();
	
	@SuppressWarnings("static-access")
	@EventHandler
	private void takeDamage(EntityDamageEvent e) {
		
		if(!(e.getEntity() instanceof Player)) return;
		Player p = (Player) e.getEntity();
		
		if (e.getDamage() >= p.getHealth()) {
			
			RPlayer rp = main.playertoRplayers.get(p);
			if(rp.getAsPlayer() != null) {
				p.removePotionEffect(PotionEffectType.SLOW);
				main.getCorpseApi().spawnCorpse(rp.getAsPlayer(), p.getLocation());
				rp.setAsPlayer(null);
			}
			rp.setDead(true);
			corpAPI.spawnCorpse(p, p.getLocation());
			e.setCancelled(true);
			p.setGameMode(GameMode.SPECTATOR);
			p.sendMessage(sysmsg.servername + "§cVous êtes dans le comas, si aucun medecin ne vien vous aider dans les 2 minutes vous serez mort.");
			new BukkitRunnable() {
		        @Override
		        public void run() {
		        	if (rp.isDead()) {
						p.setGameMode(GameMode.SURVIVAL);
						p.setHealth(0);
						rp.setDead(false);
					}
		        }
		    }.runTaskLater(Main.getInstance(), 20*120);
		}
		

	}
	
	@SuppressWarnings({ "static-access", "deprecation" })
	@EventHandler
	private void corpClick(CorpseClickEvent e) {
		Player p = e.getClicker();
		RPlayer rp = main.playertoRplayers.get(p);
		if(rp.getJob().getSimplename().equals("medecin")) {
			
			if(rp.getAsPlayer() != null) {
				p.sendMessage(sysmsg.vousportereja);
				return;
			}
			
			if (Bukkit.getPlayer(e.getCorpse().getCorpseName()) != null) {
				if(main.playertoRplayers.get(Bukkit.getPlayer(e.getCorpse().getCorpseName())).isDead()) {
					if (p.getDisplayName().equalsIgnoreCase(e.getCorpse().getCorpseName())) {
						return;
					}
					p.sendMessage(sysmsg.getCorp);
					rp.setAsPlayer(Bukkit.getPlayer(e.getCorpse().getCorpseName()));
					rp.getAsPlayer().sendMessage(sysmsg.onmeporte);
					corpAPI.removeCorpse(e.getCorpse());
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 9999999, 5), false);
					return;
				}
				
			}
			
			p.sendMessage(sysmsg.corpisDead);
				
				
			
			
		}else {
			p.sendMessage(sysmsg.cantGetCorp);
		}

	}
	
	@EventHandler
	private void onMouve(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		RPlayer rp = main.playertoRplayers.get(p);
		if(rp != null) {
			if (rp.isDead()) {
				e.setTo(e.getFrom());
				e.setCancelled(false);
			}
			if(rp.getAsPlayer() != null) {
				rp.getAsPlayer().teleport(p);
			}
			
		}
		

	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	private void onSnick(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		RPlayer rp = main.playertoRplayers.get(p);
		if (rp.getAsPlayer() != null) {
			main.getCorpseApi().spawnCorpse(rp.getAsPlayer(), p.getLocation());
			rp.getAsPlayer().sendMessage(sysmsg.servername + "§cle joueur vous à lacher");
			rp.setAsPlayer(null);
			p.sendMessage(sysmsg.vouslavezlacher);
			p.removePotionEffect(PotionEffectType.SLOW);
		}

	}
	
	
	@SuppressWarnings({"deprecation" })
	@EventHandler
	private void bed(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		RPlayer rp = main.playertoRplayers.get(p);
		if(rp == null) return;
		if(rp.getAsPlayer() != null) {
			if (e.getClickedBlock().getType().equals(Material.BED_BLOCK)) {
				if(rp.getAsPlayer().isOnline()) {
					Player target = rp.getAsPlayer();
					target.setGameMode(GameMode.SURVIVAL);
				
					target.setHealth(target.getMaxHealth()/2);
					RPlayer rtarget = main.playertoRplayers.get(target);
					rtarget.setDead(false);			
					rp.getAsPlayer().sendMessage(sysmsg.servername + "§ble joueur vous à réanimer !");
					p.sendMessage(sysmsg.servername + "§bVous avez réanimer le joueur !system");
					p.removePotionEffect(PotionEffectType.SLOW);
					rp.setAsPlayer(null);
					target.teleport(e.getClickedBlock().getLocation());
					
				}else {
					p.sendMessage(sysmsg.corpisDead);
				}
			}
		}
		
	}
	
	
	@EventHandler
	private void playerdead(PlayerRespawnEvent e) {
		e.getPlayer().teleport(new Location(e.getPlayer().getWorld(),391, 71, 775));

	}

	
	@EventHandler
	private void playerdead(PlayerSpawnLocationEvent e) {
		e.getPlayer().teleport(new Location(e.getPlayer().getWorld(),391, 71, 775));

	}

}
