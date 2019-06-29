/**
 /**
 * Classe liant notre appli  la BDD
 * @author SIKI DIABELLE ELOI
 * @version 1.0
 *
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import classes.Message;

public class MessageDAO {

	/**
	 * Constantes de connexion  la BDD url
	 */
	final static String URL = "jdbc:mysql://localhost/bank?useTimezone=true&serverTimezone=GMT";
	/**
	 * Constantes de connexion  la BDD login
	 */
	final static String LOGIN = "root";
	/**
	 * Constantes de connexion  la BDD pass
	 */
	final static String PASS = "root"; 
	
	/**
	 * Constructeur de la classe de liason des messages à la BDD
	 */
	public MessageDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Success loading driver");
		} catch (ClassNotFoundException e) {
			System.err
					.println("Impossible de charger le pilote de BDD, ne pas oublier d'importer le fichier .jar dans le projet");
		}
	}
	
	/**
	 * Récupérer un message de la BDD
	 * @param id identifiant du message
	 * @return retourne le message correspondant à l'id
	 */
	public String getMessage(int id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String retour = null;
		
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT text_message FROM message WHERE id=?");
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				retour = rs.getString("text_message");
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
	 * Supprimer un message de la BDD
	 * @param id id du message
	 */
	public int deleteMessage(int id)
	{
		Connection con = null;
		PreparedStatement ps = null;
		int retour = 0;
		
		try {

			// tentative de connexion
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			
			ps = con.prepareStatement("DELETE FROM message WHERE id = ?");
			ps.setInt(1, id);
			
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
	
	/**
	 * Permet de récupérer les messages dans la boite de réception
	 * @return liste des messages
	 */
	public ArrayList<Message> getListeMessage()
	{
		ArrayList<Message> retour = new ArrayList<Message>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT * FROM message");
			rs = ps.executeQuery();
			
			while(rs.next()) {
				retour.add(new Message(rs.getInt("id"),rs.getString("email"), rs.getString("objet"), rs.getString("text_message")));
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
		MessageDAO m = new MessageDAO();
		m.deleteMessage(2);
	}
	
}
