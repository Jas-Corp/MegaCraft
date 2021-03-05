package fr.jas14.megacraft.Listeners;


import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;

import fr.jas14.megacraft.Main;
import fr.jas14.megacraft.players.RPlayer;


public class ConnectionDeconectionManager implements Listener {
	private Main main = Main.getInstance();

	@EventHandler
	private void playerConnection(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();	
		main.sql.loadAccount(p); // ON CREE LE COMPTE SI LE JOUEUR EN N'AS PAS SINON ON LE CHARGE
		RPlayer rp = main.playertoRplayers.get(p);
		e.setJoinMessage(main.sysmsg.servername + "§f[§2+§f] §e"+ p.getDisplayName()+ " " + rp.getRank().getDisplayname()  );
		
		
		
		
		
		//Gestion des candidature de police
	
		if (main.playerforPoliceWhitlists.containsKey(p.getDisplayName())) {
			int perm = main.playerforPoliceWhitlists.get(p.getDisplayName());
			if (perm >= 100 && perm < 200) {
				perm = perm - 100;
				rp.setJob(main.policejobs.get(perm));
				p.sendMessage(main.sysmsg.acceptedCandidature);
				main.playerforPoliceWhitlists.remove(p.getDisplayName());
			}
			if (perm > 200) {
			
				p.sendMessage(main.sysmsg.denyedCandidature);
				main.playerforPoliceWhitlists.remove(p.getDisplayName());
			}
			
		}
	
	
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	private void playerDeconnection(PlayerQuitEvent e) {
		
		
		
		
		
		Player p = e.getPlayer();
		RPlayer rp = main.playertoRplayers.get(p);
		
		// Gestion de la mort et de quand on porte un joueur
		if(rp.isDead()) {
			p.setGameMode(GameMode.SURVIVAL);
			p.setHealth(0);
			rp.setDead(false);
			
		}
		
		if(rp.getAsPlayer() != null) {
			p.removePotionEffect(PotionEffectType.SLOW);
			main.getCorpseApi().spawnCorpse(rp.getAsPlayer(), p.getLocation());
			rp.setAsPlayer(null);
			
		}
		e.setQuitMessage(main.sysmsg.servername + "§f[§4-§f] §e"+ p.getDisplayName()+ " " + rp.getRank().getDisplayname());
		main.sql.saveAccount(p);
		main.playertoRplayers.remove(p);

	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		
		/*if (e.getFrom().getY()<e.getTo().getY()) {
			ProtocolManager pm = ProtocolLibrary.getProtocolManager();
			PacketContainer packet = pm.createPacket(PacketType.Play.Server.ANIMATION);
			packet.getModifier().writeDefaults();
			packet.getIntegers().write(0, e.getPlayer().getEntityId()).write(1, 0);
			try {
				pm.sendServerPacket(e.getPlayer(), packet);
			} catch (InvocationTargetException e1) {
				e1.printStackTrace();
			}
		}*/
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
