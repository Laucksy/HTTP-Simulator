package runners;

import engines.ServerEngine;
import layers.TransportLayer;
import helpers.Helper;

//This class represents the server application
public class ServerApp {
  public static void main(String[] args) throws Exception{
    ServerEngine  server = new ServerEngine();
    server.run();
  }
}
