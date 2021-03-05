package fr.jas14.megacraft.players.jobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.jas14.megacraft.Main;
import fr.jas14.megacraft.others.ItemBuilder;
import fr.jas14.megacraft.others.inventory.CustomInventory;
import fr.jas14.megacraft.players.RPlayer;

public class poleEmploie implements CustomInventory {

	private Main main = Main.getInstance(); // ON RECUPERE LE MAIN
	private List<job> jobs = new ArrayList<>(); // LISTE DES RANKS A AFFICHER
	private jobManager jm = new jobManager(); // ON RECUPERE LE RANK MANAGER+
	private HashMap<Player, Integer> needwait = new HashMap<>(); // LISTE DES RANKS A AFFICHER

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "§bPôle §9Emploi";
	}

	@Override
	public void contents(Player player, Inventory inv) {
		// ON PREND LA LISTE DES RANKS A AFFICHER ET ON Y MET LA LISTE DES RANKS
		jobs.clear();
		jobs.addAll(main.jobs);
		Clear(inv); // ON MET DES VITRE PARTOUT

		int i = 0;
		while (i != 8) {
			if (jobs.isEmpty())
				return;// SI RANK ET VITE ON ANNULE

			job jb = jobs.get(0); // SINON ON RECUPERE LE REND
			inv.setItem(i,
					new ItemBuilder(jb.getIt()).setName(jb.getDisplayname()+ " §7§o(" + String.valueOf(jb.getSalaire())+ "€/h)").setLore(jb.getSimplename()).toItemStack()); // ON
																															// L'AFFICHE
			jobs.remove(0); // ET ON L'ENLEVE DE LA LISTE
			i++;
			if (i == 8) { // SI I = 8 ALORS ON CREE LA PAGE SUIVANTE
				inv.setItem(8, new ItemBuilder(Material.ARROW).setName("§cSuivant").toItemStack());
				i = 0;
				break;
			}
		}
	}

	@Override
	public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
		
		RPlayer rp = main.playertoRplayers.get(player);

		// ON MET EN PLACE LES OPTIONS QUAND UN UTILISATEUR APPUIE SUR UN GRADE DANS LA
		if (current.getItemMeta().hasLore()) {
			if (jm.jobExist(current.getItemMeta().getLore().get(0))) {
				

				job jb = jm.getJobByName(current.getItemMeta().getLore().get(0));
				if(rp.getJob().equals(jb)) {
					player.sendMessage(main.sname + "§cVous avez déjà ce travaille");
					return;
				}
				if(needwait.containsKey(player)) {
					if(needwait.get(player) < rp.getPlayhour()) {
						needwait.remove(player);
					}else {
						player.sendMessage(main.sname + "§cVous devez attendre une heures avant de pouvoir changer de métier");
						return;
					}
					
				}
				Clear(inv);

				// ITEM QUI PERMET DE CHANGER LE BLOQUE DU RANG
				inv.setItem(4, new ItemBuilder(jb.getIt()).setName(jb.getDisplayname()).setLore(jb.getSimplename())
						.toItemStack());
				// ITEM QUI PERMET DE CHANGER LE NOM AFFICHABLE DU RANG
				inv.setItem(2, new ItemBuilder(Material.WOOL).setData((short) 13).setName("§2Validé").toItemStack());
				// ITEM QUI PERMET DE CHANGER LA PERMISSION DU RANG
				inv.setItem(6, new ItemBuilder(Material.WOOL).setData((short) 14).setName("§4Annulé").toItemStack());
	
			}
		}
		if (current.getItemMeta().getDisplayName().equals("§2Validé")) {
			
			job jb =  jm.getJobByName(inv.getItem(4).getItemMeta().getLore().get(0));
			
			rp.setJob(jb);
			needwait.put(player, rp.getPlayhour());
			player.closeInventory();
			player.sendMessage(main.sname + "§2Vous venez de prendre le métier : " + jb.getDisplayname());
			
		}
		
		if (current.getItemMeta().getDisplayName().equals("§4Annulé")) {
			player.closeInventory();
			main.mn.open(player, poleEmploie.class);
			
		}
		
		
		if (current.getItemMeta().getDisplayName().equals("§cSuivant")) {
			Clear(inv);
			int i = 0;
			while (i != 8) {
				if (jobs.isEmpty())
					return;// SI RANK ET VITE ON ANNULE

				job jb = jobs.get(0); // SINON ON RECUPERE LE REND
				inv.setItem(i, new ItemBuilder(jb.getIt()).setName(jb.getDisplayname() + " §7§o(" + String.valueOf(jb.getSalaire())+ "€/h)").setLore(jb.getSimplename())
						.toItemStack()); // ON L'AFFICHE
				jobs.remove(0); // ET ON L'ENLEVE DE LA LISTE
				i++;
				if (i == 8) { // SI I = 8 ALORS ON CREE LA PAGE SUIVANTE
					inv.setItem(8, new ItemBuilder(Material.ARROW).setName("§cSuivant").toItemStack());
					i = 0;
					break;
				}
			}

		}

	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 9;
	}

	// REMPALCE TOUT L'INVENTAIRE PARD DES PANNEAUX.
	private void Clear(Inventory inv) {
		for (int i = 0; i < 9; i++) {
			inv.setItem(i, new ItemBuilder(Material.AIR).toItemStack());
			inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).setName("-").setData((short) 15).toItemStack());
		}
	}

}
