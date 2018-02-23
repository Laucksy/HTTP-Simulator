package runners;

import endsystems.Server;
import layers.TransportLayer;
import helpers.Helper;

//This class represents the server application
public class ServerApp {
  public static void main(String[] args) throws Exception{
    Server  server = new Server();
    server.run();
  }
}
