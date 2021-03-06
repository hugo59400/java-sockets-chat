package fr.dampierre.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientThread extends Thread {

  private String pseudo;
  private Server serveur;
  private DataInputStream inDataStream;
  private DataOutputStream outDataStream;

  public ClientThread(Socket clientSocket, Server serveur, String pseudo) {
    this.serveur = serveur;
    this.pseudo = pseudo;

    try {
      InputStream inStream = clientSocket.getInputStream();
      inDataStream = new DataInputStream(inStream);
      OutputStream outStream = clientSocket.getOutputStream();
      outDataStream = new DataOutputStream(outStream);

    } catch (IOException ex) {
      System.err.println("!!! Erreur thread client : ");
      System.err.println("!!! Détails : " + ex.getMessage());
    }
  }

  @Override
  public void run() {
    System.out.println("### Démarrage du thread client : " + pseudo);

    try {
      while (true) {
        String msg = inDataStream.readUTF();
        msg = pseudo + " >> " + msg;
        serveur.broadcasterMessage(msg);
      }
    } catch (Exception e) {
      System.out.println("### Déconnexion client.");
    }

  }

  public void transmettreMessage(String msg) {
    try {
      outDataStream.writeUTF(msg);
    } catch (Exception e) {
      System.out.println("### Erreur transmission du message.");
    }
  }
}
