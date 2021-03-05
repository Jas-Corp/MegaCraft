package fr.jas14.megacraft.terrains;

import java.util.List;



import fr.jas14.megacraft.Main;

public class TResources {
	
	private String prefix;
	private List<String> perms;
	private int respawntime;
	
	public TResources(String prefix, List<String> perms, int respawntime) {
		this.prefix = prefix;
		this.perms = perms;
		this.respawntime = respawntime;
		Main.getInstance().tregions.add(this);
		
	}
	

	public List<String> getPerms() {
		return perms;
	}
	public void setPerms(List<String> perms) {
		this.perms = perms;
	}
	public void addPerms(String perm) {
		perms.add(perm);
	}
	public boolean removePerms(String perm) {
		if (perms.contains(perm)) {
			perms.remove(perm);
			return true;
		}
		return false;
		
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public int getRespawntime() {
		return respawntime;
	}
	public void setRespawntime(int respawntime) {
		this.respawntime = respawntime;
	}

}
