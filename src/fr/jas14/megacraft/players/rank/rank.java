package fr.jas14.megacraft.players.rank;

import org.bukkit.inventory.ItemStack;

import fr.jas14.megacraft.Main;

public class rank {

	
	private int id, power;
	private String displayname, simplename;
	private ItemStack it;
	
	public rank(int id, String displayname, String simplename, int power, ItemStack it) {
		
		Main.getInstance().ranks.add(this);
		
		this.id=id;
		this.displayname = displayname;
		this.power = power;
		this.it = it;
		this.simplename = simplename;
	}
	
	public String getDisplayname() {
		return displayname;
	}
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	
	public ItemStack getIt() {
		return it;
	}
	public void setIt(ItemStack it) {
		this.it = it;
	}
	public String getSimplename() {
		return simplename;
	}
	public void setSimplename(String simplename) {
		this.simplename = simplename;
	}
	
	
	
}
