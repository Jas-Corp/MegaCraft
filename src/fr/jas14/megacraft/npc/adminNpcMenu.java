package fr.jas14.megacraft.npc;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.jas14.megacraft.Main;
import fr.jas14.megacraft.others.ItemBuilder;
import fr.jas14.megacraft.others.inventory.CustomInventory;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;

public class adminNpcMenu implements CustomInventory {

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return Main.getInstance().sname + "§eGestion des npc";
	}

	@SuppressWarnings("deprecation")
	@Override
	public void contents(Player player, Inventory inv) {

		ItemStack skull = new ItemStack(397, 1, (short) 3);
		inv.setItem(0, new ItemBuilder(skull).setSkullOwner("test").setName("§bPôle §9Emploi").toItemStack());
		inv.setItem(1, new ItemBuilder(skull).setSkullOwner("bleu").setName("§1Commisaire").toItemStack());
		inv.setItem(2, new ItemBuilder(skull).setSkullOwner("mayor").setName("§eMaire").toItemStack());
	}

	@Override
	public void onClick(Player player, Inventory inv, ItemStack current, int slot) {

		switch (current.getItemMeta().getDisplayName()) {

		case "§bPôle §9Emploi":
			NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "§bPôle §9Emploi");
			player.chat("/npc select " + String.valueOf(npc.getId()));
			player.chat("/npc skin --url https://minesk.in/1664252856");
			player.chat("/npc name");
			npc.spawn(player.getLocation());
			break;

		case "§1Commisaire":
			NPC npc1 = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "§1Commisaire");
			player.chat("/npc select " + String.valueOf(npc1.getId()));
			player.chat("/npc skin --url https://minesk.in/226869330");
			player.chat("/npc name");
			npc1.spawn(player.getLocation());
			break;
		case "§eMaire":
			NPC npc2 = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "§eMaire");
			player.chat("/npc select " + String.valueOf(npc2.getId()));
			player.chat("/npc skin --url https://minesk.in/1605984861");
			player.chat("/npc name");
			npc2.spawn(player.getLocation());
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
