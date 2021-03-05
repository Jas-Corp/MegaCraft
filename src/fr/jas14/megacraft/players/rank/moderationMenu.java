package fr.jas14.megacraft.players.rank;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.jas14.megacraft.Main;
import fr.jas14.megacraft.npc.adminNpcMenu;
import fr.jas14.megacraft.others.ItemBuilder;
import fr.jas14.megacraft.others.inventory.CustomInventory;
import fr.jas14.megacraft.players.jobs.jobInventory;

public class moderationMenu implements CustomInventory {

	private Main main = Main.getInstance();
	@Override
	public String name() {
		// TODO Auto-generated method stub
		return main.sname + "§bModération";
	}

	@Override
	public void contents(Player player, Inventory inv) {
		/*	addMenu(new rankInventory());
	addMenu(new ATM());
	addMenu(new jobInventory());
	addMenu(new adminNpcMenu());
	addMenu(new poleEmploie());
	addMenu(new policemenu());*/
		inv.setItem(0, new ItemBuilder(Material.DIAMOND).setName("§4Gestion des grades").toItemStack());
		inv.setItem(1, new ItemBuilder(Material.APPLE).setName("§2Gestion des métiers").toItemStack());
		inv.setItem(2, new ItemBuilder(Material.SKULL_ITEM).setName("§eMenu des pnjs").toItemStack());

	}

	@Override
	public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
		if(!(current.hasItemMeta())) return;
		switch (current.getItemMeta().getDisplayName()) {
		case "§4Gestion des grades":
			main.mn.open(player, rankInventory.class);
			break;
		case "§2Gestion des métiers":
			main.mn.open(player, jobInventory.class);
			break;
		case "§eMenu des pnjs":
			main.mn.open(player, adminNpcMenu.class);
			break;

		default:
			break;
		}

	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 9;
	}

}
