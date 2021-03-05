package fr.jas14.megacraft.players.jobs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.jas14.megacraft.Main;
import fr.jas14.megacraft.others.ItemBuilder;
import fr.jas14.megacraft.others.messagesSystem;
import fr.jas14.megacraft.others.inventory.CustomInventory;
import fr.jas14.megacraft.players.rank.moderationMenu;

public class jobInventory implements CustomInventory {

	private Main main = Main.getInstance(); // ON RECUPERE LE MAIN
	private List<job> jobs = new ArrayList<>(); // LISTE DES RANKS A AFFICHER
	private jobManager jm = new jobManager(); // ON RECUPERE LE RANK MANAGER
	private messagesSystem sysmsg = new messagesSystem();
	
	
	@Override
	public String name() {
		return main.sname + "§4Gestion des métiers";
	}

	@Override
	public void contents(Player player, Inventory inv) {

		// ON PREND LA LISTE DES RANKS A AFFICHER ET ON Y MET LA LISTE DES RANKS
		jobs.clear();
		jobs.addAll(main.jobs);
		// PERMET DE PLACER LE VERRE
		Clear(inv);
		// ITEM QUI PERMET DE CREE UN NOUVEAU RANK
		inv.setItem(0, new ItemBuilder(Material.DIAMOND_PICKAXE).setName("§2Crée").setLore("§r§7§oCrée un nouveau métier")
				.toItemStack());
		// ITEM QUI PERMET DE VOIR LES RANKS
		inv.setItem(4, new ItemBuilder(Material.CHEST).setName("§bListe des métiers")
				.setLore("§r§7§oModifié/Voir les métiers").toItemStack());
		// ITEM QUI PERMET DE FERMER LE MENU
		inv.setItem(8,
				new ItemBuilder(Material.BARRIER).setName("§cFermer").setLore("§r§7§oFermer le menu").toItemStack());


	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
		
		
		
		// ON RECUPERE LE NOM DE L'ITEM
		switch (current.getItemMeta().getDisplayName()) {
		
		
		
		// ON CREE UN GRADE
		
		case "§2Crée":
			player.closeInventory();
			player.sendMessage(sysmsg.jobnotgoodinformations);
			main.chatlistener.put(player, "create job"); // AJOUTE LE JOUEUR DANS LA LISTE DES ECOUTES
			break;
			
		// ON AFFICHE TOUT LES GRADES 
		case "§cSuivant":	
		case "§bListe des métiers":
			Clear(inv); // ON MET DES VITRE PARTOUT
			
			int i = 0;
			while (i != 8) {
				if (jobs.isEmpty()) return;// SI RANK ET VITE ON ANNULE  
				
				job jb = jobs.get(0); // SINON ON RECUPERE LE REND
				inv.setItem(i, new ItemBuilder(jb.getIt()).setName(jb.getDisplayname()).setLore(jb.getSimplename()).toItemStack()); // ON L'AFFICHE
				jobs.remove(0); // ET ON L'ENLEVE DE LA LISTE
				i++;
				if (i == 8) { // SI I = 8 ALORS ON CREE LA PAGE SUIVANTE
					inv.setItem(8, new ItemBuilder(Material.ARROW).setName("§cSuivant").toItemStack());
					i = 0;
					break;
				}
			}
			break;
			
		// ON FERME L'INVENTARIE
		case "§cFermer":
			main.mn.open(player, moderationMenu.class);
			break;
			
		// ON ACTIVE LE CHANGEMENT DE NOM
		case "§6Changer le nom":
			main.chatlistener.put(player, "change job name,"+inv.getItem(0).getItemMeta().getLore().get(0)); // ON MET LE JOUEUR DANS LE CHANGE RANK NAME ET ON AJOUTE LE NOM DU GRADE
			player.closeInventory();
			player.sendMessage(main.sname + "§rPour continuer veuillez indiquer le nouveau nom");
			break;	
			
		// ON ACTIVE LE CHANGEMENT DE PERMISSION
		case "§cChanger la permission":
			main.chatlistener.put(player, "change job power,"+inv.getItem(0).getItemMeta().getLore().get(0)); // ON MET LE JOUEUR DANS LE CHANGE RANK POWER ET ON AJOUTE LE NOM DU GRADE
			player.closeInventory();
			player.sendMessage(main.sname + "§rPour continuer veuillez indiquer la nouvelle permission");
			break;	

		// ON ACTIVE LE CHANGEMENT DE PERMISSION
		case "§2Changer le salaire":
			main.chatlistener.put(player, "change job salaire,"+inv.getItem(0).getItemMeta().getLore().get(0)); // ON MET LE JOUEUR DANS LE CHANGE RANK POWER ET ON AJOUTE LE NOM DU GRADE
			player.closeInventory();
		    player.sendMessage(main.sname + "§rPour continuer veuillez indiquer le nouveaux salaire");
			break;	
		
		
			
		// SI ON SUPPRIME LE GRADE	
		case "§4Supprimer le métier":
			if (current.getItemMeta().hasLore()) {
				if (jm.jobExist(inv.getItem(0).getItemMeta().getLore().get(0))) {
					if(main.jobs.size() <= 1) {
						player.sendMessage(main.sname + "§cVous ne pouvez pas supprimer le métier par défaut !");
						return;
					}
					job jb = jm.getJobByName(inv.getItem(0).getItemMeta().getLore().get(0));
					main.jobs.remove(jb);
					player.closeInventory();
					player.sendMessage(main.sname + "§cLe métier §r" +jb.getDisplayname() + "§c a été supprimé.");
				}
			}
			break;

		default:
			break;
		}

			// ON MET EN PLACE LES OPTIONS QUAND UN UTILISATEUR APPUIE SUR UN GRADE DANS LA LISTES
			if (current.getItemMeta().hasLore()) {
				if (jm.jobExist(current.getItemMeta().getLore().get(0))) {
					
					
					job jb = jm.getJobByName(current.getItemMeta().getLore().get(0));
					if (inv.getItem(2).getItemMeta().getDisplayName().equals("§6Changer le nom")) {

						if (player.getItemInHand().getType().equals(Material.AIR))
							return;
						jb.setIt(new ItemBuilder(player.getItemInHand().getType()).toItemStack());
						inv.setItem(0, new ItemBuilder(jb.getIt()).setName(jb.getDisplayname())
								.setLore(jb.getSimplename()).toItemStack());
					}
					Clear(inv);

					// ITEM QUI PERMET DE CHANGER LE BLOQUE DU RANG
					inv.setItem(0, new ItemBuilder(jb.getIt()).setName(jb.getDisplayname()).setLore(jb.getSimplename())
							.toItemStack());
					// ITEM QUI PERMET DE CHANGER LE NOM AFFICHABLE DU RANG
					inv.setItem(2, new ItemBuilder(Material.BOOK).setName("§6Changer le nom").setLore("§r§7§oChanger le nom du métier").toItemStack());
					// ITEM QUI PERMET DE CHANGER LA PERMISSION DU RANG
					inv.setItem(4, new ItemBuilder(Material.REDSTONE_COMPARATOR).setName("§cChanger la permission").setLore("§r§7§oLa permission: "+String.valueOf(jb.getPerm())).toItemStack());
					// ITEM QUI PERMET DE CHANGER Le SALAIRE DU RANG
					inv.setItem(6, new ItemBuilder(Material.GOLD_INGOT).setName("§2Changer le salaire").setLore("§r§7§oLe salire: "+String.valueOf(jb.getSalaire())).toItemStack());
					// ITEM QUU PERMET DE SUPPRIMER LE RANG
					inv.setItem(8, new ItemBuilder(Material.BARRIER).setName("§4Supprimer le métier").setLore("§r§7§oSupprime le métier").toItemStack());
				}
			}

		

	
		

	}

	@Override
	public int getSize() {
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
