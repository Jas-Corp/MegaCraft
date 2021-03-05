package fr.jas14.megacraft.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.jas14.megacraft.Main;
import fr.jas14.megacraft.players.RPlayer;
import fr.jas14.megacraft.players.jobs.poleEmploie;
import fr.jas14.megacraft.players.jobs.policemenu;
import fr.jas14.megacraft.players.money.ATM;
import fr.jas14.megacraft.shop.shopMenu;
import net.citizensnpcs.api.event.NPCRightClickEvent;

public class interactionListener implements Listener{
	
	private Main main = Main.getInstance();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	private void onClick(PlayerInteractEvent  e) {
		
		if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			
			Player p = e.getPlayer();

			switch (e.getClickedBlock().getType().toString()) {
			
			// POUR LES ATM
		
			case "MEGACRAFT_ATM_BLOCK":
				System.out.println(p.getItemInHand().getType());

				if(p.getItemInHand().getType().toString().equals("MEGACRAFT_CB")) {
					e.setCancelled(true);
					Main.getInstance().mn.open(e.getPlayer(), ATM.class);
				}
				
				break;

			default:
				break;
			}
			
			
			
			
		}

	}
	
	
	@EventHandler
	private void npcRightClick(NPCRightClickEvent e) {
		if(e.getNPC().getName().equals("§bPôle §9Emploi")) {
			Main.getInstance().mn.open(e.getClicker(), poleEmploie.class);
			
		}
		if(e.getNPC().getName().equals("§1Commisaire")) {
			Main.getInstance().mn.open(e.getClicker(), policemenu.class);
			
		}
		RPlayer rp = main.playertoRplayers.get(e.getClicker());
		if(e.getNPC().getName().equals("§eMaire") && (rp.getFirstname().equals("§") || rp.getName().equals("§"))) {
			Main.getInstance().chatlistener.put(e.getClicker(), "create identity name");
			e.getClicker().sendMessage("§8[§fMaire§8] §eBonjour, pouvez vous me donner votre prénom ?");
			
		}
		main.shops.forEach(s->{
			if (e.getNPC().getName().equals(s.getName())) {
				
				if (s.getPerms().contains(rp.getJob().getSimplename()) || rp.getRank().getPower() >= 7) {
					main.playeropenShop.put(e.getClicker(), s);
					main.mn.open(e.getClicker(), shopMenu.class);
				}else {
					e.getClicker().sendMessage(main.sysmsg.notPermission);
				}
			}
		});
	}

}
