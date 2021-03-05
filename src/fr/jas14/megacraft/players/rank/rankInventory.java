package fr.jas14.megacraft.players.rank;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.jas14.megacraft.Main;
import fr.jas14.megacraft.others.ItemBuilder;
import fr.jas14.megacraft.others.inventory.CustomInventory;

public class rankInventory implements CustomInventory {

	private Main main = Main.getInstance(); // ON RECUPERE LE MAIN
	private List<rank> ranks = new ArrayList<>(); // LISTE DES RANKS A AFFICHER
	private rankManager rm = new rankManager(); // ON RECUPERE LE RANK MANAGER

	
	
	@Override
	public String name() {
		return main.sname + "§4Gestion des grades";
	}

	@Override
	public void contents(Player player, Inventory inv) {

		// ON PREND LA LISTE DES RANKS A AFFICHER ET ON Y MET LA LISTE DES RANKS
		ranks.clear();
		ranks.addAll(main.ranks);
		// PERMET DE PLACER LE VERRE
		Clear(inv);
		// ITEM QUI PERMET DE CREE UN NOUVEAU RANK
		inv.setItem(0, new ItemBuilder(Material.DIAMOND_PICKAXE).setName("§2Crée").setLore("§r§7§oCrée un nouveau grade")
				.toItemStack());
		// ITEM QUI PERMET DE VOIR LES RANKS
		inv.setItem(4, new ItemBuilder(Material.CHEST).setName("§bListe des grades")
				.setLore("§r§7§oModifié/Voir les ranks").toItemStack());
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
			player.sendMessage(main.sname
					+ "§fPour créer votre rang veuillez prendre en main l'item de votre choix pour le représenter et indiquer les informations suivantes séparer par une virgule : §cLe nom simple, la permission et le nom d'affichage §7§o par exemple \"admin,5,&4[Administrateur]\"");
			main.chatlistener.put(player, "create rank"); // AJOUTE LE JOUEUR DANS LA LISTE DES ECOUTES
			break;
			
		// ON AFFICHE TOUT LES GRADES 
		case "§cSuivant":	
		case "§bListe des grades":
			Clear(inv); // ON MET DES VITRE PARTOUT

			int i = 0;
			while (i != 8) {
				if (ranks.isEmpty()) return;// SI RANK ET VITE ON ANNULE  
				
				rank rk = ranks.get(0); // SINON ON RECUPERE LE REND
				inv.setItem(i, new ItemBuilder(rk.getIt()).setName(rk.getDisplayname()).setLore(rk.getSimplename()).toItemStack()); // ON L'AFFICHE
				ranks.remove(0); // ET ON L'ENLEVE DE LA LISTE
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
			main.chatlistener.put(player, "change rank name,"+inv.getItem(0).getItemMeta().getLore().get(0)); // ON MET LE JOUEUR DANS LE CHANGE RANK NAME ET ON AJOUTE LE NOM DU GRADE
			player.closeInventory();
			player.sendMessage(main.sname + "§rPour continuer veuillez indiquer le nouveau nom");
			break;	
			
		// ON ACTIVE LE CHANGEMENT DE PERMISSION
		case "§cChanger la permission":
			main.chatlistener.put(player, "change rank power,"+inv.getItem(0).getItemMeta().getLore().get(0)); // ON MET LE JOUEUR DANS LE CHANGE RANK POWER ET ON AJOUTE LE NOM DU GRADE
			player.closeInventory();
			player.sendMessage(main.sname + "§rPour continuer veuillez indiquer la nouvelle permission");
			break;	
		
			
		// SI ON SUPPRIME LE GRADE	
		case "§4Supprimer le grade":
			if (current.getItemMeta().hasLore()) {
				if (rm.rankExist(inv.getItem(0).getItemMeta().getLore().get(0))) {
					rank rk = rm.getRankByName(inv.getItem(0).getItemMeta().getLore().get(0));
					main.ranks.remove(rk);
					player.closeInventory();
					player.sendMessage(main.sname + "§cLe grade §r" +rk.getDisplayname() + "§c a été supprimé.");
				}
			}
			break;

		default:
			break;
		}

			// ON MET EN PLACE LES OPTIONS QUAND UN UTILISATEUR APPUIE SUR UN GRADE DANS LA LISTES
			if (current.getItemMeta().hasLore()) {
				if (rm.rankExist(current.getItemMeta().getLore().get(0))) {

					rank rk = rm.getRankByName(current.getItemMeta().getLore().get(0));
					if (inv.getItem(2).getItemMeta().getDisplayName().equals("§6Changer le nom")) {

						if (player.getItemInHand().getType().equals(Material.AIR))
							return;
						rk.setIt(new ItemBuilder(player.getItemInHand().getType()).toItemStack());
						inv.setItem(0, new ItemBuilder(rk.getIt()).setName(rk.getDisplayname())
								.setLore(rk.getSimplename()).toItemStack());
					}
					Clear(inv);

					// ITEM QUI PERMET DE CHANGER LE BLOQUE DU RANG
					inv.setItem(0, new ItemBuilder(rk.getIt()).setName(rk.getDisplayname()).setLore(rk.getSimplename())
							.toItemStack());
					// ITEM QUI PERMET DE CHANGER LE NOM AFFICHABLE DU RANG
					inv.setItem(2, new ItemBuilder(Material.BOOK).setName("§6Changer le nom").setLore("§r§7§oChanger le nom du grade").toItemStack());
					// ITEM QUI PERMET DE CHANGER LA PERMISSION DU RANG
					inv.setItem(4, new ItemBuilder(Material.REDSTONE_COMPARATOR).setName("§cChanger la permission").setLore("§r§7§oLa permission: "+String.valueOf(rk.getPower())).toItemStack());
					// ITEM QUU PERMET DE SUPPRIMER LE RANG
					inv.setItem(8, new ItemBuilder(Material.BARRIER).setName("§4Supprimer le grade").setLore("§r§7§oSupprime le grade").toItemStack());
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
