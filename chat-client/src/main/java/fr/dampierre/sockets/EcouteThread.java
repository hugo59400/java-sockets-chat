package fr.dampierre.sockets;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class EcouteThread extends Thread {

  private DataInputStream inDataStream;

  public EcouteThread(Socket socket) {

    try {
      InputStream inStream = socket.getInputStream();
      inDataStream = new DataInputStream(inStream);
    } catch (IOException ex) {
      System.err.println("!!! Erreur thread client : ");
      System.err.println("!!! Détails : " + ex.getMessage());
    }
  }

  @Override
  public void run() {
    System.err.println("### Thread écoute démarré");

    try {
      while (true) {
        String msg = inDataStream.readUTF();
        traiterMessage(msg);
      }
    } catch (IOException ex) {
      System.err.println("!!! Erreur thread ecoute");
      System.err.println("!!! Détails : " + ex.getMessage());
    }

  }

  private void traiterMessage(String msg) {
    System.out.println(msg);
  }
}
