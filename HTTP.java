import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class HTTP {
  Connection connection;

  public class HTTPResponse {
    public String data = "";
    public int status;
    public String statusText;
    public HashMap<String, String> headers;

    public HTTPResponse(String raw) {
      Scanner sc = new Scanner(raw);

      // Parse first line of the response
      try {
        /* HTTP Version */ sc.next();
        this.status = sc.nextInt();
        this.statusText = sc.nextLine();

        // Parse headers
        this.headers = new HashMap();
        while(sc.hasNextLine()) {
          String line = sc.nextLine();
          if (line.trim().equals("")) {
            break;
          }
          String varval[] = line.split(":");
          System.out.println(varval);
          this.headers.put(varval[0].trim(), varval[1].trim());
        }

        // Get the rest of the response as the data
        Pattern p = Pattern.compile("$", Pattern.MULTILINE);
        if (this.status != 404)
          this.data = sc.next(p);
      } catch (Exception e) {
        this.status = 0;
        this.statusText = e.toString();
        this.headers = new HashMap();
      }
    }

    public String toString() {
      String tostring = "Response : \n"
                        + "Status : " + this.status + "\n"
                        + "StatusText : " + this.statusText + "\n"
                        + "Headers : " + this.headers + "\n"
                        + "Data : " + this.data + "\n";
      return tostring;
    }
  }

  public HTTP() {
    connection = null;
  }

  public HTTPResponse get(String version, String uri, int port) {
    // System.out.println(version + "," + uri + "," + port);
    // System.out.println(connection != null ? connection.getPort() : "null");
    // if (connection != null) System.out.println(connection.getPort());
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
      System.out.println("Closing TCP Connection");
      connection.close();
      connection = null;
    }

    return response;
  }
}
