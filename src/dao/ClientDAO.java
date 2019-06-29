
 /**
² * Classe liant notre appli  la BDD
 * @author TIC1.3
 * @version 1.0
 *
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import classes.Client;

public class ClientDAO {

	/**
	 * Conctantes de connexion  la BDD
	 */
	final static String URL = "jdbc:mysql://localhost/bank?useTimezone=true&serverTimezone=GMT";
	final static String LOGIN = "root";
	final static String PASS = "root"; 
	
	public ClientDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Success loading driver");
		} catch (ClassNotFoundException e) {
			System.err
					.println("Impossible de charger le pilote de BDD, ne pas oublier d'importer le fichier .jar dans le projet");
		}
	}
	
	
	/**
	 * Renvoie les infos du client
	 * @param id l'id du client dont on requiert les infos
	 * @return un objet client contenant toutes ses infos
	 */
	public Client getInfos(int id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Client retour = null;
		
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT * FROM client WHERE idClient=?");
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				retour = new Client(
						rs.getInt("idClient"),
						rs.getString("civ").charAt(0),
						rs.getString("nom"),
						rs.getString("prenom"),
						rs.getString("adr"),
						rs.getDate("bday"),
						rs.getString("email"),
						rs.getString("mdp"),
						rs.getString("pays"),
						rs.getString("city"),
						rs.getString("nat"),
						rs.getString("cP"),
						rs.getString("tel")
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
	 * Renvoie le type de transaction 
	 * @param nom mot à rechercher dans la table des clients
	 * @return retourne la liste des clients ayant ce mot clé
	 */
	public ArrayList<Client> getListeClient(String nom)
	{
		ArrayList<Client> retour = new ArrayList<Client>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT * FROM client WHERE nom LIKE ? OR prenom LIKE ? ");
			ps.setString(1, "%"+nom+"%");
			ps.setString(2, "%"+nom+"%");
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				retour.add(new Client(rs.getInt("idClient"),
						rs.getString("civ").charAt(0),
						rs.getString("nom"),
						rs.getString("prenom"),
						rs.getString("adr"),
						rs.getDate("bday"),
						rs.getString("email"),
						rs.getString("mdp"),
						rs.getString("pays"),
						rs.getString("city"),
						rs.getString("nat"),
						rs.getString("cP"),
						rs.getString("tel")));
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
	
	
	public static void main(String[] args) {
		
		ClientDAO c = new ClientDAO();
		System.out.print(c.getListeClient("").get(2).getIdClient());
	}
	
}
