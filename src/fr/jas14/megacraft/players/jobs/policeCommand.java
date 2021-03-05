package fr.jas14.megacraft.players.jobs;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.jas14.megacraft.Main;
import fr.jas14.megacraft.players.RPlayer;

public class policeCommand implements CommandExecutor {
	
	private Main main = Main.getInstance();


	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		
		if(!(sender instanceof Player)) return false;
		
		Player p = (Player) sender;
		RPlayer rp = main.playertoRplayers.get(p);
		
		// GESTION DE LA PERMISSION
		if (rp.getRank().getPower() < 7 && !(rp.getJob().getSimplename().equals("commissaire"))) {
			p.sendMessage(main.sysmsg.notPermission);
			return false;
		}
		
		// GESTION DU / LIST
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("list")) {
				p.sendMessage(main.sysmsg.servername + "§cVoici la liste ds joueurs en attente :");
				main.playerforPoliceWhitlists.forEach((name, grade)->{
					if (!(grade >= 100)) {
						
						p.sendMessage("-> §7" + name + " §fpostule pour " + main.policejobs.get(grade).getDisplayname());
					}

				});
			}
			return false;
		}
		
		// GESTION DU ACCEPTE DENY
		if(args.length == 2) {
			
			// SI LE JOUEUR EST DANS LA LIST 
			if(main.playerforPoliceWhitlists.containsKey(args[1])) {
				
				int perm = main.playerforPoliceWhitlists.get(args[1]); // ON DEFINIS LA PERMISSION LE JOUEUR ET LE RJOUEUR
				Player target = Bukkit.getPlayer(args[1]);
				RPlayer Rtarget = main.playertoRplayers.get(target);
				// SI LA CANDIDATURE A DEJA ETAIT ACCEPTER OU REFUSER
				if (perm >= 100) {
					p.sendMessage(main.sysmsg.playerNotHere);
					return false;
				}
				
				// SI JAMAIS C'EST ACCEPT
				if(args[0].equalsIgnoreCase("accept")) {
					if (Bukkit.getPlayer(args[1]) != null) {
						
						Rtarget.setJob(main.policejobs.get(perm));
						main.playerforPoliceWhitlists.remove(args[1]);
						
						p.sendMessage(main.sysmsg.acceptedCandidatureAdmin + target.getDisplayName());
						target.sendMessage(main.sysmsg.acceptedCandidature);
						
					}else {
						main.playerforPoliceWhitlists.put(args[1], (perm + 100)); // SI > 100 ALORS ACCEPTER 
						p.sendMessage(main.sysmsg.acceptedCandidatureAdmin + args[1]);
					}
					return true;
				}
				
				// SI JAMAIS C'EST DENY
				if(args[0].equalsIgnoreCase("deny")) {
					if (Bukkit.getPlayer(args[1]) != null) {
						
						main.playerforPoliceWhitlists.remove(args[1]);
						
						p.sendMessage(main.sysmsg.denyedCandidatureAdmin + target.getDisplayName());
						target.sendMessage(main.sysmsg.denyedCandidature);
						
					}else {
						main.playerforPoliceWhitlists.put(args[1], (perm + 200)); // SI > 200 ALORS REFUSER
						p.sendMessage(main.sysmsg.denyedCandidatureAdmin + args[1]);
					}
					
					return true;
				}
				
				
				
				
			}else {
				p.sendMessage(main.sysmsg.playerNotHere);
			}

		}
		
		p.sendMessage(main.sysmsg.truePoliceCommands);
		
		
		
		
		
		
		return false;
	}

}
