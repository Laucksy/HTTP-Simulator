package engines;

import extra.Connection;
import runners.ClientApp;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.*;

/**

  HTTPEngine - This class provides http functionality, implements get method
  and generates request headers, as well as parses those headers and keeps 
  them in HashMap (header title as a key and header value as a value)
  Keeps information about http response, also keeps informationa bout
  transmission and propagation delays

*/
public class HTTPEngine {
  Connection connection;
  private int TRANSMISSION_DELAY_RATE;
  private int PROPAGATION_DELAY;

  /**

  HTTPresponse - keeps information about headers, http version, provided data 
  and the status

  */
  public class HTTPResponse {
    public String raw;
    public String version;
    public String data = "";
    public int status;
    public String statusText;
    public HashMap<String, String> headers;

    /*
      Constructor - Creates a new instance of a HTTPResponse, parses headers and keeps the list

      @param raw Raw data sent in the response
    */
    public HTTPResponse(String raw) {
      this.raw = raw;

      headers = new HashMap<>();
      String tmp = "";

      // Parse headers
      Pattern general = Pattern.compile("HTTP/(\\d.\\d) (\\d{3}) ([a-zA-Z ]+)\\n((.+:.+\\s*)+)\\n([\\S\\s]+)");
      Pattern header = Pattern.compile("((.+):\\s*(.+)\\s*)");

      Matcher m = general.matcher(raw);
      m.find();

      version = m.group(1);
      status = Integer.parseInt(m.group(2));
      statusText = m.group(3);
      tmp = m.group(4);
      data = m.group(6);

      // Store the headers
      m = header.matcher(tmp);
      while (m.find()) {
        headers.put(m.group(2), m.group(3));
      }

    }

    /**
     * Get the header value based on the provided header title
     * 
     * @params header The header title 
     * @return String
     */
    public String getHeader(String header) {
      return headers.get(header) == null ? "" : headers.get(header);
    }

    /**
     * String representation of the HTTPResponse class
     * @return String
     */
    public String toString() {
      if (ClientApp.DEBUG_MODE)
        return  "Raw response: \n"
                + this.raw
                + "\n"
                + "Parsed Response : \n"
                + "Status : " + this.status + "\n"
                + "StatusText : " + this.statusText + "\n"
                + "Headers : " + this.headers + "\n"
                + "Data : - masked -\n";
      else
        return this.data;
    }
  }

  /**
   * Contructor for HTTPEngine
   * 
   * @param trans - transmission delay
   * @param prop - propagation delay
   */
  public HTTPEngine(int trans, int prop) {
    connection = null;
    TRANSMISSION_DELAY_RATE = trans;
    PROPAGATION_DELAY = prop;
  }

  /**
   * Http Get Method , get source based on the provided uri and port 
   * 
   * @param version - http version
   * @param uri - uri for the file
   * @param loadedDate - the time when the source was loaded last time
   * 
   * @return HTTPResponse
   */
  public HTTPResponse get(String version, String uri, int port, long loadedDate) {
    if (connection == null || connection.getPort() != port || !connection.isClient()) {
      connection = new Connection(/* isServer */ false, port, TRANSMISSION_DELAY_RATE, PROPAGATION_DELAY);
      connection.connect();
    }

    // build the request
    String request = "GET " + uri + " HTTP/" + version + "\n";
    request += "Host: localhost:" + port + "\n";
    request += "Connection: " + (version.equals("1.1") ? "keep-alive" : "close") + "\n";

    if (loadedDate != -1)
      request += "If-Modified-Since: " + loadedDate + "\n";
    
    // send request, this goes to transport layer
    connection.send(request);
    HTTPResponse response = new HTTPResponse(connection.receive());

    // check the http version and close connection if it should be not persistent
    if (!version.equals("1.1")) {
      connection.close();
      connection = null;
    }

    return response;
  }

  /**
   * listen to provided port, set up new Connection
   * 
   * @param port - int
   * @return String - received packet
   */
  public String listen(int port) {
    if (connection == null || connection.getPort() != port || !connection.isServer()) {
      connection = new Connection(/* isServer */ true, port, TRANSMISSION_DELAY_RATE, PROPAGATION_DELAY);
      connection.connect();
    }
    return connection.receive();
  }

  /**
   * send data to the server
   * @param port - int
   * @param data - String
   * 
   */
  public void send(String data, int port) {
    if (connection == null || connection.getPort() != port || !connection.isServer()) {
      connection = new Connection(/* isServer */ true, port, TRANSMISSION_DELAY_RATE, PROPAGATION_DELAY);
      connection.connect();
    }
    connection.send(data);
  }
}
