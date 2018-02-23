package runners;

import endsystems.Client;

/*
  ClientApp - Runs the client app and passes the running mode arguments to it

*/

public class ClientApp {
  public static boolean DEBUG_MODE = false;
  public static boolean EXPERIMENT_MODE = false;

  public static void main(String[] args) {
    if (args.length > 0 && args[0].equals("debug")) {
      DEBUG_MODE = true;
    }
      else if (args.length > 0 && args[0].equals("experiment"))
    {
      EXPERIMENT_MODE = true;
    }

    // Create a new client instance
    Client client = new Client();
    client.browse();
  }
}
