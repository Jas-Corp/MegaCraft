package fr.jas14.megacraft.shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.jas14.megacraft.Main;
import fr.jas14.megacraft.players.RPlayer;

public class shopCommand implements CommandExecutor {

	private Main main = Main.getInstance();
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		if(!(sender instanceof Player)) return false;
		
		Player p = (Player) sender;
		
		RPlayer rp = main.playertoRplayers.get(p);
		
		
		if(!(rp.getRank().getPower() >= 7)) {
			p.sendMessage(main.sysmsg.notPermission);
			return false;
		}
		
		// /SHOP TRUC MACHIN,boulange,tamere 10,100,10,1000
		if (args.length == 5 && args[0].equalsIgnoreCase("create")) {
			List<Integer> prices = new ArrayList<>();
			if (getShopByName(args[1]) != null) {
				p.sendMessage(main.sname + "§cCe shop existe déjà");
				return false;
			}
			Arrays.asList(args[3].split(",")).forEach(s->{
			if (isInteger(s)) {
				prices.add(Integer.valueOf(s));
			}
				
			});
			if (prices.size() == Arrays.asList(args[3].split(",")).size()) {
				int i = 0;
				List<ItemStack> items = new ArrayList<>();
				while (i != 9) {
					if (p.getInventory().getItem(i) != null) {
						items.add(p.getInventory().getItem(i).clone());
					}
					i++;
					
				}
				if (items.size() == prices.size()) {
					new shopBase(args[1], items, prices, Arrays.asList(args[2].split(",")), isTrue(args[4]));
					p.sendMessage(main.sysmsg.shopCreated);
				}else {
					p.sendMessage(main.sysmsg.goodShopCommand);
				}
				return true;
			}else {
				p.sendMessage(main.sysmsg.goodShopCommand);
				return true;
			}
			
		}
		
		if (args.length == 2 && args[0].equalsIgnoreCase("delete")) {
			if (getShopByName(args[1]) != null) {
				main.shops.remove(getShopByName(args[1]));
				p.sendMessage(main.sname + "§cCe shop a était supprimer");
			}else {
				p.sendMessage(main.sname + "§cCe shop n'existe pas");
			}
			return true;
		}
		
		if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
			p.sendMessage(main.sname + "§cListe des shops;");
			main.shops.forEach(s->{
				p.sendMessage("§f-> §7" + s.getName());
			});
			return true;
			
		}		
		p.sendMessage(main.sysmsg.goodShopCommand);

		
		
		return false;
	}
	
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s);
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}
	
	public shopBase getShopByName(String name) {

		for (int i = 0; i < main.shops.size(); i++) {
			if(main.shops.get(i).getName().equals(name)) {
				return main.shops.get(i);
			}
		}
		return null;
	}
	
	public boolean isTrue(String name) {
		if (name.equalsIgnoreCase("true")) {
			return true;
		}
		return false;
	}

}
