package fr.jas14.megacraft.others;


public class messagesSystem {
	
	
	// GLOBAL SERVER 
	public String servername = "§0§l[§3§oMegaCraft§0§l] ";
	public String notPermission = servername +"§cVous n'avez pas le droit de faire ça !";
	public String playerNotHere = servername +"§cLe joueur n'est pas en ligne ou dans la liste !";
	
	// JOB AND RANK
	public String prefixchange = servername +"§2Le préfix a bien été changer en :§r";
	public String itsnumberserror = servername +"§cL'action a été annulée car vous devez donner un nombre";
	public String permissionchanged = servername +"§2La permission a bien été changer en :§r";
	
	// JOB
	public String salairechanged = servername + "§2Le salaire a bien été changer en :§r";
	public String jobnotgoodinformations = servername + "§cL'action a été annulée car vous devez indiquer les informations suivantes séparées par une virgule : §4le nom simple, la permission, le nom d'affichage et salaire §7§o par exemple \"boulanger,5,&4[Boulanger],500\"";
	public String jobAlreadyExist = servername + "§cVous ne pouvez pas créé ce métier car il existe déjà !";
	
	// RANK
	public String ranknotgoodinformations = servername + "§cL'action a été annulée car vous devez indiquer les informations suivantes séparées par une virgule : §4le nom simple, la permission et le nom d'affichage §7§o par exemple \"admin,5,&4[Administrateur]\"";
	public String rankAlreadyExist = servername + "§cVous ne pouvez pas créé ce rang car il existe déjà !";
	
	
	// ECONOMIE SYSTEM
	public String banque = "§8[§2§oBanque§8] ";
	public String nomoney = banque + "§cVous n'avez pas l'argent nécessaire";
	public String maderetrait = banque + "§fVous avez effectué un §4retrait";
	public String madedepot = banque + "§fVous avez effectué un §2dépot";
	
	// DEATH SYSTEM
	public String cantGetCorp = servername + "§cVous n'avez pas la capicité pour porter ce corp";
	public String getCorp = servername + "§2Vous porter un joueur";
	public String corpisDead = servername + "§eLe joueur est mort !";
	public String vousportereja = servername + "§cVous portez déjà quelqu'un !";
	public String vouslavezlacher = servername + "§cVous venez de lacher un joueur au sol !";
	public String onmeporte = servername + "§cQuelqu'un vous porte sur sont dos";
	
	// POLICE SYSTEME FOR PLAYER
	public String acceptedCandidature = servername + "§2Votre candidature a été accepté !";
	public String denyedCandidature = servername + "§cVotre candidature a été refusé !";
	public String sendedCandidature = servername + "§aVotre candidature a bien était envoyé";
	public String notPlayTimeRequierd = servername + "§cVous n'avez pas le temps de jeu requis.";
	
	// POLICE SYSTEM FOR ADMIN
	public String acceptedCandidatureAdmin = servername + "§2Vous avez accepté la candidature de §f";
	public String denyedCandidatureAdmin = servername + "§cVous avez refusé la candidature de §f";
	public String truePoliceCommands = servername + "§cVous devez faire §f /police list §cou§f /police (accept/deny) joueur";
	
	// PARCEL
	public String parcelBuy = servername + "§fVouc venez §2d'acheter§f la parcel :";
	public String parcelsell = servername + "§fVouc venez §4de vendre§f votre parcel !";
	public String forcesell = servername + "§fVouc venez §4réisinalisé§f cette parcel !";
	public String trysell = servername + "§fSi vous voulez revendre votre parcelle faite une shift+click";
	
	
	// MODERATION 
	public String setrank = servername + "§fVous venez de changer le rang du joueur en : ";
	public String setjob = servername + "§fVous venez de changer le métier du joueur en : ";
	public String setrankn = servername + "§fUn administrateur vous as donner le rang : ";
	public String setjobn = servername + "§fUn administrateur vous as donner le métier : ";
	public String kick = servername + "§fVous venez de §4kick §f le joeur §e";
	public String ban = servername + "§fVous venez de §4ban §f le joeur §e";
	public String goodrankcommand = servername + "§cVous devez faire la command : §f /rank pseudo grade";
	public String goodjobcommand = servername + "§cVous devez faire la command : §f /job pseudo metier";
	public String goodkickcommand = servername + "§cVous devez faire la command : §f /kick pseudo raison";
	public String goodbancommand = servername + "§cVous devez faire la command : §f /ban pseudo raison";
	
	
	
	//SHOP
	public String goodShopCommand = servername + "§cVous devez faire la commandes suivante : §f/shop create (nom du pnj) (perm,perm...) (prix de l'item1,2,3,4) (true/false)\n§cou §f/shop delete nom du pnj\n§cou §f/shop list";
	public String shopCreated = servername + "§2Vous venez de crée un shop";

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


}
