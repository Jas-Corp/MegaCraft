package fr.jas14.megacraft.players.money;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.jas14.megacraft.Main;
import fr.jas14.megacraft.others.ItemBuilder;
import fr.jas14.megacraft.others.messagesSystem;
import fr.jas14.megacraft.others.inventory.CustomInventory;
import fr.jas14.megacraft.players.RPlayer;

public class ATM implements CustomInventory {
	
	
	
	private messagesSystem sysmsg = new messagesSystem();
	@Override
	public String name() {
		// TODO Auto-generated method stub
		return sysmsg.banque;
	}

	@Override
	public void contents(Player player, Inventory inv) {
		
		RPlayer rp = Main.getInstance().playertoRplayers.get(player); // ON RECEPURE LE JOUEUR
		
		Clear(inv);
		
		// ON MET LES ITEMS RETIRER
		inv.setItem(9, new ItemBuilder(Material.getMaterial(7157)).setName("§fRetirer §45€").toItemStack());
		inv.setItem(10, new ItemBuilder(Material.getMaterial(4255)).setName("§fRetirer §410€").toItemStack());
		inv.setItem(11, new ItemBuilder(Material.getMaterial(4262)).setName("§fRetirer §4100€").toItemStack());
		inv.setItem(12, new ItemBuilder(Material.getMaterial(4657)).setName("§fRetirer §4500€").toItemStack());
		
		// COMPTE EN BANQUE
		@SuppressWarnings("deprecation")
		ItemStack skull = new ItemStack(397, 1, (short) 3);
		inv.setItem(13, new ItemBuilder(skull).setName("§2§lCompte").setLore("§r§a"+String.valueOf(rp.getMoney()) + "€").setSkullOwner(player.getDisplayName()).toItemStack());
		inv.setItem(22, new ItemBuilder(Material.HOPPER).setName("§eTout déposer").toItemStack());
		
		// ON MET LES ITEMS DEPOSER
		inv.setItem(14, new ItemBuilder(Material.getMaterial(7157)).setName("§fDeposer §25€").toItemStack());
		inv.setItem(15, new ItemBuilder(Material.getMaterial(4255)).setName("§fDeposer §210€").toItemStack());
		inv.setItem(16, new ItemBuilder(Material.getMaterial(4262)).setName("§fDeposer §2100€").toItemStack());
		inv.setItem(17, new ItemBuilder(Material.getMaterial(4657)).setName("§fDeposer §2500€").toItemStack());
		
	}

	@Override
	public void onClick(Player player, Inventory inv, ItemStack current, int slot) {

		RPlayer rp = Main.getInstance().playertoRplayers.get(player); // O? RECUPERE LE JOUEUR
		
		switch (current.getItemMeta().getDisplayName()) { // ON RECUPERE LE NOM DE CHAQUE ITEM
		case "§fRetirer §45€":
			if (rp.removeMoney(5)) { // ON LUI ENELVE 10
				
			player.sendMessage(sysmsg.maderetrait); 
			player.getInventory().addItem(new ItemBuilder(Material.getMaterial(7157)).toItemStack());
				
			}else {
				player.sendMessage(sysmsg.nomoney);
			}
			
			break;
		case "§fRetirer §410€":
			if (rp.removeMoney(10)) { // ON LUI ENELVE 10
				
			player.sendMessage(sysmsg.maderetrait); 
			player.getInventory().addItem(new ItemBuilder(Material.getMaterial(4255)).toItemStack());
				
			}else {
				player.sendMessage(sysmsg.nomoney);
			}
			
			break;
		case "§fRetirer §4100€":
			if (rp.removeMoney(100)) { // ON LUI ENELVE 100
				
			player.sendMessage(sysmsg.maderetrait); 
			player.getInventory().addItem(new ItemBuilder(Material.getMaterial(4262)).toItemStack());
				
			}else {
				player.sendMessage(sysmsg.nomoney);
			}
			
			break;
		case "§fRetirer §4500€":
			if (rp.removeMoney(500)) { // ON LUI ENELVE 500
				
			player.sendMessage(sysmsg.maderetrait); 
			player.getInventory().addItem(new ItemBuilder(Material.getMaterial(4657)).toItemStack());
			
				
			}else {
				player.sendMessage(sysmsg.nomoney);
			}
			
			break;
		case "§fDeposer §25€":
			if (getPhysicalMoney(player) >= 5 && player.getInventory().contains(Material.getMaterial(7157))) { // ON VERIFIE SI SUR LUI IL A UNE VALEUR DE 10 OU PLUS ET SI IL A LE BILLET CORRESPONDENT
				
			player.sendMessage(sysmsg.madedepot); 
			removePhysicalIngot(player, "§25€"); // ON RETIRE LE BILLET ET LUI AJOUTE L'ARGENT
			rp.addMoney(5);
			
				
			}else {
				player.sendMessage(sysmsg.nomoney); // ON LUI DIT QUIL PEUX PAS
			}
			
			break;
		case "§fDeposer §210€":
			if (getPhysicalMoney(player) >= 10 && player.getInventory().contains(Material.getMaterial(4255))) { // ON VERIFIE SI SUR LUI IL A UNE VALEUR DE 10 OU PLUS ET SI IL A LE BILLET CORRESPONDENT
				
			player.sendMessage(sysmsg.madedepot); 
			removePhysicalIngot(player, "§210€"); // ON RETIRE LE BILLET ET LUI AJOUTE L'ARGENT
			rp.addMoney(10);
			
				
			}else {
				player.sendMessage(sysmsg.nomoney); // ON LUI DIT QUIL PEUX PAS
			}
			
			break;
		case "§fDeposer §2100€":
			if (getPhysicalMoney(player) >= 100 && player.getInventory().contains(Material.getMaterial(4262))) {
			player.sendMessage(sysmsg.madedepot);
			removePhysicalIngot(player, "§2100€");
			rp.addMoney(100);
				
			}else {
				player.sendMessage(sysmsg.nomoney);
			}
			
			break;
		case "§fDeposer §2500€":
			if (getPhysicalMoney(player) >= 500 && player.getInventory().contains(Material.getMaterial(4657))) {
			player.sendMessage(sysmsg.madedepot);
			removePhysicalIngot(player, "§2500€");
			rp.addMoney(500);
			
				
			}else {
				player.sendMessage(sysmsg.nomoney);
			}
			
			break;
			
		case "§eTout déposer":
			if (getPhysicalMoney(player) != 0) { // ON VERIFIE QU'IL A QUELQUE CHOSES
				
			rp.addMoney(getPhysicalMoney(player)); // ALORS ON LUI AJOUTE TOUT CE QU'IL A ET LUI CLEAR LES BILLET
			player.getInventory().remove(Material.getMaterial(7157));
			player.getInventory().remove(Material.getMaterial(4255));
			player.getInventory().remove(Material.getMaterial(4262));
			player.getInventory().remove(Material.getMaterial(4657));
				
			}else {
				player.sendMessage(sysmsg.nomoney);
			}
			
			break;

		default:
			break;
		}
		@SuppressWarnings("deprecation")
		ItemStack skull = new ItemStack(397, 1, (short) 3);
		inv.setItem(13, new ItemBuilder(skull).setName("§2§lCompte").setLore("§r§a"+String.valueOf(rp.getMoney()) + "€").setSkullOwner(player.getDisplayName()).toItemStack());
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 27;
	}
	
	
	// REMPALCE TOUT L'INVENTAIRE PARD DES PANNEAUX.
	private void Clear(Inventory inv) {
		for (int i = 0; i < 27; i++) {
			inv.setItem(i, new ItemBuilder(Material.AIR).toItemStack());
			inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).setName("-").setData((short) 13).toItemStack());
		}
	}
	
	
	// RECUPERER LE NOMBRE DE BILLET DU JOUEUR
	public int getPhysicalMoney(Player p) {
		int currency = 0;
		for (ItemStack is : p.getInventory().getContents()) {
			if (is != null) {
					//Zd
					switch (is.getTypeId()) {
					case 7157:
						currency = currency + is.getAmount() * 5;
						
						break;
					case 4255:
						currency = currency + is.getAmount() * 10;
						
						break;
					case 4262:
					

						currency = currency + is.getAmount() * 100;
				
						break;
					case 4657:
						currency = currency + is.getAmount() * 500;
				
						break;
					default:
						break;
					}

				

			}

		}
		return currency;

	}
	
	// 
	
	public void removePhysicalIngot(Player p, String name) {
		if (name.equals("§25€")) {
			p.getInventory().removeItem(new ItemBuilder(Material.getMaterial(7157), 1).toItemStack());
			

		}
		if (name.equals("§210€")) {
			p.getInventory().removeItem(new ItemBuilder(Material.getMaterial(4255), 1).toItemStack());
			

		}
		if (name.equals("§2100€")) {
			p.getInventory().removeItem(new ItemBuilder(Material.getMaterial(4262), 1).toItemStack());

		}
		if (name.equals("§2500€")) {
			p.getInventory().removeItem(new ItemBuilder(Material.getMaterial(4657), 1).toItemStack());

		}
	
	
	
	}
	


}
