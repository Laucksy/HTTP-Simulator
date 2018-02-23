package extra;

import layers.TransportLayer;
import helpers.Helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Connection {
  private TransportLayer transportLayer;
  private int port;
  private boolean isServer;

  public Connection(boolean isServer, int p, int trans, int prop) {
    this.port = p;
    this.transportLayer = new TransportLayer(isServer, port, trans, prop);
    this.isServer = isServer;
  }

  public int getPort() {
    return port;
  }

  public void close() {
    transportLayer.close();
  }

  public void connect () {
    transportLayer.connect();
  }

  public boolean isServer () {
    return this.isServer;
  }

  public boolean isClient () {
    return !this.isServer;
  }

  public void send(String message) {
    byte[] byteArray = message.getBytes();
    transportLayer.send(byteArray);
  }

  public String receive() {
    byte[] byteArray = transportLayer.receive();

    String str = new String(byteArray);
    return str;
  }
}
