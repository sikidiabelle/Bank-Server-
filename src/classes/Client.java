package classes;

import java.io.Serializable;

/**
* classe representant un client de la banque 
* @author TIC1.3
*
*/

public class Client implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Id du client
	 */
	private int idClient = 0;
	/**
	 * Civilite du client
	 */
	private char civ = Character.MIN_VALUE;
	/**
	 * Nom du client
	 */
	private String nom = null;
	/**
	 * Prenom du client
	 */
	private String prenom = null;
	/**
	 * Adresse domicile du client
	 */
	private String adr = null;
	/**
	 * Date de naissance du client
	 */
	private java.sql.Date bday = null;
	/**
	 * Email du client
	 */
	private String email = null;
	/**
	 * Mot de passe du client
	 */
	private String mdp;
	/**
	 * Pays de r�sidence client
	 */
	private String pays = null;
	/**
	 * Ville de r�sidence du client
	 */
	private String city = null;
	/**
	 * Nationalit� du client
	 */
	private String nat = null;
	/**
	 * Code postal du client client
	 */
	private String cP;
	/**
	 * Num�ro de t�l�phone mobile du client
	 */
	private String tel;
	
	/**
	 * Constructeur pour un client d�j� inscrit
	 * @param idClient id du client
	 * @param civ civilit� du client
	 * @param nom nom du client
	 * @param prenom prenom du client
	 * @param adr adresse du client
	 * @param bday date de naissance du client
	 * @param email email du client
	 * @param mdp mot de passe du client
	 * @param pays pays de r�sidence
	 * @param city ville de r�sidence
	 * @param nat nationalit� du client
	 * @param cP code postal du client
	 * @param tel num�ro de t�l�phone mobile
	 */
	public Client(int idClient, char civ, String nom, String prenom, String adr, java.sql.Date bday,
			String email, String mdp, String pays, String city, String nat, String cP, String tel) {
		
		this.idClient=idClient; this.civ=civ; this.nom=nom; this.prenom=prenom;
		this.adr=adr; this.bday=bday; this.email=email; this.pays=pays;
		this.city=city; this.nat=nat; this.cP = cP; this.tel = tel; this.mdp=mdp;
		
	}
	
	/**
	 * Renvoie l'id du client 
	 * @return retourne l'id du client dans la BDD
	 */
	public int getIdClient() {return idClient; }
	/**
	 * Renvoie un caract�re indiquant le genre du client 
	 * @return retourne un caract�re indiquant le genre du client
	 */
	public char getCiv() {return civ; }
	/**
	 * Renvoie le nom du client 
	 * @return retourne le nom du client
	 */
	public String getNom() {return nom; }
	/**
	 * Renvoie le pr�nom du client 
	 * @return retourne le pr�nom du client
	 */
	public String getPrenom() {return prenom; }
	/**
	 * Renvoie l'adresse du client 
	 * @return retourne l'adresse du client
	 */
	public String getAdr() {return adr; }
	/**
	 * Renvoie la date de naissance du client 
	 * @return retourne la date de naissance du client
	 */
	public java.sql.Date getBday() {return bday; }
	/**
	 * Renvoie l'email du client 
	 * @return retourne l'email du client
	 */
	public String getEmail() {return email; }
	/**
	 * Renvoie le mot de passe du client 
	 * @return retourne le mot de passe du client
	 */
	public String getMdp() { return mdp; }
	/**
	 * Renvoie le pays de r�sidence du client 
	 * @return retourne le pays de r�sidence du client  
	 */
	public String getPays() {return pays; }
	/**
	 * Renvoie la ville de r�sidence du client 
	 * @return retourne la ville de r�sidence du client
	 */
	public String getCity() {return city; }
	/**
	 * Renvoie la nationalit� du client 
	 * @return retourne la nationalit� du client 
	 */
	public String getNat() {return nat; }
	/**
	 * Renvoie le code postal du client 
	 * @return retourne l'id du client dans la BDD
	 */
	public String getCP() {return cP; }
	/**
	 * Renvoie le num�ro de t�l�phone du client 
	 * @return retourne le num�ro du client 
	 */
	public String getTel() {return tel; }
	
}
