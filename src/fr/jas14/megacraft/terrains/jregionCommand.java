package fr.jas14.megacraft.terrains;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.jas14.megacraft.Main;
import fr.jas14.megacraft.others.messagesSystem;
import fr.jas14.megacraft.players.RPlayer;

public class jregionCommand implements CommandExecutor {

	private Main main = Main.getInstance();
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		
		if(!(sender instanceof Player)) return false;
		

		// CREATE PREFIX PERM TEMPS
		// REMOVE PREFIX

		messagesSystem sysmsg = new messagesSystem();
		Player p = (Player) sender;
		RPlayer rp = main.playertoRplayers.get(p);
		
		if (rp.getRank().getPower() < 7) {
			p.sendMessage(sysmsg.notPermission);
			return false;
		}
		
		if (args.length < 2) {
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("list")) {
					p.sendMessage(main.sname + "§cVoici la liste des régions :");
					main.tregions.forEach(t->{
						p.sendMessage("-> §f" + t.getPrefix());
					});
					return false;
				}	
			}
			
			p.sendMessage(main.sname + "§cVous devez faire la commande suivante : §f/jregion create prefix perm,perm,perm,... temps de respawn du block §cou §f/jregion remove prefix §cou /jregion list");
			
			return false;
		}
		
		if(args.length == 2){
			if(args[0].equalsIgnoreCase("remove")) {
				if (getRegionByName(args[1]) != null) {
					main.tregions.remove(getRegionByName(args[1]));
					p.sendMessage(main.sname + "§4la région a était supprimé.");
					return false;
				}else {
					p.sendMessage(main.sname + "§cla région n'existe pas");
					return false;
				}
				
			}
			
		}
		
		if(args.length == 4){
			if(args[0].equalsIgnoreCase("create")) {
				if (getRegionByName(args[1]) != null) {
					p.sendMessage(main.sname + "§4la région a existe déjà.");
					return false;
				}else {
					if(isInteger(args[3])) {
						new TResources(args[1], Arrays.asList(args[2].split(",")), Integer.valueOf(args[3]));
						p.sendMessage(main.sname + "§4Vous venez de crée la zone");
						
						
					}
					else {
						p.sendMessage(main.sname + "§cVous devez faire la commande suivante : §f/jregion create prefix perm,perm,perm,... temps de respawn du block §cou §f/jregion remove prefix");
						return false;
					}
					
					
				}
				
			}else {
				p.sendMessage(main.sname + "§cVous devez faire la commande suivante : §f/jregion create prefix perm,perm,perm,... temps de respawn du block §cou §f/jregion remove prefix");
				return false;
			}
			
		}else {
			p.sendMessage(main.sname + "§cVous devez faire la commande suivante : §f/jregion create prefix perm,perm,perm,... temps de respawn du block §cou §f/jregion remove prefix");
			return false;
		}
		
		
		
		
		
		
		
		return false;
	}
	
	
	public TResources getRegionByName (String name) {
		if(main.tregions.size() == 0) return null;
		for (int i = 0; i < main.tregions.size(); i++) {
			if(main.tregions.get(i).getPrefix().equals(name)) {
				return main.tregions.get(i);
			}
		}
		return null;
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

}
