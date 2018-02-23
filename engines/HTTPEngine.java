package engines;

import extra.Connection;
import runners.ClientApp;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.*;

public class HTTPEngine {
  Connection connection;
  private int TRANSMISSION_DELAY_RATE;
  private int PROPAGATION_DELAY;

  public class HTTPResponse {
    public String raw;
    public String version;
    public String data = "";
    public int status;
    public String statusText;
    public HashMap<String, String> headers;

    public HTTPResponse(String raw) {
      this.raw = raw;

      headers = new HashMap<>();
      String tmp = "";
      Pattern general = Pattern.compile("HTTP/(\\d.\\d) (\\d{3}) ([a-zA-Z ]+)\\n((.+:.+\\s*)+)\\n([\\S\\s]+)");
      Pattern header = Pattern.compile("((.+):\\s*(.+)\\s*)");

      Matcher m = general.matcher(raw);
      m.find();

      version = m.group(1);
      status = Integer.parseInt(m.group(2));
      statusText = m.group(3);
      tmp = m.group(4);
      data = m.group(6);

      m = header.matcher(tmp);
      while (m.find()) {
        headers.put(m.group(2), m.group(3));
      }

    }

    public String getHeader(String header) {
      return headers.get(header) == null ? "" : headers.get(header);
    }

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

  public HTTPEngine(int trans, int prop) {
    connection = null;
    TRANSMISSION_DELAY_RATE = trans;
    PROPAGATION_DELAY = prop;
  }

  public HTTPResponse get(String version, String uri, int port, long loadedDate) {
    if (connection == null || connection.getPort() != port || !connection.isClient()) {
      connection = new Connection(/* isServer */ false, port, TRANSMISSION_DELAY_RATE, PROPAGATION_DELAY);
      connection.connect();
    }

    String request = "GET " + uri + " HTTP/" + version + "\n";
    request += "Host: localhost:" + port + "\n";
    request += "Connection: " + (version.equals("1.1") ? "keep-alive" : "close") + "\n";

    if (loadedDate != -1)
      request += "If-Modified-Since: " + loadedDate + "\n";

    connection.send(request);
    HTTPResponse response = new HTTPResponse(connection.receive());


    if (!version.equals("1.1")) {
      connection.close();
      connection = null;
    }

    return response;
  }

  public String listen(int port) {
    if (connection == null || connection.getPort() != port || !connection.isServer()) {
      connection = new Connection(/* isServer */ true, port, TRANSMISSION_DELAY_RATE, PROPAGATION_DELAY);
      connection.connect();
    }
    return connection.receive();
  }

  public void send(String data, int port) {
    if (connection == null || connection.getPort() != port || !connection.isServer()) {
      connection = new Connection(/* isServer */ true, port, TRANSMISSION_DELAY_RATE, PROPAGATION_DELAY);
      connection.connect();
    }
    connection.send(data);
  }
}
