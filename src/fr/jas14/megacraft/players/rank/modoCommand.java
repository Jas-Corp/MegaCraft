package fr.jas14.megacraft.players.rank;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.jas14.megacraft.Main;
import fr.jas14.megacraft.others.ItemBuilder;
import fr.jas14.megacraft.players.RPlayer;
import fr.jas14.megacraft.players.jobs.job;

public class modoCommand implements CommandExecutor {
	
	private Main main = Main.getInstance();
	
	@SuppressWarnings("deprecation")
	@Override 
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		if(!(sender instanceof Player)) return false;
		
		Player p = (Player) sender;
		RPlayer rp = Main.getInstance().playertoRplayers.get(p);
		
		
		
		
	if (arg.equalsIgnoreCase("skick") && rp.getRank().getPower() >= 2) {
			
			if (args.length >= 2) {
				if (Bukkit.getPlayer(args[0]) != null) {
					RPlayer target = main.playertoRplayers.get(Bukkit.getPlayer(args[0]));
					if(target.getRank().getPower() >= rp.getRank().getPower()) {
						
						p.sendMessage(main.sysmsg.notPermission);
						return false;
						
					}
					StringBuffer sb = new StringBuffer();
				    for(int i = 0; i < args.length; i++) {
				    	if (i != 0) {
				    		sb.append(args[i] + " ");
						}
				         
				      }
				    p.sendMessage(main.sysmsg.kick);
				    Bukkit.getPlayer(args[0]).kickPlayer(main.sysmsg.servername + "\n§fVous avez était kick pour : §e" + sb.toString() + "\n§f Par :§c" + p.getDisplayName());
					
					
				}else {
					p.sendMessage(main.sysmsg.playerNotHere);
				}
				
			}else {
				p.sendMessage(main.sysmsg.goodkickcommand);
			}
			
			return false;
				
			}
	
	
	if (arg.equalsIgnoreCase("sban") && rp.getRank().getPower() >= 3) {
		
		if (args.length >= 2) {
			if (Bukkit.getPlayer(args[0]) != null) {
				RPlayer target = main.playertoRplayers.get(Bukkit.getPlayer(args[0]));
				if(target.getRank().getPower() >= rp.getRank().getPower()) {
					
					p.sendMessage(main.sysmsg.notPermission);
					return false;
					
				}
		
				StringBuffer sb = new StringBuffer();
			    for(int i = 0; i < args.length; i++) {
			    	if (i != 0) {
			    		sb.append(args[i] + " ");
					}
			         
			      }
			    p.sendMessage(main.sysmsg.ban);
			    Bukkit.getServer().dispatchCommand(main.getServer().getConsoleSender(), "ban " + Bukkit.getPlayer(args[0]).getDisplayName() + " " +sb.toString());    
				
			}else {
				p.sendMessage(main.sysmsg.playerNotHere);
			}
			
		}else {
			p.sendMessage(main.sysmsg.goodbancommand);
		}
	
			return false;
		}
	
		if (arg.equalsIgnoreCase("spardon") && rp.getRank().getPower() >= 3) {
		
		if (args.length >= 1) {


			    p.sendMessage(main.sysmsg.servername + "§2Vous avez deban le joueur " +args[0]);
			    Bukkit.getServer().dispatchCommand(main.getServer().getConsoleSender(), "pardon " + args[0]);    
				
			
			
		}else {
			p.sendMessage(main.sysmsg.servername + "§c/pardon player");
		}
	
			return false;
		}
		

		
		
		// ON RETOURNE SI PAS LA PERM
		if(!(rp.getRank().getPower() >= 7) && !p.isOp()) {
			p.sendMessage(Main.getInstance().sysmsg.notPermission);
			return false;
		}
		
		// GESTION DU / MODO
		if (arg.equalsIgnoreCase("modo")) {
			Main.getInstance().mn.open(p, moderationMenu.class);
		}
		
		
		// GESTION DU JOB
		if (arg.equalsIgnoreCase("job")) {
			
		if (args.length == 2) {
			if (Bukkit.getPlayer(args[0]) != null) {
				if (getJobByName(args[1]) != null) {
					RPlayer target = main.playertoRplayers.get(Bukkit.getPlayer(args[0]));
					target.setJob(getJobByName(args[1]));
					p.sendMessage(main.sysmsg.setjob + getJobByName(args[1]).getDisplayname());
					Bukkit.getPlayer(args[0]).sendMessage(main.sysmsg.setjobn + getJobByName(args[1]).getDisplayname());
				}else {
					p.sendMessage(main.sysmsg.servername + "§cCe métier n'existe pas");
				}
				
			}else {
				p.sendMessage(main.sysmsg.playerNotHere);
			}
			
		}else {
			p.sendMessage(main.sysmsg.goodjobcommand);
		}
	
			
		}
		
		// GESTION DU GRADE
		if (arg.equalsIgnoreCase("rank")) {
			
			if (args.length == 2) {
				if (Bukkit.getPlayer(args[0]) != null) {
					if (getRankByName(args[1]) != null) {
						RPlayer target = main.playertoRplayers.get(Bukkit.getPlayer(args[0]));
						target.setRank(getRankByName(args[1]));
						p.sendMessage(main.sysmsg.setrank + getRankByName(args[1]).getDisplayname());
						Bukkit.getPlayer(args[0]).sendMessage(main.sysmsg.setrankn + getRankByName(args[1]).getDisplayname());
					}else {
						p.sendMessage(main.sysmsg.servername + "§cCe grade n'existe pas");
					}
					
				}else {
					p.sendMessage(main.sysmsg.playerNotHere);
				}
				
			}else {
				p.sendMessage(main.sysmsg.goodrankcommand);
			}
		
				
			}
		
		if (arg.equalsIgnoreCase("itemname")) {
			StringBuffer sb = new StringBuffer();
		    for(int i = 0; i < args.length; i++) {
		    		sb.append(args[i] + " ");
				
		         
		      }
		    String nname = sb.toString().replaceAll("&", "§");
		    System.out.println(nname);
			new ItemBuilder(p.getItemInHand()).setName(nname).toItemStack();
			p.updateInventory();
			p.sendMessage(main.sysmsg.servername + "§fVotre items a bien était renomé !");
			
			
		}
		
		


		
		
		
		
		
		
		
		
		
		
		return false;
	}
	
	
	public job getJobByName(String name) {

		for (int i = 0; i < main.jobs.size(); i++) {
			if(main.jobs.get(i).getSimplename().equals(name)) {
				return main.jobs.get(i);
			}
		}
		for (int i = 0; i < main.policejobs.size(); i++) {
			if(main.policejobs.get(i).getSimplename().equals(name)) {
				return main.policejobs.get(i);
			}
		}
		return null;
	}
	
	public rank getRankByName(String name) {
		for (int i = 0; i < main.ranks.size(); i++) {
			if(main.ranks.get(i).getSimplename().equals(name)) {
				return main.ranks.get(i);
			}
		}
		
		return null;
	}

}
