/**
 * Classe liant notre appli à  la BDD
 * @author TIC1.3
 * @version 1.0
 *
 */
package dao;

import java.sql.Connection;
import java.util.*;

import classes.Compte;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CompteDAO {

	/**
	 * Conctantes de connexion à la BDD
	 */
	final static String URL = "jdbc:mysql://localhost/Bank?useTimezone=true&serverTimezone=GMT";
	final static String LOGIN = "root";
	final static String PASS = "root"; 
	
	public CompteDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Success loading driver");
		} catch (ClassNotFoundException e) {
			System.err
					.println("Impossible de charger le pilote de BDD, ne pas oublier d'importer le fichier .jar dans le projet");
		}
	}

	
		/**
	 * Renvoie les infos du compte 
	 * @param numCpt le numéro du compte dont on requiert les infos
	 * @return un objet compte contenant toutes ses infos
	 */
	public Compte getInfosCompte(String numCpt) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Compte retour = null;
		
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT * FROM compte WHERE numCompte=?");
			ps.setString(1, numCpt);
			
			rs = ps.executeQuery();
			
			
			if(rs.next()) {
				retour = new Compte(
						rs.getString("numCompte"),
						rs.getString("rib"),
						rs.getString("iban"),
						rs.getInt("ClientID")
						);
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
	 * Permet de récupérer le solde d'un compte
	 * @param numero muméro du compte
	 * @return solde solde du compte
	 */
	
	
	@SuppressWarnings("resource")
	public double getSold (String numero) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		double retour = 0;
		CompteDAO c = new CompteDAO();
        int type = c.getTypeCompte(numero);	
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			if(type ==1) 
				{ps = con.prepareStatement("SELECT solde FROM cbancaire WHERE numCpt=?");}
			if(type ==0) 
				{ps = con.prepareStatement("SELECT solde FROM cepargne WHERE numCpt=?");}
			if(type ==2) 
				{ps = con.prepareStatement("SELECT capital FROM ctitre WHERE numCpt=?");}
			
			ps.setString(1, numero);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				retour = rs.getDouble(1);
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
	 * Permet de récupérer les comptes d'un Client
	 * @param id Identifiant du client
	 * @return liste de ses comptes
	 */
	
	public ArrayList<String> getCpts(int id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<String> retour = new ArrayList<String>();
		
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT numCompte FROM compte WHERE ClientID=?");
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String t = rs.getString("numCompte");
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
	 * Type de compte
	 * @param num numéro de compte
	 * @return entier correspondant au type de compte 0 : épargne, 1 : courant, 2 : titre
	 */
	public int getTypeCompte(String num)
	{
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs;
			String v= "0";
			int retour = 0;
			
			try {
	
				// tentative de connexion
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				
				ps = con.prepareStatement("SELECT iban FROM compte WHERE numCompte=?");
				ps.setString(1,num);
				// Excution de la requte
				rs = ps.executeQuery();
				if(rs.next())
				{
					v = rs.getString(1); 
				}
				if(v.charAt(13) == '0')
				{
					retour = 0;
				}
				if(v.charAt(13) == '1')
				{
					retour = 1;
				}
				if(v.charAt(13) == '2')
				{
					retour = 2;
				}
				
	
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// fermeture du preparedStatement et de la connexion
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
