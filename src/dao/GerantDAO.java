/**
 /**
 * Classe liant notre appli  la BDD
 * @author SIKI DABELLE ELOI
 * @version 1.0
 *
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import classes.Gerant;

public class GerantDAO {

	/**
	 * Conctantes de connexion  la BDD
	 */
	final static String URL = "jdbc:mysql://localhost/bank?useTimezone=true&serverTimezone=GMT";
	final static String LOGIN = "root";
	final static String PASS = "root"; 
	
	public GerantDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Success loading driver");
		} catch (ClassNotFoundException e) {
			System.err
					.println("Impossible de charger le pilote de BDD, ne pas oublier d'importer le fichier .jar dans le projet");
		}
	}
	
	/**
	 * Vérifie l'email et le mot de passe d'un gerant
	 * @param email email à tester
	 * @param pwd mot de passe renseigné
	 * @return l'id du conseiller s'il est présent dans la bdd
	 * et 0 sinon
	 */
	
	public int checkLogin(String email, String pwd){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Gerant gerant;
		int retour = 0;
		
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT * FROM gerant WHERE email=? AND mdp = ? AND STATUS = ?");
			ps.setString(1, email);
			ps.setString(2, pwd);
			ps.setString(3, "conseiller");
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				gerant = new Gerant(
						rs.getInt("idGerant"),
						rs.getString("email"),
						rs.getString("mdp"),
						rs.getString("status")
						);
				retour = gerant.getIdGerant();
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
	 * Renvoie les infos du gerant
	 * @param id id du gerant
	 * @return un objet gerant contenant toutes ses infos
	 */
	public Gerant getInfos(int id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Gerant retour = null;
		
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT * FROM gerant WHERE idGerant = ? ");
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				retour = new Gerant(
						rs.getInt("idGerant"),
						rs.getString("email"),
						rs.getString("mdp"),
						rs.getString("status")
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
	 * Permet d'ajouter un gerant dans la table
	 * 
	 * @param g le gerant  ajouter
	 * @return retourne le nombre de lignes ajoutees dans la table
	 */
	public int add(Gerant g) {
		Connection con = null;
		PreparedStatement ps = null;
		int retour = 0;
		
		try {

			// tentative de connexion
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			
			ps = con.prepareStatement("INSERT INTO gerant "
					+ "(email, mdp, status)"
					+ " VALUES (?,?,?)");
			ps.setString(1, g.getEmail());
			ps.setString(2, g.getMdp());
			ps.setString(3, g.getStatus());

			// Excution de la requte
			retour = ps.executeUpdate();

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
	
	public static void main(String[] args) {
		GerantDAO d = new GerantDAO();
		System.out.println(d.checkLogin("toto@toto.to", "toto"));
		
	}
	
}