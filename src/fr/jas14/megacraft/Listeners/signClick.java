package fr.jas14.megacraft.Listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import fr.jas14.megacraft.Main;
import fr.jas14.megacraft.players.RPlayer;

public class signClick implements Listener{
	
	private Main main = Main.getInstance();
	
	@EventHandler
	private void playerClick(PlayerInteractEvent e) {
		if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !e.getAction().equals(Action.LEFT_CLICK_BLOCK)) return;
		Action action = e.getAction();
			
		
		if (e.getClickedBlock().getType().equals(Material.WALL_SIGN) || e.getClickedBlock().getType().equals(Material.SIGN_POST)) {
			Player p = e.getPlayer();
			RPlayer rp = main.playertoRplayers.get(p);
			Sign sign = (Sign) e.getClickedBlock().getState();
			
			RegionManager regionManager = Main.getInstance().getWorldGuard().getRegionManager(e.getClickedBlock().getWorld());

			if (regionManager.getRegion(sign.getLine(1)) != null) {
				ProtectedRegion rg = regionManager.getRegion(sign.getLine(1));
				if (rg.hasMembersOrOwners()) {
					
					String uuid = rg.getOwners().getUniqueIds().toString().replace("[", "").replace("]", "");
		            OfflinePlayer op = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
		            String playerName = op.getName();
					sign.setLine(0, "§7[§4Acheter§7]");
					sign.setLine(3, "Par :§e" + playerName);
					sign.update();
					if (playerName.equals(p.getDisplayName())) {
						if (p.isSneaking()) {
							rg.getOwners().clear();
							sign.setLine(0, "§7[§4A vendre§7]");
							sign.setLine(1, rg.getId());
							sign.setLine(3, "");
							sign.update();
							int moneyneeded =  Integer.valueOf(sign.getLine(2).replaceAll("€", "").replaceAll("§2", ""));
							int addmoney = moneyneeded - (moneyneeded*20/100);
							rp.addMoney(addmoney);
							p.sendMessage(main.sysmsg.parcelsell);
							
						}else {
							p.sendMessage(main.sysmsg.trysell);
						}
					}
					if (p.isSneaking()) {
						if(rp.getRank().getPower() >= 7) {
							rg.getOwners().clear();
							sign.setLine(0, "§7[§4A vendre§7]");
							sign.setLine(1, rg.getId());
							sign.setLine(3, "");
							sign.update();
							p.sendMessage(main.sysmsg.forcesell);
						}
					}
						
					
					return;
				}
				
				int moneyneeded =  Integer.valueOf(sign.getLine(2).replaceAll("€", "").replaceAll("§2", ""));
				
				if (p.isSneaking()) {
					if(rp.getRank().getPower() >= 7) {
						if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
							sign.setLine(2, "§2" + String.valueOf(moneyneeded+100) +"€");
						}
						if (action.equals(Action.LEFT_CLICK_BLOCK)) {
							e.setCancelled(true);
							if ((moneyneeded - 100) <= 0) {
								sign.setLine(2, "§2200€");
							}else {
								sign.setLine(2, "§2" + String.valueOf(moneyneeded-100) +"€");
							}
							
						}
						sign.update();
						return;
					}
				}
				
				if (rp.getMoney() >= moneyneeded) {
					rp.removeMoney(moneyneeded);
					rg.getOwners().addPlayer(p.getUniqueId());
					p.sendMessage(main.sysmsg.parcelBuy + sign.getLine(1));
					sign.setLine(0, "§7[§4Acheter§7]");
					sign.setLine(3, "Par :§e" + p.getDisplayName());
					sign.update();
				}else {
					p.sendMessage(main.sysmsg.nomoney);
				}
				
			}
			
		}

	}

}
