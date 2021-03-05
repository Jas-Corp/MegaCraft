package fr.jas14.megacraft.database;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import fr.jas14.megacraft.Main;
import fr.jas14.megacraft.others.ItemBuilder;
import fr.jas14.megacraft.players.RPlayer;
import fr.jas14.megacraft.players.jobs.job;
import fr.jas14.megacraft.players.rank.rank;
import fr.jas14.megacraft.shop.shopBase;
import fr.jas14.megacraft.terrains.TResources;
import scala.collection.mutable.StringBuilder;

public class SQLGetter {

	private Main main;

	public SQLGetter(Main main) {
		this.main = main;
	}

	public void createTable() {

		PreparedStatement ps;
		try {

			ps = main.database.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS players "
					+ "(id INT not null AUTO_INCREMENT, pseudo VARCHAR(100),name VARCHAR(100),firstname VARCHAR(100),"
					+ "rank VARCHAR(100),job VARCHAR(100),company VARCHAR(100),"
					+ "money INT(100),playhour INT(100),policetime INT(100)," + "PRIMARY KEY (id))");
			ps.executeUpdate();
			ps.close();

			ps = main.database.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS jobs "
					+ "(id INT not null AUTO_INCREMENT, ids INT(100),displayname VARCHAR(100),simplename VARCHAR(100),"

					+ "perm INT(100),salaire INT(100),isPolice VARCHAR(100),item VARCHAR(1000)," + "PRIMARY KEY (id))");
			ps.executeUpdate();
			ps.close();

			ps = main.database.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS ranks "
					+ "(id INT not null AUTO_INCREMENT, ids INT(100),displayname VARCHAR(100),simplename VARCHAR(100),"

					+ "power INT(100),item VARCHAR(1000)," + "PRIMARY KEY (id))");
			ps.executeUpdate();
			ps.close();

			// String prefix, List<String> perms, int respawntime
			ps = main.database.getConnection()
					.prepareStatement("CREATE TABLE IF NOT EXISTS jregions "
							+ "(id INT not null AUTO_INCREMENT,prefix VARCHAR(100),perms VARCHAR(255),"

							+ "respawntime INT(100)," + "PRIMARY KEY (id))");

			ps.executeUpdate();
			ps.close();

			ps = main.database.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS waitforpolicewithlist "
					+ "(id INT not null AUTO_INCREMENT,pseudo VARCHAR(100),grade INT(100), PRIMARY KEY (id))");
			ps.executeUpdate();
			ps.close();

			// INSERT INTO shops (name, perms, it1, it2, it3, it4, it5, it6, it7, it8, it9,
			// prices)
			ps = main.database.getConnection()
					.prepareStatement("CREATE TABLE IF NOT EXISTS shops "
							+ "(id INT not null AUTO_INCREMENT,name VARCHAR(100),perms VARCHAR(255),"
							+ "it1 VARCHAR(1000)," + "it2 VARCHAR(1000)," + "it3 VARCHAR(1000)," + "it4 VARCHAR(1000),"
							+ "it5 VARCHAR(1000)," + "it6 VARCHAR(1000)," + "it7 VARCHAR(1000)," + "it8 VARCHAR(1000),"
							+ "it9 VARCHAR(1000)," + "prices VARCHAR(255),isselling VARCHAR(255)," + " PRIMARY KEY (id))");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void createAccount(Player p) {
		if (!hasAccount(p)) {
			try {
				PreparedStatement ps = main.database.getConnection().prepareStatement(
						"INSERT INTO players (pseudo, name, firstname, rank, job, company, money, playhour, policetime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
				ps.setString(1, p.getDisplayName());
				ps.setString(2, "§");
				ps.setString(3, "§");
				ps.setString(4, "joueur");
				ps.setString(5, "migrant");
				ps.setString(6, "§");
				ps.setInt(7, 200);
				ps.setInt(8, 0);
				ps.setInt(9, 0);
				ps.execute();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public boolean hasAccount(Player p) {
		try {
			PreparedStatement ps = main.database.getConnection()
					.prepareStatement("SELECT money FROM players WHERE pseudo = ?");
			ps.setString(1, p.getDisplayName());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	public void saveAccount(Player p) {
		RPlayer rp = main.playertoRplayers.get(p);
		if (hasAccount(p)) {
			try {

				PreparedStatement ps = main.database.getConnection().prepareStatement(
						"UPDATE players SET name = ?,firstname = ?, rank = ?, job = ?, company = ?, money = ?, playhour = ?, policetime = ?  WHERE pseudo = ?");
				ps.setString(1, rp.getName());
				ps.setString(2, rp.getFirstname());
				ps.setString(3, rp.getRank().getSimplename());
				ps.setString(4, rp.getJob().getSimplename());
				ps.setString(5, rp.getCompany());
				ps.setLong(6, rp.getMoney());
				ps.setInt(7, rp.getPlayhour());
				ps.setInt(8, rp.getPolicetime());
				ps.setString(9, p.getDisplayName());
				ps.executeUpdate();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void loadAccount(Player p) {

		if (!hasAccount(p))
			createAccount(p);

		try {
			PreparedStatement ps = main.database.getConnection()
					.prepareStatement("SELECT * FROM `players` WHERE pseudo='" + p.getName() + "'");
			ResultSet result = ps.executeQuery();
			if (main.playertoRplayers.containsKey(p))
				return;
			while (result.next()) {

				RPlayer rp = new RPlayer(result.getString("pseudo"), result.getString("name"),
						result.getString("firstname"), getRankByName(result.getString("rank")),
						getJobByName(result.getString("job")), result.getString("company"), result.getInt("money"),
						result.getInt("playhour"), 0, false, null);
				main.playertoRplayers.put(p, rp);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void saveJob() {

		try {
			PreparedStatement ps;
			ps = main.database.getConnection().prepareStatement("TRUNCATE TABLE jobs");
			ps.execute();
			ps.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		main.jobs.forEach(jb -> {

			try {
				PreparedStatement ps;
				ps = main.database.getConnection().prepareStatement(
						"INSERT INTO jobs (ids, displayname, simplename, perm, salaire, isPolice, item) VALUES (?, ?, ?, ?, ?, ?, ?)");
				ps.setInt(1, jb.getId());
				ps.setString(2, jb.getDisplayname());
				ps.setString(3, jb.getSimplename());
				ps.setInt(4, jb.getPerm());
				ps.setInt(5, jb.getSalaire());
				ps.setString(6, String.valueOf(jb.isPolice()));

				ps.setString(7, itemStackArrayToBase64(jb.getIt()));
				ps.execute();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			// NAME ITEM,ITEM,ITEM PRICE,PRICE,PRICE

		});

	}

	public void loadJob() throws IOException {

		try {
			PreparedStatement ps;
			ps = main.database.getConnection().prepareStatement("SELECT * FROM jobs");
			ResultSet result = ps.executeQuery();
			boolean asGood = false;
			while (result.next()) {
				new job(result.getInt("ids"), result.getString("displayname"), result.getString("simplename"),
						result.getInt("perm"), itemStackArrayFromBase64(result.getString("item")),
						result.getInt("salaire"), Boolean.parseBoolean(result.getString("isPolice")));
				asGood = true;
			}
			if (!asGood) {
				ps = main.database.getConnection().prepareStatement(
						"INSERT INTO jobs (ids, displayname, simplename, perm, salaire, isPolice, item) VALUES (?, ?, ?, ?, ?, ?, ?)");
				ps.setInt(1, 0);
				ps.setString(2, "§8[§7Migrant§8]");
				ps.setString(3, "migrant");
				ps.setInt(4, 0);
				ps.setInt(5, 100);
				ps.setString(6, String.valueOf("false"));

				ps.setString(7, itemStackArrayToBase64(new ItemBuilder(Material.APPLE).toItemStack()));
				ps.execute();
				ps.close();
				new job(0, "§8[§7Migrant§8]", "migrant", 0, new ItemBuilder(Material.APPLE).toItemStack(), 100, false);
			}
			ps.close();
		} catch (SQLException e1) {

		}

	}

	// public rank(int id, String displayname, String simplename, int power,
	// ItemStack it) {
	public void saveRank() {

		try {
			PreparedStatement ps;
			ps = main.database.getConnection().prepareStatement("TRUNCATE TABLE ranks");
			ps.execute();
			ps.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		main.ranks.forEach(rk -> {

			try {
				PreparedStatement ps;
				ps = main.database.getConnection().prepareStatement(
						"INSERT INTO ranks (ids, displayname, simplename, power, item) VALUES (?, ?, ?, ?, ?)");
				ps.setInt(1, rk.getId());
				ps.setString(2, rk.getDisplayname());
				ps.setString(3, rk.getSimplename());
				ps.setInt(4, rk.getPower());
				ps.setString(5, itemStackArrayToBase64(rk.getIt()));
				ps.execute();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		});

	}

	public void loadRank() throws IOException {
		try {
			PreparedStatement ps;
			ps = main.database.getConnection().prepareStatement("SELECT * FROM ranks");
			ResultSet result = ps.executeQuery();
			boolean asGood = false;
			while (result.next()) {
				new rank(result.getInt("ids"), result.getString("displayname"), result.getString("simplename"),
						result.getInt("power"), itemStackArrayFromBase64(result.getString("item")));
				asGood = true;
			}
			if (!asGood) {
				ps = main.database.getConnection().prepareStatement(
						"INSERT INTO ranks (ids, displayname, simplename, power, item) VALUES (?, ?, ?, ?, ?)");
				ps.setInt(1, 0);
				ps.setString(2, "§8[§7Joueur§8]");
				ps.setString(3, "joueur");
				ps.setInt(4, 0);
				ps.setString(5, itemStackArrayToBase64(new ItemBuilder(Material.APPLE).toItemStack()));
				ps.execute();
				ps.close();
				new rank(0, "§8[§7Joueur§8]", "joueur", 0, new ItemBuilder(Material.APPLE).toItemStack());
			}
			ps.close();
		} catch (SQLException e1) {

		}

	}

	public void loadPoliceWait() throws IOException {
		try {
			PreparedStatement ps;
			ps = main.database.getConnection().prepareStatement("SELECT * FROM waitforpolicewithlist");
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				main.playerforPoliceWhitlists.put(result.getString("pseudo"), result.getInt("grade"));

			}

			ps.close();
		} catch (SQLException e1) {

		}

	}

	public void savePoliceWait() {
		try {
			PreparedStatement ps;
			ps = main.database.getConnection().prepareStatement("TRUNCATE TABLE waitforpolicewithlist");
			ps.execute();
			ps.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		main.playerforPoliceWhitlists.forEach((name, grade) -> {
			try {
				PreparedStatement ps;
				ps = main.database.getConnection()
						.prepareStatement("INSERT INTO waitforpolicewithlist (pseudo, grade) VALUES (?, ?)");
				ps.setString(1, name);
				ps.setInt(2, grade);
				ps.execute();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		});

	}

	public void saveJregions() {

		try {
			PreparedStatement ps;
			ps = main.database.getConnection().prepareStatement("TRUNCATE TABLE jregions");
			ps.execute();
			ps.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		main.tregions.forEach(rg -> {

			StringBuilder strs = new StringBuilder();
			rg.getPerms().forEach(perm -> {
				strs.append(perm + ",");

			});
			String perms = strs.toString();

			try {
				PreparedStatement ps;
				ps = main.database.getConnection()
						.prepareStatement("INSERT INTO jregions (prefix, perms, respawntime) VALUES (?, ?, ?)");
				ps.setString(1, rg.getPrefix());
				ps.setString(2, perms);
				ps.setInt(3, rg.getRespawntime());
				ps.execute();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		});

	}

	public void loadJregion() throws IOException {
		try {
			PreparedStatement ps;
			ps = main.database.getConnection().prepareStatement("SELECT * FROM jregions");
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				new TResources(result.getString("prefix"), Arrays.asList(result.getString("perms").split(",")),
						result.getInt("respawntime"));
			}
			ps.close();
		} catch (SQLException e1) {

		}

	}

	public void saveShops() {

		try {
			PreparedStatement ps;
			ps = main.database.getConnection().prepareStatement("TRUNCATE TABLE shops");
			ps.execute();
			ps.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		main.shops.forEach(s -> {

			try {
				PreparedStatement ps;
				ps = main.database.getConnection().prepareStatement(
						"INSERT INTO shops (name, perms, it1, it2, it3, it4, it5, it6, it7, it8, it9, prices, isselling) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				ps.setString(1, s.getName());
				StringBuilder strs = new StringBuilder();
				s.getPerms().forEach(perm -> {
					strs.append(perm + ",");

				});
				String perms = strs.toString();
				ps.setString(1, s.getName());
				ps.setString(2, perms);
				ps.setString(3, "null");
				ps.setString(4, "null");
				ps.setString(5, "null");
				ps.setString(6, "null");
				ps.setString(7, "null");
				ps.setString(8, "null");
				ps.setString(9, "null");
				ps.setString(10, "null");
				ps.setString(11, "null");
				for (int i = 0; i < s.getItems().size(); i++) {
					if (i == 0) {
						ps.setString(3, itemStackArrayToBase64(s.getItems().get(i)) +"," + s.getItems().get(i).getItemMeta().getDisplayName());
					}else {
						ps.setString(i + 3, itemStackArrayToBase64(s.getItems().get(i)) +"," + s.getItems().get(i).getItemMeta().getDisplayName());
					}
				}
				if (s.getItems().size() == 9) {
					ps.setString(11, itemStackArrayToBase64(s.getItems().get(8)));
				}
				StringBuilder strs1 = new StringBuilder();
				s.getPrices().forEach(price->{
					
					strs1.append(String.valueOf(price) +",");	
				});
				String prices = strs1.toString();
				ps.setString(12, prices);
				ps.setString(13, String.valueOf(s.isIsselling()));
				
				ps.execute();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			// NAME ITEM,ITEM,ITEM PRICE,PRICE,PRICE

		});

	}
	
	public void loadShop() throws IOException {
		try {
			PreparedStatement ps;
			ps = main.database.getConnection().prepareStatement("SELECT * FROM shops");
			ResultSet result = ps.executeQuery();
			while (result.next()) {

				List<ItemStack> items = new ArrayList<>();
				int a = 1;
				while(a != 9) {
					String value = result.getString("it" + String.valueOf(a));
					if (!value.equalsIgnoreCase("null")) {
						ItemStack nit = itemStackArrayFromBase64(value.split(",")[0]);
						if (value.split(",")[1].equalsIgnoreCase("null")) {
							items.add(new ItemBuilder(nit.getType(), nit.getAmount()).setData(nit.getDurability()).toItemStack());
						}else {
							items.add(new ItemBuilder(nit.getType(), nit.getAmount()).setName(value.split(",")[1]).setData(nit.getDurability()).toItemStack());
						}
						
					}
					a++;
				}
				List<Integer> prices = new ArrayList<>();
				Arrays.asList(result.getString("prices").split(",")).forEach(price->{
					prices.add(Integer.valueOf(price));
				});
				new shopBase(result.getString("name"), items, prices, Arrays.asList(result.getString("perms").split(",")), isTrue(result.getString("isselling")));

				
				
			
			}
			
			ps.close();
		} catch (SQLException e1) {

		}

	}

	public void saveAll() {

		saveJob();
		saveRank();
		saveJregions();
		savePoliceWait();
		saveShops();
		Bukkit.getOnlinePlayers().forEach(p -> {
			saveAccount(p);
		});
		System.out.println("§8[§aDataBase§8] §fSauvegarde §2ok");

	}

	public void loadAll() throws IOException {
		loadJob();
		loadRank();
		loadJregion();
		loadPoliceWait();
		loadShop();
		Bukkit.getOnlinePlayers().forEach(p -> {
			loadAccount(p);
		});
		System.out.println("§8[§aDataBase§8] §fChargement §2ok");

	}

	public static String itemStackArrayToBase64(ItemStack item) throws IllegalStateException {
		if (item == null) {
			return "null";
		}
		String encodedObject;
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
			dataOutput.writeObject(item);
			dataOutput.flush();

			byte[] serializeobj = outputStream.toByteArray();

			encodedObject = Base64.getEncoder().encodeToString(serializeobj);
			// Serialize that array
			dataOutput.close();
			return encodedObject;
		} catch (Exception e) {
			throw new IllegalStateException("Unable to save item stacks.", e);
		}
	}

	public static ItemStack itemStackArrayFromBase64(String data) throws IOException {
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
			BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
			ItemStack items = (ItemStack) dataInput.readObject();

			dataInput.close();
			return items;
		} catch (ClassNotFoundException e) {
			throw new IOException("Unable to decode class type.", e);
		}
	}

	public job getJobByName(String name) {

		for (int i = 0; i < main.jobs.size(); i++) {
			if(main.jobs.get(i).getSimplename().equals(name)) {
				return main.jobs.get(i);
			}
		}
		for (int i = 0; i < main.policejobs.size(); i++) {
			if(main.policejobs.get(i).getSimplename().equals(name)) {
				return main.policejobs.get(i);
			}
		}
		return null;
	}

	public rank getRankByName(String name) {
		for (int i = 0; i < main.ranks.size(); i++) {
			if (main.ranks.get(i).getSimplename().equals(name)) {
				return main.ranks.get(i);
			}
		}
		return main.ranks.get(0);
	}
	
	public boolean isTrue(String name) {
		if (name.equalsIgnoreCase("true")) {
			return true;
		}
		return false;
	}

}
