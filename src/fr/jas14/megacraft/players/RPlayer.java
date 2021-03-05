package fr.jas14.megacraft.players;

import org.bukkit.entity.Player;

import fr.jas14.megacraft.Main;
import fr.jas14.megacraft.players.jobs.job;
import fr.jas14.megacraft.players.rank.rank;
public class RPlayer {
	
	
	private String name, firstname, company, pseudo;
	private rank rank;
	private job job;
	private int playhour, weight, policetime;
	private int money;
	private Player asPlayer;
	private boolean isDead;
	
	
	public RPlayer(String pseudo, String name, String firstname, rank rank, job job , String company, int money, int playhour, int weight, boolean isDead, Player asPlayer) {
		
		Main.getInstance().rplayers.add(this);
		this.pseudo = pseudo;
		this.name = name;
		this.firstname = firstname;
		this.rank = rank;
		this.job = job;
		this.company = company;
		this.money = money;
		this.playhour = playhour;
		this.weight = weight;
		this.isDead = isDead;
		this.asPlayer = asPlayer;
		policetime = 0;

	}
	

	// GETTER AND SETTER 
	public long getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public void addMoney(int money) {
		this.money+= money;

	}
	public boolean removeMoney(int money) {
		if(this.money >= money) {
			this.money-= money;
			return true;
		}
		return false;
		
	}
	
	// 
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	//
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	//
	public rank getRank() {
		return rank;
	}
	public void setRank(rank rank) {
		this.rank = rank;
	}

	//
	public job getJob() {
		return job;
	}

	
	public void setJob(job job) {
		this.job = job;
		if (job.isPolice()) {
			setPolicetime(playhour);
		}
	}

	//
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	//
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	//
	public int getPlayhour() {
		return playhour;
	}
	public void setPlayhour(int playhour) {
		this.playhour = playhour;
	}

	//
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public Player getAsPlayer() {
		return asPlayer;
	}
	public void setAsPlayer(Player asPlayer) {
		this.asPlayer = asPlayer;
	}
	public boolean isDead() {
		return isDead;
	}
	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}
	
	public int getPolicetime() {
		return policetime;
	}
	public void setPolicetime(int policetime) {
		this.policetime = policetime;
	}
	
	
	

}
