import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.*;

public class HTTP {
  Connection connection;

  public class HTTPResponse {
    public String data = "";
    public int status;
    public String statusText;
    public HashMap<String, String> headers;

    public HTTPResponse(String raw) {

      System.out.println("-------- Response: \n");
      System.out.println(raw);
      System.out.println("-------- End of the raw");

      Pattern resourcePattern = Pattern.compile(
        "HTTP/(\\d.\\d) (\\d{3}) ([a-zA-Z]+)\nConnection: (.+)\nContent-Length: (\\d+)\nContent-Type: ([a-z]+/[a-z]+)([\\S\\s]+)"
      );
      Matcher m = resourcePattern.matcher(raw);
      m.find();

      String version = m.group(1);
      String statusCode = m.group(2);
      String statusMessage = m.group(3);
      String connection = m.group(4);
      String contentLength = m.group(5);
      String contentType = m.group(6);
      String content = m.group(7);


      System.out.println(
        "version: " + version + "\n" + 
        "statusCode: " + statusCode + "\n" + 
        "statusMessage: " + statusMessage + "\n" +
        "connection: " + connection + "\n" + 
        "contentLength: " + contentLength + "\n" +
        "contentType: " + contentType + "\n" +
        "content: " + content + "---"
      );

      // Scanner sc = new Scanner(raw);

      // Parse first line of the response
      // try {
      //   /* HTTP Version */ sc.next();
      //   this.status = sc.nextInt();
      //   this.statusText = sc.nextLine();

      //   // Parse headers
      //   this.headers = new HashMap();
      //   while(sc.hasNextLine()) {
      //     String line = sc.nextLine();
      //     if (line.trim().equals("")) {
      //       break;
      //     }
      //     String varval[] = line.split(":");
      //     System.out.println(varval);
      //     this.headers.put(varval[0].trim(), varval[1].trim());
      //   }

      //   // Get the rest of the response as the data
      //   Pattern p = Pattern.compile("$", Pattern.MULTILINE);
      //   if (this.status != 404)
      //     this.data = sc.next(p);
      // } catch (Exception e) {
      //   this.status = 0;
      //   this.statusText = e.toString();
      //   this.headers = new HashMap();
      // }
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
