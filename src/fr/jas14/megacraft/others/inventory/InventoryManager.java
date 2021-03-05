package fr.jas14.megacraft.others.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.jas14.megacraft.Main;
import fr.jas14.megacraft.npc.adminNpcMenu;
import fr.jas14.megacraft.players.jobs.jobInventory;
import fr.jas14.megacraft.players.jobs.poleEmploie;
import fr.jas14.megacraft.players.jobs.policemenu;
import fr.jas14.megacraft.players.money.ATM;
import fr.jas14.megacraft.players.rank.moderationMenu;
import fr.jas14.megacraft.players.rank.rankInventory;
import fr.jas14.megacraft.shop.shopMenu;




public class InventoryManager implements Listener {
	public Main main;
	@SuppressWarnings("unused")
	private static InventoryManager inv;

	public InventoryManager(Main main) {
		this.main = main;
	}

	
	@EventHandler
	public void onClick(InventoryClickEvent event) {

		Player player = (Player) event.getWhoClicked();
		Inventory inv = event.getInventory();
		ItemStack current = event.getCurrentItem();

		if (event.getCurrentItem() == null)
			return;
		if(current.getType().equals(Material.AIR)) return;

		main.registeredMenus.values().stream()
				.filter(menu -> inv.getName().equalsIgnoreCase(menu.name()))
				.forEach(menu -> {
					
						try {
							menu.onClick(player, inv, current, event.getSlot());
							event.setCancelled(true);
						} catch (NullPointerException e) {
							
						}
					
						
						

					
					;
				});
		
	}

	public void addMenu(CustomInventory m) {
		main.registeredMenus.put(m.getClass(), m);
		
	}

	public void open(Player player, Class<? extends CustomInventory> gClass) {
		

			if (!main.registeredMenus.containsKey(gClass))return;
			CustomInventory menu = main.registeredMenus.get(gClass);
	
			Inventory inv2 = Bukkit.createInventory(null, menu.getSize(), menu.name());
			menu.contents(player, inv2);
			player.openInventory(inv2);
		
		

	}

	// ------------------------------------------POUR ENREGISTER UN
	// MENU---------------------------------------------------------

	@EventHandler
	private void onEnbale(PluginEnableEvent e) {

	addMenu(new rankInventory());
	addMenu(new ATM());
	addMenu(new jobInventory());
	addMenu(new adminNpcMenu());
	addMenu(new poleEmploie());
	addMenu(new policemenu());
	addMenu(new moderationMenu());
	addMenu(new shopMenu());
	}
	

	

}
