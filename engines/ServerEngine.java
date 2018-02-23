package engines;

import layers.TransportLayer;
import helpers.Helper;

import java.util.concurrent.TimeUnit;
import java.io.File;

public abstract class ServerEngine {
  protected TransportLayer transportLayer;
  protected int port;

  public ServerEngine (int port) {
    transportLayer = new TransportLayer(true, port);
    this.port = port;
  }

  public void run () throws Exception {
    while (true) {
      byte[] byteArray = transportLayer.receive();

      if (byteArray == null) {
        TimeUnit.MILLISECONDS.sleep(250);
        continue;
      }

      String str = new String (byteArray);

      int index = str.indexOf("HTTP/") + 5;
      String version = str.substring(index, index + 3);
      String uri = str.substring(str.indexOf(" ") + 1, index - 6);

      load(version, uri, byteArray);
    }
  }

  public abstract void load (String version, String uri, byte[] byteArray);

  public String createResponse(String version, int code, String type, String data, String uri) {
    File file = new File("website_example/" + uri);

    System.out.println("website_example/" + uri);
    System.out.println(file.lastModified());

    String msg = "Ok";
    if (code == 404) msg = "Not Found";
    if (code == 304) msg = "Not Modified";

    String response = "HTTP/" + version + " " + code + " " + msg + "\n";
    response += "Connection: " + (version.equals("1.1") ? "keep-alive" : "close") + "\n";
    response += "Date: " + System.currentTimeMillis() + "\n";
    response += "Last-Modified: " + file.lastModified() + "\n"; 
    response += "Content-Length: " + data.length() + "\n";
    response += "Content-Type: " + type + "\n";
    response += "\n";
    response += data;

    return response;
  }
}