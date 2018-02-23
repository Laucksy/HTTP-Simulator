package engines;

import engines.HTTPEngine;
import helpers.Helper;

import java.util.concurrent.TimeUnit;
import java.io.File;

public abstract class ServerEngine {
  protected HTTPEngine http;
  protected int port;

  public ServerEngine (int port, int trans, int prop) {
    this.http = new HTTPEngine(trans, prop);
    this.port = port;
  }

  public void run () throws Exception {
    while (true) {
      String str = http.listen(this.port);

      if (str == null || str.equals("")) {
        TimeUnit.MILLISECONDS.sleep(250);
        continue;
      }

      int index = str.indexOf("HTTP/") + 5;
      String version = str.substring(index, index + 3);
      String uri = str.substring(str.indexOf(" ") + 1, index - 6);

      load(version, uri, str);
    }
  }

  public abstract void load (String version, String uri, String raw);

  public String createResponse(String version, int code, String type, String data, String uri, long lastModified) {

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
