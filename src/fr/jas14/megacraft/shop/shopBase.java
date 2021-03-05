package fr.jas14.megacraft.shop;

import java.util.List;

import org.bukkit.inventory.ItemStack;

import fr.jas14.megacraft.Main;

public class shopBase {
	private String name;
	private List<ItemStack> items;
	private List<Integer> prices;
	private List<String> perms;
	private boolean isselling;
	public shopBase(String name, List<ItemStack> items, List<Integer> prices, List<String> perms, boolean isselling) {
		
		this.name = name;
		this.prices = prices;
		this.perms = perms;
		this.items = items;	
		this.isselling = isselling;
		Main.getInstance().shops.add(this);
	}
	
	
	
	public String getName() {
		return name;
	}
	
	public void addItems(int price, ItemStack it) {
		items.add(it);
		prices.add(price);
		

	}
	
	public void removeItems(ItemStack it) {
		prices.get(items.lastIndexOf(it));
		items.remove(it);

	}
	
	public List<String> getPerms() {
		return perms;
	}
	
	public List<ItemStack> getItems() {
		return items;
	}
	
	public List<Integer> getPrices() {
		return prices;
	}
	public boolean isIsselling() {
		return isselling;
	}

}
