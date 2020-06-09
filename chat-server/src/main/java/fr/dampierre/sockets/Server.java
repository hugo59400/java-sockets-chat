package fr.dampierre.sockets;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public final class Server {

  private ServerSocket serverSocket;
  private final int PORT = 8999;
  private final int NB_CLIENTS_MAX = 100;
  private ClientThread[] clients = new ClientThread[NB_CLIENTS_MAX];
  private int nbClients = 0;

  private void demarrer() {

    try {
      serverSocket = new ServerSocket(PORT);
    } catch (Exception ex) {
      System.err.println("!!! Erreur lors de la création du socket serveur.");
      System.err.println("!!! Détails : " + ex.getMessage());
      System.exit(0);
    }

    System.out.println("### Écoute sur le port " + PORT);

    while (true) {
      try {
        Socket clientSocket = serverSocket.accept();
        System.out.println("### Connexion client depuis : " + clientSocket.getInetAddress());


        InputStream inStream = clientSocket.getInputStream();
        DataInputStream inDataStream = new DataInputStream(inStream);
        String pseudo = inDataStream.readUTF();

        ClientThread clientThread = new ClientThread(clientSocket, this, pseudo);

        enregistrerClient(clientThread);

        clientThread.start();

      } catch (Exception ex) {
        System.err.println("!!! Erreur Serveur.");
        System.err.println("!!! Détails : " + ex.getMessage());
      }
    }
  }

  private void enregistrerClient(ClientThread clientThread) {
    clients[nbClients] = clientThread;
    nbClients++;
  }

  public void broadcasterMessage(String msg) {
    System.out.println("### Broadcast du message : " + msg);
    for (ClientThread client : clients) {
      if (client != null) {
        client.transmettreMessage(msg);
      }
    }
  }

  public static void main(String[] args) {

    Server server = new Server();
    server.demarrer();
  }

}
