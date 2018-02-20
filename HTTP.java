public class HTTP {
  Connection connection;

  public HTTP() {
    connection = null;
  }

  public String get(String version, String uri, int port) {
    System.out.println(connection == null);
    if (connection != null) System.out.println(connection.getPort());
    if (connection == null || connection.getPort() != port) {
      connection = new Connection(port);
    }

    String request = "GET " + uri + " HTTP/" + version + "\n";
    request += "Host: localhost:" + port + "\n";
    request += "Connection: " + (version.equals("1.1") ? "keep-alive" : "close") + "\n";

    connection.send(request);
    String response = connection.receive();

    if (version != "1.1") {
      connection.close();
      connection = null;
    }

    return response;
  }
}
