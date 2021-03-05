package fr.jas14.megacraft.players.jobs;

import org.bukkit.inventory.ItemStack;

import fr.jas14.megacraft.Main;

public class job {

	
	private int id, perm, salaire;
	private String displayname, simplename;
	private ItemStack it;
	private boolean isPolice;
	
	public job(int id, String displayname, String simplename, int perm, ItemStack it, int salaire, boolean isPolice) {
		if (isPolice) {
			
			Main.getInstance().policejobs.add(this);
		}else {
			Main.getInstance().jobs.add(this);
		}
		
		
		this.id=id;
		this.displayname = displayname;
		this.perm = perm;
		this.it = it;
		this.simplename = simplename;
		this.salaire = salaire;
		this.isPolice = isPolice;
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
	
	public int getPerm() {
		return perm;
	}
	public void setPerm(int power) {
		this.perm = power;
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
	
	public int getSalaire() {
		return salaire;
	}
	public void setSalaire(int salaire) {
		this.salaire = salaire;
	}
	public boolean isPolice() {
		return isPolice;
	}

	
}
