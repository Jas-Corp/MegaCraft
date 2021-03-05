package fr.jas14.megacraft.players.jobs;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import fr.jas14.megacraft.Main;
import fr.jas14.megacraft.others.ItemBuilder;

public class jobManager {
	
	private Main main = Main.getInstance();
	
	
	
	public void createJob(int id, String displayname, String simplename, int perm, ItemStack it, int money ) {
				new job(id, displayname,simplename, perm, it, money, false);
				// AJOUTER L'AJOUT A LA BASE DE DONNER
	}
	
	
	public job getJobByName(String name) {

		for (int i = 0; i < main.jobs.size(); i++) {
			if(main.jobs.get(i).getSimplename().equals(name)) {
				return main.jobs.get(i);
			}
		}
		return null;
	}
	
	
	public boolean jobExist(String name) {
		if(getJobByName(name) != null) return true;
		return false;

	}
	
	public void loadPoliceJobs() {
		
		new job(0, "§8[§7Douanier§8]","douanier", 0, new ItemBuilder(Material.SEEDS).toItemStack(), 500, true);
		new job(1, "§8[§aGardien de la paix§8]","gardien", 0, new ItemBuilder(Material.CLAY).toItemStack(), 1000, true);
		new job(2, "§8[§2Brigadier§8]","brigadier", 0, new ItemBuilder(Material.COAL).setData((short) 1).toItemStack(), 1500, true);
		new job(3, "§8[§bBrigadier Chef§8]","brigadierchef", 0, new ItemBuilder(Material.COAL).toItemStack(), 2000, true);
		new job(4, "§8[§3Major§8]","major", 0, new ItemBuilder(Material.BRICK).toItemStack(), 2500, true);
		new job(5, "§8[§9Lieutenant§8]","lieutenant", 0, new ItemBuilder(Material.IRON_INGOT).toItemStack(), 3000, true);
		new job(6, "§8[§1Capitaine§8]","capitaine", 0, new ItemBuilder(Material.GOLD_INGOT).toItemStack(), 3500, true);
		new job(7, "§8[§eCommandant§8]","commandant", 0, new ItemBuilder(Material.EMERALD).toItemStack(), 4000, true);
		new job(8, "§8[§6Commissaire§8]","commissaire", 0, new ItemBuilder(Material.DIAMOND).toItemStack(), 4500, true);

		
		
	}

}
