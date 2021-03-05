package fr.jas14.megacraft.Listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;

import fr.jas14.megacraft.Main;
import fr.jas14.megacraft.others.ItemBuilder;
import fr.jas14.megacraft.players.RPlayer;

public class blockBreakingEvent implements Listener {
	
	@EventHandler
	public void blockBreka(BlockBreakEvent e) {



		RegionManager regionManager = Main.getInstance().getWorldGuard().getRegionManager(e.getBlock().getWorld());
        ApplicableRegionSet applicableRegionSet = regionManager.getApplicableRegions(e.getBlock().getLocation());
        RPlayer rp = Main.getInstance().playertoRplayers.get(e.getPlayer());
        applicableRegionSet.getRegions().forEach(p->{
        	
        	if (e.getBlock().getType().equals(Material.WALL_SIGN) || e.getBlock().getType().equals(Material.SIGN_POST)) {
        		Sign sign = (Sign)e.getBlock().getState();
        		if (p.getId().equals(sign.getLine(1))) {
        			if (!e.getPlayer().isOp() && rp.getRank().getPower() != 1 && !(rp.getRank().getPower() >= 7)) {
						e.setCancelled(true);
						e.getPlayer().sendMessage(Main.getInstance().sysmsg.notPermission);
					}
					
				}
        	}
       
			if (p.hasMembersOrOwners()) {
				String uuid = p.getOwners().getUniqueIds().toString().replace("[", "").replace("]", "");
	            OfflinePlayer op = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
	            String playerName = op.getName();
	        	if (!e.getPlayer().getDisplayName().equals(playerName)) {
	        		if (!e.getPlayer().isOp() && rp.getRank().getPower() != 1 && !(rp.getRank().getPower() >= 7)) {
						 e.setCancelled(true);
					}

				}
			}
			
			if (e.getBlock().getType().equals(Material.IRON_ORE) || e.getBlock().getType().equals(Material.GOLD_ORE) || e.getBlock().getType().equals(Material.COAL_ORE) || e.getBlock().getType().equals(Material.DIAMOND_ORE) || e.getBlock().getType().equals(Material.REDSTONE_ORE) ||e.getBlock().getType().equals(Material.LAPIS_ORE) ) {
				if (rp.getJob().getSimplename().equals("mineur")) {
					e.getBlock().getDrops().forEach(it->{
    					e.getPlayer().getInventory().addItem(it);
    					blockRegen(e.getBlock().getState(), 20*20);
    					e.getBlock().setType(Material.AIR);
    				});					
					e.getBlock().setType(Material.AIR);
					return;
				}
				
				
				
				
			}
     		
        	Main.getInstance().tregions.forEach(t->{
        		if (p.getId().contains(t.getPrefix())) {
        			if(t.getPerms().contains(rp.getJob().getSimplename())) {
        				if (e.getBlock().getType().equals(Material.GRASS)) {
        					if (!e.getPlayer().isOp() && rp.getRank().getPower() != 1 && !(rp.getRank().getPower() >= 7)) {
       						 e.setCancelled(true);
       					}
							return;
						}
        				if (e.getBlock().getType().equals(Material.SAND) || e.getBlock().getType().equals(Material.PURPUR_SLAB) || e.getBlock().getType().equals(Material.RAILS) || e.getBlock().getType().equals(Material.STAINED_CLAY) || e.getBlock().getType().equals(Material.CONCRETE)|| e.getBlock().getType().equals(Material.DIRT) ) {
        					if (!e.getPlayer().isOp() && rp.getRank().getPower() != 1 && !(rp.getRank().getPower() >= 7)) {
       						 e.setCancelled(true);
        					}
							return;
						}
        				if (e.getBlock().getType().equals(Material.LEAVES)) {
							e.getPlayer().getInventory().addItem(new ItemBuilder(Material.APPLE).toItemStack());
							blockRegen(e.getBlock().getState(), 20*t.getRespawntime());
        					e.getBlock().setType(Material.AIR);
						}
        				e.getBlock().getDrops().forEach(it->{
        					e.getPlayer().getInventory().addItem(it);
        					blockRegen(e.getBlock().getState(), 20*t.getRespawntime());
        					e.getBlock().setType(Material.AIR);
        				});
        			}
					
				}
        	});
        });
	
        
        if (applicableRegionSet.getRegions().size() <= 0) {
        	if (!e.getPlayer().isOp() && rp.getRank().getPower() != 1 && !(rp.getRank().getPower() >= 7)) {
				 e.setCancelled(true);
			}
		}
       
      
	
	
	
	}
	@EventHandler
	private void Blockplace(BlockPlaceEvent e) {
		
		RegionManager regionManager = Main.getInstance().getWorldGuard().getRegionManager(e.getBlock().getWorld());
        ApplicableRegionSet applicableRegionSet = regionManager.getApplicableRegions(e.getBlock().getLocation());
        RPlayer rp = Main.getInstance().playertoRplayers.get(e.getPlayer());
        applicableRegionSet.getRegions().forEach(p->{
        	
         	if (p.getId().contains("parcel")) {
        		if (!p.hasMembersOrOwners()) {
					if (e.getBlock().getType().equals(Material.WALL_SIGN) || e.getBlock().getType().equals(Material.SIGN_POST)) {
						Sign sign = (Sign)e.getBlock().getState();
						sign.setLine(0, "§7[§4A vendre§7]");
						sign.setLine(1, p.getId());
						sign.setLine(2, "§2200€");
						sign.update();
						
					}
				}
				
			}
         	
        	if (p.hasMembersOrOwners()) {
				String uuid = p.getOwners().getUniqueIds().toString().replace("[", "").replace("]", "");
	            OfflinePlayer op = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
	            String playerName = op.getName();
	        	if (!e.getPlayer().getDisplayName().equals(playerName)) {
	        		if (!e.getPlayer().isOp() && rp.getRank().getPower() != 1 && !(rp.getRank().getPower() >= 7)) {
						 e.setCancelled(true);
					}

				}
			}
        });
        
        if (applicableRegionSet.getRegions().size() <= 0) {
        	if (!e.getPlayer().isOp() && rp.getRank().getPower() != 1 && !(rp.getRank().getPower() >= 7)) {
				 e.setCancelled(true);
			}
		}

	}
	
	public void blockRegen(BlockState bs, int regenTime) {
	    new BukkitRunnable() {
	        @Override
	        public void run() {
	        	bs.update(true);
	        }
	        
	        @EventHandler
	        private void onDeath(EntityDeathEvent e) {
				if (e.getEntity() instanceof Player) return;
					
				

			}
	    }.runTaskLater(Main.getInstance(), regenTime);
	}

}
