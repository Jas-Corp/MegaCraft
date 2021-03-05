package fr.jas14.megacraft.players.jobs;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.jas14.megacraft.Main;
import fr.jas14.megacraft.others.ItemBuilder;
import fr.jas14.megacraft.others.inventory.CustomInventory;
import fr.jas14.megacraft.players.RPlayer;

public class policemenu implements CustomInventory {
	
	private Main main = Main.getInstance();
	

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "§1Commissariat";
	}

	@Override
	public void contents(Player player, Inventory inv) {
		Clear(inv);
		RPlayer rp = Main.getInstance().playertoRplayers.get(player);
		if(main.playerforPoliceWhitlists.containsKey(player.getDisplayName())) {
			inv.setItem(4, new ItemBuilder(Material.BARRIER).setName("§cVotre demande est en attente.").toItemStack());
			return;
		}
		if (rp.getJob().getSimplename().equals("commissaire")) {
			inv.setItem(4, new ItemBuilder(Material.BARRIER).setName("§cVous avez le plus au grade dans la police !").toItemStack());		
			return;
		}
		
		if (!rp.getJob().isPolice()) {
			job jb = main.policejobs.get(0);
			inv.setItem(4, new ItemBuilder(jb.getIt()).setName(jb.getDisplayname()).setLore(jb.getSimplename()).toItemStack());
		}else {
			job jb = main.policejobs.get(rp.getJob().getId()+1);
			inv.setItem(4, new ItemBuilder(jb.getIt()).setName(jb.getDisplayname()).setLore(jb.getSimplename()).toItemStack());
		}

	}

	@Override
	public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
		
		if (main.playerforPoliceWhitlists.containsKey(player.getDisplayName())) return;
		if (current.getType().equals(Material.BARRIER)) {
			return;
		}
		
		RPlayer rp = Main.getInstance().playertoRplayers.get(player);
		job jb = main.policejobs.get(0);
		
		if (rp.getJob().isPolice()) {
			if (!((rp.getPlayhour()-rp.getPolicetime()) >= 5)) {
				player.sendMessage(main.sysmsg.notPlayTimeRequierd);
				return;
			}
			
		}else {
			if (!(rp.getPlayhour() >= 5)) {
				player.sendMessage(main.sysmsg.notPlayTimeRequierd);
				return;
			}
		}
		
		if (rp.getJob().isPolice()) jb = main.policejobs.get(rp.getJob().getId()+1);
		

		Clear(inv);

		// ITEM QUI PERMET DE CHANGER LE BLOQUE DU RANG
		inv.setItem(4, new ItemBuilder(jb.getIt()).setName(jb.getDisplayname()).setLore(jb.getSimplename())
				.toItemStack());
		// ITEM QUI PERMET DE CHANGER LE NOM AFFICHABLE DU RANG
		inv.setItem(2, new ItemBuilder(Material.WOOL).setData((short) 13).setName("§2Validé").toItemStack());
		// ITEM QUI PERMET DE CHANGER LA PERMISSION DU RANG
		inv.setItem(6, new ItemBuilder(Material.WOOL).setData((short) 14).setName("§4Annulé").toItemStack());
		
		if (current.getItemMeta().getDisplayName().equals("§2Validé")) {
			if(rp.getJob().isPolice()) {
				main.playerforPoliceWhitlists.put(player.getDisplayName(), rp.getJob().getId()+1);	
				
			}else {
				main.playerforPoliceWhitlists.put(player.getDisplayName(), 0);
			}
			
			player.closeInventory();
			player.sendMessage(main.sysmsg.sendedCandidature);
			
		}
		
		if (current.getItemMeta().getDisplayName().equals("§4Annulé")) {
			player.closeInventory();
			
		}
		

	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 9;
	}
	
	private void Clear(Inventory inv) {
		for (int i = 0; i < 9; i++) {
			inv.setItem(i, new ItemBuilder(Material.AIR).toItemStack());
			inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).setName("-").setData((short) 9).toItemStack());
		}
	}

}
