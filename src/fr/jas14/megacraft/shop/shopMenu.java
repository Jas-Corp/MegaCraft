package fr.jas14.megacraft.shop;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.jas14.megacraft.Main;
import fr.jas14.megacraft.others.ItemBuilder;
import fr.jas14.megacraft.others.inventory.CustomInventory;
import fr.jas14.megacraft.players.RPlayer;

public class shopMenu implements CustomInventory {

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return Main.getInstance().sysmsg.servername + "§2Shop";
	}

	@Override
	public void contents(Player player, Inventory inv) {
		shopBase shop = Main.getInstance().playeropenShop.get(player);
		Clear(inv);
		for (int i = 0; i < shop.getItems().size(); i++) {
			ItemStack it = shop.getItems().get(i);
			if (shop.isIsselling()) {
				inv.setItem(i, new ItemBuilder(it.getType(), it.getAmount()).setName(it.getItemMeta().getDisplayName()).setLore("§r§fPrix rachat :§2" + String.valueOf(shop.getPrices().get(i)) + "€").setData(it.getDurability()).toItemStack());

			}else {
				inv.setItem(i, new ItemBuilder(it.getType(), it.getAmount()).setName(it.getItemMeta().getDisplayName()).setLore("§r§fPrix :§2" + String.valueOf(shop.getPrices().get(i)) + "€").setData(it.getDurability()).toItemStack());

			}
			
		}
		
	}

	@Override
	public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
		shopBase shop = Main.getInstance().playeropenShop.get(player);
	
		RPlayer rp = Main.getInstance().playertoRplayers.get(player);
		if (shop.isIsselling()) {
			if (current.getItemMeta().hasDisplayName()) {
				int add =Integer.valueOf(current.getItemMeta().getLore().get(0).replace("§r§fPrix rachat :§2", "").replace("€", ""));
				if (getifhavewithname(player, current.getItemMeta().getDisplayName()) >= current.getAmount()) {
					player.getInventory().removeItem(new ItemBuilder(current.getType(), current.getAmount()).toItemStack());
					rp.addMoney(add);
					player.sendMessage(Main.getInstance().sname + "§2vous avez vendu votre item !");
					return;
				}
			}else {
				if (getifhave(player, current) >= current.getAmount()) {
					int add =Integer.valueOf(current.getItemMeta().getLore().get(0).replace("§r§fPrix rachat :§2", "").replace("€", ""));
					player.getInventory().removeItem(new ItemBuilder(current.getType(), current.getAmount()).toItemStack());
					rp.addMoney(add);
					player.sendMessage(Main.getInstance().sname + "§2vous avez vendu votre item !");
					return;
				}
			}
			player.sendMessage(Main.getInstance().sname + "§4vous n'avez pas l'item requis");
			return;
		}
		int price =Integer.valueOf(current.getItemMeta().getLore().get(0).replace("§r§fPrix :§2", "").replace("€", ""));
		if (rp.getMoney() >= price) {
			rp.removeMoney(price);

			player.getInventory().addItem(new ItemBuilder(current.getType(), current.getAmount()).setName(current.getItemMeta().getDisplayName()).setData(current.getDurability()).toItemStack());
			player.sendMessage(Main.getInstance().sysmsg.servername + "§2Vous avez effecuté l'achat");
			
		}else {
			player.sendMessage(Main.getInstance().sysmsg.nomoney);
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
			inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).setName("-").setData((short) 5).toItemStack());
		}
	}
	
	public int getifhavewithname(Player p, String name) {
		int currency = 0;
		for (ItemStack is : p.getInventory().getContents()) {
			if (is != null) {
				if (is.hasItemMeta()) {		
					if (is.getItemMeta().hasDisplayName()) {
						if (is.getItemMeta().getDisplayName().equals(name)) {
							currency = currency + is.getAmount();
						}
					}

				}

			}

		}
		return currency;

	}
	
	public int getifhave(Player p, ItemStack name) {
		int currency = 0;
		for (ItemStack is : p.getInventory().getContents()) {
			if (is != null) {
				System.out.println(currency + "   "+ name.getType() + "    " + is.getType());
				if (name.getType().equals(is.getType()) && !is.getItemMeta().hasDisplayName()) {
					currency = currency + is.getAmount();
				}

			}

		}
		return currency;

	}
	

	


}
