package engines;

import engines.HTTPEngine;
import helpers.Helper;

import java.util.concurrent.TimeUnit;
import java.io.File;

/**

  ServerEngine - Abstract class that models a generic server (can be used as
  both a proxy server and a web server). Provides methods for data transfer
  through the HTTPEngine

*/

public abstract class ServerEngine {
  protected HTTPEngine http;
  protected int port;

  /*
    Constructor - Creates a new instance of a ServerEngine

    @param port Port the server will be listening to
    @param trans Transmission delay
    @param prop Propagation delay
  */
  public ServerEngine (int port, int trans, int prop) {
    this.http = new HTTPEngine(trans, prop);
    this.port = port;
  }

  /*
    run - Server's main routine: Loops until a request is received, in which
    case the request is processed and the requested file is sent back.
  */
  public void run () throws Exception {
    // Loop continuously
    while (true) {
      // Create a new connection if it doesn't exist and listen to the
      // specified port
      String str = http.listen(this.port);

      // Sleep for 250 millisecons if a request was not received
      if (str == null || str.equals("")) {
        TimeUnit.MILLISECONDS.sleep(250);
        continue;
      }

      // Parse the received request
      int index = str.indexOf("HTTP/") + 5;
      String version = str.substring(index, index + 3);
      String uri = str.substring(str.indexOf(" ") + 1, index - 6);

      // Load the requested file
      load(version, uri, str);
    }
  }

  /*
    load - abstract method for loading files from the network. Can be overridden
    to add caching and error handling

    @param version HTTP version
    @param uri location of the requested object
    @param raw the raw response from the server

  */
  public abstract void load (String version, String uri, String raw);

  /*
    createResponse - formulates an HTTP response based on the given parameters

    @param version HTTP version
    @param code status code (status message will be infered from this)
    @param type MIME type of the object
    @param data raw data from the object (String)
    @param uri location of the requested object
    @param lastModified sets the last modified HTTP header to this value

  */
  public String createResponse(
    String version, int code, String type,
    String data, String uri, long lastModified
  ) {

    String msg = "Ok";
    if (code == 404) msg = "Not Found";
    if (code == 304) msg = "Not Modified";

    String response = "HTTP/" + version + " " + code + " " + msg + "\n";
    response += "Connection: " + (version.equals("1.1") ? "keep-alive" : "close") + "\n";
    response += "Date: " + System.currentTimeMillis() + "\n";
    response += "Last-Modified: " + lastModified + "\n";
    response += "Content-Length: " + data.length() + "\n";
    response += "Content-Type: " + type + "\n";
    response += "\n";

    if (code != 304)
      response += data;

    return response;
  }
}
