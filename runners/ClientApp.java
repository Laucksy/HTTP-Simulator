package runners;

import endsystems.Client;

//This class represents the client application
public class ClientApp {
  public static boolean DEBUG_MODE = false;

  public static void main(String[] args) {
    if (args.length > 0 && args[0].equals("debug"))
    {
      DEBUG_MODE = true;
    }
    Client client = new Client();
    client.browse();
  }
}
