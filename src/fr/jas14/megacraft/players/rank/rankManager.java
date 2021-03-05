package fr.jas14.megacraft.players.rank;

import org.bukkit.inventory.ItemStack;

import fr.jas14.megacraft.Main;

public class rankManager {
	
	private Main main = Main.getInstance();
	
	
	
	public void createRank(int id, String displayname, String simplename, int power, ItemStack it ) {
				new rank(id, displayname,simplename, power, it);
				// AJOUTER L'AJOUT A LA BASE DE DONNER
	}
	
	
	public rank getRankByName(String name) {
		for (int i = 0; i < main.ranks.size(); i++) {
			if(main.ranks.get(i).getSimplename().equals(name)) {
				return main.ranks.get(i);
			}
		}
		return null;
	}
	
	
	public boolean rankExist(String name) {
		if(getRankByName(name) != null) return true;
		return false;

	}

}
