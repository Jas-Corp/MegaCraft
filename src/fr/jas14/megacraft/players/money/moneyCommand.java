package fr.jas14.megacraft.players.money;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.jas14.megacraft.Main;
import fr.jas14.megacraft.others.messagesSystem;
import fr.jas14.megacraft.players.RPlayer;
import fr.jas14.megacraft.players.rank.rank;

public class moneyCommand implements CommandExecutor {
	
	private Main main = Main.getInstance();
	private messagesSystem sysmsg = new messagesSystem();
	private String sname = "§8[§2§oBanque§8] ";
	private String pMission = sysmsg.notPermission;
	private String formulation = sname + "§cVous devez formuler la commande suivante §f/money §7(add/remove/set) joueur nombre";
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(!(sender instanceof Player)) return false; // SI L'ENVOIEUR ET LA CONSOLE ON RETOURNE ARRETE.
		
		Player p = (Player) sender; // ON RECUPERE LE JOUEUR
		RPlayer rp = main.playertoRplayers.get(p); 
		rank rk = rp.getRank();
		
		// SI IL N'Y AS PAS D'ARGUMENT ON RETOURNE L'ARGENT DU JOUEUR
		if(args.length == 0) {
			p.sendMessage(sname+ "§fVous avez §2" + String.valueOf(rp.getMoney()) + "€");
		}
		
		
		// SI LA COMMANDE EST /MONEY TEXT
		if(args.length == 1) {
			// SI LE JOUEUR A LA PERM MODERATEUR GERANT ADMIN OU FONDATEUR
			if(rk.getPower() == 3 || rk.getPower() >= 7) { 
				String name = args[0]; // ON RECUPERE LE TEXT VOIR SI C'EST UN PSEUDO
				if(Bukkit.getPlayer(name) != null) { // SI OUI
					Player target = Bukkit.getPlayer(name);
					RPlayer RTarget = main.playertoRplayers.get(target);
					p.sendMessage(sname + "§fLe joueur §7" + name + "§f à §2" + String.valueOf(RTarget.getMoney()) + "€");
				}else {
					p.sendMessage(sname + "§cLe joueur §f" + name + " §cn'existe pas ou n'est pas connecté.");
				}
			}else {
				p.sendMessage(pMission);
			}
			
		}
		
		
		if(args.length > 1) {
			if(rk.getPower() >= 7) { 
				if(args.length == 3) {
					String name = args[1]; // ON RECUPERE LE TEXT VOIR SI C'EST UN PSEUDO
					if(Bukkit.getPlayer(name) != null) { // SI OUI
						// ON VERIFIE SI LE 3 EM ARGUEMENT ET BIEN UN NOMBRE SINON ON ANNULE
						if(!isInteger(args[2])) {
							p.sendMessage(formulation);
							return false;
						}
						int msgInt = Integer.valueOf(args[2]);
						//ON DEFINI LA CIBLE
						Player target = Bukkit.getPlayer(name);
						RPlayer RTarget = main.playertoRplayers.get(target);
						
						// O? RECUPERE La PREMIER INFORMATION
						switch (args[0]) {
						case "add":
							RTarget.addMoney(msgInt);
							p.sendMessage(sname + "§fVous venez d'ajouter §2" + args[2] + "€ §fau joueur §7" + args[1]);
							target.sendMessage(sname + "§fLe joueur §7" + p.getDisplayName() + " §fvient de vous ajouter §2" + args[2] + "€");
							break;
						case "remove":
							if(!RTarget.removeMoney(msgInt)) { // SI ON RETIRE PLUS QUE l'ARGENT QUE LE JOUEUR A
								RTarget.setMoney(0); // ALORS ON MET SONT ARGENT A 0
							}
							p.sendMessage(sname + "§fVous venez de retirer §4" + args[2] + "€ §fau joueur §7" + args[1]);
							target.sendMessage(sname + "§fLe joueur §7" + p.getDisplayName() + " §fvient de vous enlever §4" + args[2] + "€");
							break;
							
						case "set":
							RTarget.setMoney(msgInt);
							p.sendMessage(sname + "§fVous venez de définir l'argent du joueur §7" + args[1] + "§fsur §e" + args[2] + "€");
							target.sendMessage(sname + "§fLe joueur §7" + p.getDisplayName() + " §fvient de définir votre argent sur §e" + args[2] + "€");
							break;
						

						default:
							p.sendMessage(formulation);
							return false;
							
						}
					}else {
						p.sendMessage(sname + "§cLe joueur §f" + name + " §cn'existe pas ou n'est pas connecté.");
					}
					
				}else {
					
					p.sendMessage(formulation);
				}
				
			}
			else{
				p.sendMessage(pMission);
			}
			
			
		}
		
		
		
		
		
		
		return false;
	}
	
	public static boolean isInteger(String s) {
	    try { 
	        Long.parseLong(s);
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}

}
