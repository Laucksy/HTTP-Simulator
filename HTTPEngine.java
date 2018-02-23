import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.*;

public class HTTPEngine {
  Connection connection;

  public class HTTPResponse {
    public String res;
    public String version;
    public String data = "";
    public int status;
    public String statusText;
    public HashMap<String, String> headers;

    public HTTPResponse(String raw) {
      res = raw;

      headers = new HashMap<>();
      String tmp = "";
      Pattern general = Pattern.compile("HTTP/(\\d.\\d) (\\d{3}) ([a-zA-Z ]+)\\n((.+:.+\\s*)+)([\\S\\s]+)");
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
      // String tostring = "Response : \n"
      //                   + "Status : " + this.status + "\n"
      //                   + "StatusText : " + this.statusText + "\n"
      //                   + "Headers : " + this.headers + "\n"
      //                   + "Data : " + this.data + "\n";
      return this.data;
    }
  }

  public HTTPEngine() {
    connection = null;
  }

  public HTTPResponse get(String version, String uri, int port) {
    if (connection == null || connection.getPort() != port) {
      connection = new Connection(port);
      connection.connect();
    }

    String request = "GET " + uri + " HTTP/" + version + "\n";
    request += "Host: localhost:" + port + "\n";
    request += "Connection: " + (version.equals("1.1") ? "keep-alive" : "close") + "\n";

    connection.send(request);
    HTTPResponse response = new HTTPResponse(connection.receive());

    if (!version.equals("1.1")) {
      connection.close();
      connection = null;
    }

    return response;
  }
}
