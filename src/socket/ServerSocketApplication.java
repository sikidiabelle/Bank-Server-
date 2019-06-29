package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import dao.ClientDAO;
import dao.CompteDAO;
import dao.GerantDAO;
import dao.MessageDAO;
import dao.TransactionDAO;


/**
* classe permettant de cr�er un socket serveur
* @author TIC1.3
*
*/
public class ServerSocketApplication extends Thread {
	/*
	 * serverSocket socket server associ� � la classe
	 */
	private ServerSocket serverSocket = null;
	private final static int port = 100;
	
	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Le serveur a d�marr�");
			while(true) {
				System.out.println("Serveur en attente de connexion......");
				Socket s = serverSocket.accept(); //Connexion client
				new Traitement(s).start(); //D�marrage d'un thread associ� � la connexion
			}	
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	class Traitement extends Thread{
		/*
		 * socket en cours de traitement dans le thread
		 */
		private Socket s;
		/*
		 * flux d'entr�e binaire
		 */
		private InputStream input = null;
		/*
		 * flux de sortie binaire
		 */
		private OutputStream output = null;
		/*
		 * lecteur d'entr�e chaine de caract�res
		 */
		private BufferedReader br = null;
		/*
		 * objet dans le flux de sortie
		 */
		private ObjectOutputStream oos = null;	
		
		/*
		 * requ�te liste des clients
		 */
		public String listeClients = "LISTE_CLIENTS";
		/*
		 * requ�te liste des messages
		 */
		public String listeMessages = "LISTE_MESSAGES";
		/*
		 * requ�te get message
		 */
		public String getMessage = "GET_MESSAGE_ID = ";
		/*
		 * requ�te delete message
		 */
		public String deleteMessage = "DELETE_MESSAGE_ID = ";
		/*
		 * requ�te de v�rification connexion
		 */
		public String checkLogin = "CHECK_LOGIN_(USER,PASS)";
		/*
		 * requ�te get liste des comptes
		 */
		public String getListeCompte = "GET_LISTE_COMPTE_ID = ";
		/*
		 * requ�te get infos du compte
		 */
		public String getInfoCompte = "GET_INFO_COMPTE = ";
		/*
		 * requ�te get historique des transactions
		 */
		public String historiqueTransaction = "HISTORIQUE_TRANSACTION";
		/*
		 * requ�te get transaction
		 */
		public String getTransactions = "GET_TRANSACTION_COMPTE = ";
		/*
		 * requ�te get solde d'un compte
		 */
		public String getSolde = "GET_SOLDE_COMPTE = ";
		/*
		 * requ�te get type de compte
		 */
		public String getTypeCompte = "GET_TYPE_COMPTE = ";
		/*
		 * requ�te get infos conseiller
		 */
		public String getInfoConseiller = "GET_INFO_CONSEILLER = ";
		/*
		 * requ�te get infos client
		 */
		public String getInfoClient = "GET_INFO_CLIENT = ";





		private String requeteClient;

		/**
		 * Constructeur du thread de traitement de la socket
		 * @param socket associ� au thread
		 */
		public Traitement(Socket s){
			super();
			this.s = s;
		}
		
		@Override
		public void run() {
			System.out.println("Connexion �tablie avec la machine " + s.getRemoteSocketAddress().toString());
			
			try {
				input = s.getInputStream(); // Flux d'entr�e
				br = new BufferedReader(new InputStreamReader(input)); //Lecteur de ligne
				
				output = s.getOutputStream(); // Flux de sortie
				oos = new ObjectOutputStream(output); // Objet en sortie
				
				while(true) {
					requeteClient = br.readLine();
					System.out.println("IP : "+ s.getRemoteSocketAddress().toString() +" envoie la requ�te " + requeteClient);
					if(requeteClient!=null) {
						if(requeteClient.equals(listeClients)) {
							ClientDAO c = new ClientDAO();
							Object objet = c.getListeClient("");
							oos.writeObject(objet);
							oos.flush();
							System.out.println("Liste des clients envoy�e au client");
						}
						if(requeteClient.equals(listeMessages)) {
							MessageDAO m = new MessageDAO();
							Object objet = m.getListeMessage();
							oos.writeObject(objet);
							oos.flush();
							System.out.println("Liste des messages envoy�e au client");
						}
						if(requeteClient.equals(historiqueTransaction)) {
							TransactionDAO t = new TransactionDAO();
							Object objet = t.getHisto();
							oos.writeObject(objet);
							oos.flush();
							System.out.println("L'historique des transaction envoy�e au client");
						}
						if(requeteClient.startsWith(getTransactions)) {
							TransactionDAO t = new TransactionDAO();
							String numCpt = requeteClient.substring(25);
							Object objet = t.getTransactions(numCpt);
							oos.writeObject(objet);
							oos.flush();
							System.out.println("Transaction envoy�e au client");
						}
						if(requeteClient.startsWith(getMessage)) {
							MessageDAO m = new MessageDAO();
							int id = Integer.valueOf(requeteClient.substring(17));
							Object objet = m.getMessage(id);
							oos.writeObject(objet);
							oos.flush();
							System.out.println("Message envoy�e au client");
						}
						if(requeteClient.startsWith(getListeCompte)) {
							CompteDAO m = new CompteDAO();
							int id = Integer.valueOf(requeteClient.substring(22));
							Object objet = m.getCpts(id);
							oos.writeObject(objet);
							oos.flush();
							System.out.println("Liste de comptes envoy�e au client");
						}
						if(requeteClient.startsWith(getInfoCompte)) {
							CompteDAO m = new CompteDAO();
							String numCpt = requeteClient.substring(18);
							Object objet = m.getInfosCompte(numCpt);
							oos.writeObject(objet);
							oos.flush();
							System.out.println("Info du compte envoy�e au client");
						}
						if(requeteClient.startsWith(deleteMessage)) {
							MessageDAO m = new MessageDAO();
							int id = Integer.valueOf(requeteClient.substring(20));
							Object objet = m.deleteMessage(id);
							oos.writeObject(objet);
							oos.flush();
							System.out.println("Message supprim�");
						}
						if(requeteClient.startsWith(checkLogin)) {
							GerantDAO g = new GerantDAO();
							String user;
							String mdp;
							user = br.readLine();
							mdp = br.readLine();
							System.out.println(user);
							System.out.println(mdp);
							Object objet = g.checkLogin(user, mdp);
							oos.writeObject(objet);
							oos.flush();
							System.out.println("V�rification effectu�e");
						}
						if(requeteClient.startsWith(getSolde)) {
							CompteDAO m = new CompteDAO();
							String numCpt = requeteClient.substring(19);
							System.out.println(numCpt);
							Object objet = m.getSold(numCpt);
							oos.writeObject(objet);
							oos.flush();
							System.out.println("Info du compte envoy�e au client");
						}
						if(requeteClient.startsWith(getTypeCompte)) {
							CompteDAO m = new CompteDAO();
							String numCpt = requeteClient.substring(18);
							Object objet = m.getTypeCompte(numCpt);
							oos.writeObject(objet);
							oos.flush();
							System.out.println("Info du compte envoy�e au client");
						}
						if(requeteClient.startsWith(getInfoConseiller)) {
							GerantDAO g = new GerantDAO();
							int id = Integer.valueOf(requeteClient.substring(22));
							Object objet = g.getInfos(id);
							oos.writeObject(objet);
							oos.flush();
							System.out.println("Info du conseiller envoy�e au client");
						}
						if(requeteClient.startsWith(getInfoClient)) {
							ClientDAO c = new ClientDAO();
							int id = Integer.valueOf(requeteClient.substring(18));
							Object objet = c.getInfos(id);
							oos.writeObject(objet);
							oos.flush();
							System.out.println("Info du client envoy�e au client");
						}
					}
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public static void main(String[] args) {
		
		new ServerSocketApplication().start();
		
		
		//Permet de trouver tous les ports disponibles
		/*for(int port = 1; port <= 200; port++){
	         try {
	            ServerSocket sSocket = new ServerSocket(port);
	            System.err.println("Le port " + port + " est libre! ");
	         } catch (IOException e) {
	            //System.err.println("Le port " + port + " est d�j� utilis� ! ");
	         }
	      }*/
	}

}
