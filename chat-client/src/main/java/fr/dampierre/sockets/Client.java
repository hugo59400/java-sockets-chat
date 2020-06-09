package fr.dampierre.sockets;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public final class Client {

  private final String ADRESSE = "127.0.0.1";
  private final int PORT = 8999;

  private void demarrer() throws Exception {

    System.out.println("### Tentative de connexion au serveur.");

    Socket socket = seConnecter();

    System.out.println("### Connecté au serveur.");

    String pseudo = demanderPseudo();

    envoyerPseudo(socket, pseudo);

    EcouteThread ecouteThread = new EcouteThread(socket);
    EnvoiThread envoiThread = new EnvoiThread(socket);

    ecouteThread.start();
    envoiThread.start();

  }

  private Socket seConnecter() {
    Socket socket = null;
    try {
      socket = new Socket(ADRESSE, PORT);
    } catch (Exception ex) {
      System.out.println("### Erreur connexion serveur");
      System.out.println("### Détails : " + ex.getMessage());
      System.exit(0);
    }
    return socket;
  }

  private String demanderPseudo() {
    Scanner in = new Scanner(System.in);
    System.out.print("Entrez votre pseudo : ");
    String pseudo = in.nextLine();
    return pseudo;
  }

  private void envoyerPseudo(Socket socket, String pseudo) throws IOException {
    OutputStream outStream = socket.getOutputStream();
    DataOutputStream outDataStream = new DataOutputStream(outStream);
    outDataStream.writeUTF(pseudo);
  }


  public static void main(String[] args) {
    Client client = new Client();
    try {
      client.demarrer();
    } catch (Exception e) {
      System.out.println("Une erreur s'est produite.");
    }
  }
}
