package fr.dampierre.sockets;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class EnvoiThread extends Thread {

  private DataOutputStream outDataStream;

  public EnvoiThread(Socket socket) {

    try {
      OutputStream outStream = socket.getOutputStream();
      outDataStream = new DataOutputStream(outStream);
    } catch (IOException ex) {
      System.err.println("!!! Erreur thread client : ");
      System.err.println("!!! Détails : " + ex.getMessage());
    }
  }

  @Override
  public void run() {
    System.err.println("### Thread envoi démarré");

    Scanner clavier = new Scanner(System.in);

    while (true) {
      String msg = clavier.nextLine();
      envoyerMessage(msg);
    }

  }

  private void envoyerMessage(String msg) {
    try {
      outDataStream.writeUTF(msg);
    } catch (Exception e) {
      System.out.println("### Echec d'envoi.");
    }
  }
}
