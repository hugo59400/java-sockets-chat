package fr.dampierre.sockets;

import java.net.Socket;

public final class Client {

  private final String ADRESSE = "127.0.0.1";
  private final int PORT = 8999;

  private void demarrer() {

    System.out.println("### Tentative de connexion au serveur.");

    Socket socket = null;

    try {
      socket = new Socket(ADRESSE, PORT);

    } catch (Exception ex) {
      System.out.println("### Erreur connexion serveur");
      System.out.println("### Détails : " + ex.getMessage());
      System.exit(0);
    }

    System.out.println("### Connecté au serveur.");

    EcouteThread ecouteThread = new EcouteThread(socket);
    EnvoiThread envoiThread = new EnvoiThread(socket);

    ecouteThread.start();
    envoiThread.start();

  }


  public static void main(String[] args) {
    Client client = new Client();
    client.demarrer();
  }
}
