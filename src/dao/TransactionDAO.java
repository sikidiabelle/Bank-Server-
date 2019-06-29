package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import classes.Transaction;

/**
 * Classe liant notre appli  la BDD
 * @author TIC1.3
 * @version 1.0
 *
 */
public class TransactionDAO {

	/**
	 * Conctantes de connexion  la BDD
	 */
	final static String URL = "jdbc:mysql://localhost/bank?useTimezone=true&serverTimezone=GMT";
	final static String LOGIN = "root";
	final static String PASS = "root"; 
	
	public TransactionDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Success loading driver");
		} catch (ClassNotFoundException e) {
			System.err
					.println("Impossible de charger le pilote de BDD, ne pas oublier d'importer le fichier .jar dans le projet");
		}
	}
	
	/**
	 * Renvoie tout l'historique des transactions
	 * @return une liste d'objets transaction contenant toutes les infos des transactions 
	 */
	public ArrayList<Transaction> getHisto() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Transaction> retour = new ArrayList<Transaction>();
		
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT * FROM transaction");
			
			rs = ps.executeQuery();
			
			String iban_cible;
			
			while(rs.next()) {
				//Vérifie ci le champ destinataire est vide (cas d'un depot)
				if (rs.getString("iban_cible").isEmpty()) iban_cible = "Non défini";
				else iban_cible = rs.getString("iban_cible");
				
				Transaction t = new Transaction(
						rs.getInt("idTrans"),
						rs.getString("descp"),
						rs.getString("numCpt_src"),
						iban_cible,
						rs.getDouble("montant"),
						rs.getBoolean("type"),
						rs.getTimestamp("date")
						);
				retour.add(t);
			}
			
		} catch (Exception ee) {
			ee.printStackTrace();
		} finally {
			// fermeture du ResultSet, du PreparedStatement et de la Connexion
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ignore) {
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception ignore) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception ignore) {
			}
		}
		return retour;
	}
	
	/**
	 * Renvoie la liste de tous les depots des dépots
	 * @return une liste d'objets transaction contenant toutes les infos des dépots 
	 */
	public ArrayList<Transaction> getDepots() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Transaction> retour = new ArrayList<Transaction>();
		
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			// Recupere les transaction dont les 5 premiers caractères de l'attribut desc
			// sont égal à "Dépot"
			ps = con.prepareStatement("SELECT * FROM transaction WHERE (SUBSTRING(descp, 1, 5)=?)");
			ps.setString(1, "Dépôt");
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Transaction t = new Transaction(
						rs.getInt("idTrans"),
						rs.getString("descp"),
						rs.getString("numCpt_src"),
						"Non défini",
						rs.getDouble("montant"),
						rs.getBoolean("type"),
						rs.getTimestamp("date")
						);
				retour.add(t);
			}
			
		} catch (Exception ee) {
			ee.printStackTrace();
		} finally {
			// fermeture du ResultSet, du PreparedStatement et de la Connexion
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ignore) {
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception ignore) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception ignore) {
			}
		}
		return retour;
	}
	
	/**
	 * Renvoie toutes les transactions pour un compte donnée
	 * @param numCpt numero du compte source dont on recherche les transactions
	 * @return une liste d'objets transaction contenant toutes les infos des transaction du compte 
	 */
	public ArrayList<Transaction> getTransactions(String numCpt) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Transaction> retour = new ArrayList<Transaction>();
		
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT * FROM transaction WHERE numCpt_src=?");
			ps.setString(1, numCpt);
			
			rs = ps.executeQuery();
			
			String iban_cible;
			
			while(rs.next()) {
				//Vérifie ci le champ destinataire est vide (cas d'un depot)
				if (rs.getString("numCpt_dest").isEmpty()) iban_cible = "Non défini";
				else iban_cible = rs.getString("numCpt_dest");
				
				Transaction t = new Transaction(
						rs.getInt("idTrans"),
						rs.getString("descp"),
						rs.getString("numCpt_src"),
						iban_cible,
						rs.getDouble("montant"),
						rs.getBoolean("type"),
						rs.getTimestamp("date")
						);
				retour.add(t);
			}
			
		} catch (Exception ee) {
			ee.printStackTrace();
		} finally {
			// fermeture du ResultSet, du PreparedStatement et de la Connexion
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ignore) {
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception ignore) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception ignore) {
			}
		}
		return retour;
	}
}