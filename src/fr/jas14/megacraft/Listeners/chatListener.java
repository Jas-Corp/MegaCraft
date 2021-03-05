package fr.jas14.megacraft.Listeners;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.jas14.megacraft.Main;
import fr.jas14.megacraft.others.ItemBuilder;
import fr.jas14.megacraft.others.messagesSystem;
import fr.jas14.megacraft.players.RPlayer;
import fr.jas14.megacraft.players.jobs.job;
import fr.jas14.megacraft.players.jobs.jobManager;
import fr.jas14.megacraft.players.rank.rank;
import fr.jas14.megacraft.players.rank.rankManager;

public class chatListener implements Listener {
	rankManager rm = new rankManager();
	jobManager jm = new jobManager();
	messagesSystem sysmsg = new messagesSystem();
	
	// QUAND UN JOUEUR ECRIT DANS LE TCHAT

	@SuppressWarnings("deprecation")
	@EventHandler
	private void onChating(AsyncPlayerChatEvent e) {
		System.out.println(e.getPlayer().getItemInHand().getType());
		
		e.setCancelled(true); // ON ANNULE L'ENVOIE ON RECUPERE LE JOUEUR ET LE MESSAGE
		Player p = e.getPlayer();
		String msg = e.getMessage();
		HashMap<Player, String> who = Main.getInstance().chatlistener; // ON RECUPERE LA LISTE DES GENS A QUI ON ATTEND UN MESSAGE
		if(who.containsKey(p)) { // SI LE JOUEUR Y EST
			
			String why = who.get(p); // ON RECUPERE POURQUOI ON ATTEND SONT MESSAGE
			who.remove(p); // ET ON LE RETIRE
			
			if(why.contains("create identity seconde name")) {
				p.sendMessage("§8[§fMaire§8] §eMerci ! Tenez votre carte d'identité");
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give "+ e.getPlayer().getName()+" megacraft:cd");
				RPlayer rp = Main.getInstance().playertoRplayers.get(p);
				rp.setFirstname(msg);
				Main.getInstance().chatlistener.remove(p);
			}
			if(why.contains("create identity name")) {
				p.sendMessage("§8[§fMaire§8] §eParfait ! Pouvez vous me donner votre nom ?");
				RPlayer rp = Main.getInstance().playertoRplayers.get(p);
				rp.setName(msg);
				Main.getInstance().chatlistener.put(p, "create identity seconde name");
			}
			
			if(why.contains("change rank name")) { // SI C'EST POUR UN CHANGEMENT DE NOM
				
				rank rk = rm.getRankByName(why.split(",", 0)[1]); // ON RECUPERE LE GRADE
				rk.setDisplayname(msg.replaceAll("&", "§")); // ON REMPLACE LES & par § POUR LA COULEUR 
				p.sendMessage(sysmsg.prefixchange + rk.getDisplayname()); // ON ENVOIE LE MESSAGE Au JOUEUR
				
			}
			
			if(why.contains("change rank power")) { // SI C'EST POUR CHANGER LE POWER
				
				rank rk = rm.getRankByName(why.split(",", 0)[1]); // ON RECUPERE LE GRADE
				if(isInteger(msg)) { // ON VERIFIE SI C'EST BIEN UN NOMBRE
					rk.setPower(Integer.valueOf(msg)); // O, C
					p.sendMessage(sysmsg.permissionchanged + String.valueOf(rk.getPower()));
				}else { // SINON ON ANNULE
					p.sendMessage(sysmsg.itsnumberserror);
				}
				
				
			}
			
			if(why.contains("change job name")) { // SI C'EST POUR UN CHANGEMENT DE NOM
				
				job jb = jm.getJobByName(why.split(",", 0)[1]); // ON RECUPERE LE GRADE
				jb.setDisplayname(msg.replaceAll("&", "§")); // ON REMPLACE LES & par § POUR LA COULEUR 
				p.sendMessage(sysmsg.prefixchange + jb.getDisplayname()); // ON ENVOIE LE MESSAGE Au JOUEUR
				
			}
			
			if(why.contains("change job power")) { // SI C'EST POUR CHANGER LE POWER
				
				job jb = jm.getJobByName(why.split(",", 0)[1]); // ON RECUPERE LE GRADE
				if(isInteger(msg)) { // ON VERIFIE SI C'EST BIEN UN NOMBRE
					jb.setPerm(Integer.valueOf(msg)); // O, C
					p.sendMessage(sysmsg.permissionchanged + String.valueOf(jb.getPerm()));
				}else { // SINON ON ANNULE
					p.sendMessage(sysmsg.itsnumberserror);
				}
				
				
			}
			
			if(why.contains("change job salaire")) { // SI C'EST POUR CHANGER LE POWER
				
				job jb = jm.getJobByName(why.split(",", 0)[1]); // ON RECUPERE LE GRADE
				if(isInteger(msg)) { // ON VERIFIE SI C'EST BIEN UN NOMBRE
					jb.setSalaire(Integer.valueOf(msg)); // O, C
					p.sendMessage(sysmsg.salairechanged + String.valueOf(jb.getSalaire()));
				}else { // SINON ON ANNULE
					p.sendMessage(sysmsg.itsnumberserror);
				}
				
				
			}
			
			switch (why) {
			case "create rank":
				String[] splited = msg.split("[,]", 0);
				if(splited.length !=3) { // SI IL N'Y AS PAS LES 3 ARGUMENT
					p.sendMessage(sysmsg.ranknotgoodinformations);
					return;
				}
				if(!isInteger(splited[1])) { // SI LE CHIFFRE N'EN EST PAS UN
					p.sendMessage(sysmsg.ranknotgoodinformations);
					return;
				}
				int power = Integer.valueOf(splited[1]);
				if(rm.rankExist(splited[0])) {
					p.sendMessage(sysmsg.rankAlreadyExist);
					return;
				}
				String replaced = splited[2].replaceAll("&", "§");
				p.sendMessage(sysmsg.servername + "§2Le rang " + replaced + "§r§2 vient d'être créé !");
				if(e.getPlayer().getItemInHand().getType().equals(Material.AIR)) {
					rm.createRank(0, replaced,splited[0], power, new ItemBuilder(Material.APPLE).toItemStack());
					return;
				}
				rm.createRank(0, replaced,splited[0], power, new ItemBuilder(e.getPlayer().getItemInHand().getType()).toItemStack());
				break;
				
			case "create job":
				String[] splited1 = msg.split("[,]", 0);
				if(splited1.length !=4) { // SI IL N'Y AS PAS LES 3 ARGUMENT
					p.sendMessage(sysmsg.jobnotgoodinformations);
					return;
				}
				if(!isInteger(splited1[1])) { // SI LE CHIFFRE N'EN EST PAS UN
					p.sendMessage(sysmsg.jobnotgoodinformations);
					return;
				}
				if(!isInteger(splited1[3])) { // SI LE CHIFFRE N'EN EST PAS UN
					p.sendMessage(sysmsg.jobnotgoodinformations);
					return;
				}
				
				int power1 = Integer.valueOf(splited1[1]);
				int money = Integer.valueOf(splited1[3]);
				if(jm.jobExist(splited1[0])) {
					p.sendMessage(sysmsg.jobAlreadyExist);
					return;
				}
				String replaced1 = splited1[2].replaceAll("&", "§");
				p.sendMessage(sysmsg.servername + "§2Le métier " + replaced1 + "§r§2 vient d'être créé !");
				if(e.getPlayer().getItemInHand().getType().equals(Material.AIR)) {
					jm.createJob(0, replaced1,splited1[0], power1, new ItemBuilder(Material.APPLE).toItemStack(), money);
					return;
				}
				jm.createJob(0, replaced1,splited1[0], power1, new ItemBuilder(e.getPlayer().getItemInHand().getType()).toItemStack(), money);
				break;

			default:
				break;

			
			
			}
			
			
			
			
			return;
			
		}
		
		RPlayer rp = Main.getInstance().playertoRplayers.get(p);
		
		if (msg.startsWith("!") && rp.getRank().getPower() > 1) {
			
			Bukkit.getOnlinePlayers().forEach(targets->{
				RPlayer Rtarget = Main.getInstance().playertoRplayers.get(targets);
				if(Rtarget.getRank().getPower() > 1) {
					targets.sendMessage("§8[§cSTAFF§8]-"+Main.getInstance().playertoRplayers.get(p).getRank().getDisplayname() + " §7" + Main.getInstance().playertoRplayers.get(p).getPseudo() + " §f§l>§r§f " + msg.replaceFirst("!", "")  );
				}
			});
			
			return;
		}
		
		if (msg.startsWith(";") && rp.getRank().getPower() > 7) {
			Bukkit.broadcastMessage("§8[§4§lANNONCE§8] §f" + msg.replaceFirst(";", "").replaceAll("&", "§"));
			return;
		}
		
		if (msg.startsWith("?")) {
			Bukkit.broadcastMessage("§8[§7ANONYME§8]§f§l>§r§f " + msg.replaceFirst("\\?", "")  );
		return;
		
		}
		
		
		if (msg.startsWith("€")) {
			Bukkit.broadcastMessage("§8[§2PUB§8]-"+ rp.getJob().getDisplayname() + " §7" + rp.getName() + " " + rp.getFirstname() + "§f§l>§r§f " + msg.replaceFirst("€", "") );
		return;
		}
		
		
		
		
		
		
		
		Bukkit.broadcastMessage("§8[§fHRP§8]-"+Main.getInstance().playertoRplayers.get(p).getRank().getDisplayname() + " §7" + Main.getInstance().playertoRplayers.get(p).getPseudo() + " §f§l>§r§f " + msg );
		
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
