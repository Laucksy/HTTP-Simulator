package endsystems;

import engines.ServerEngine;
import layers.TransportLayer;
import helpers.Helper;

public class Server extends ServerEngine {
  public Server () {
    super(TransportLayer.WEB_LISTENING_PORT);
  }

  public void load (String version, String uri, byte[] byteArray) {
    String fileData = Helper.instance().read("website_example/" + uri);
    String type = Helper.instance().type("website_example/" + uri);
    
    int code = fileData.equals("Not Found") ? 404 : 200;
    String data = fileData.equals("Not Found") ? "" : fileData;

    String response = createResponse(version, code, type, data, uri);
    byteArray = response.getBytes();
    transportLayer.send(byteArray);
  }
}