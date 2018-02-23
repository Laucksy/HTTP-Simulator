package endsystems;

import engines.ServerEngine;
import layers.TransportLayer;
import helpers.Helper;

import java.util.regex.*;
import java.io.File;

public class Server extends ServerEngine {
  public Server (int trans, int prop) {
    super(TransportLayer.WEB_LISTENING_PORT, trans, prop);
  }

  public void load (String version, String uri, String raw) {
    Pattern resourcePattern = Pattern.compile("If-Modified-Since: (\\d+)");
    Matcher m = resourcePattern.matcher(raw);

    long ifModified = -1;
    while (m.find()) ifModified = Long.parseLong(m.group(1));

    String fileData = Helper.instance().read("website_example/" + uri);
    String type = Helper.instance().type("website_example/" + uri);

    int code = fileData.equals("Not Found") ? 404 : 200;
    String data = fileData.equals("Not Found") ? "" : fileData;

    File file = new File("website_example/" + uri);
    long lastModified = file.lastModified();
    if (ifModified > lastModified) code = 304;

    String response = createResponse(version, code, type, data, uri, lastModified);
    http.send(response, this.port);
  }
}
