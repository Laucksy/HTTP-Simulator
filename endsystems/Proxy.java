package endsystems;

import engines.ServerEngine;
import engines.HTTPEngine;
import layers.TransportLayer;

public class Proxy extends ServerEngine {
  HTTPEngine http;

  public Proxy () {
    super(TransportLayer.PROXY_LISTENING_PORT);

    http = new HTTPEngine();
  }

  public void load (String version, String uri, byte[] byteArray) {
    HTTPEngine.HTTPResponse response = http.get(version, uri, TransportLayer.WEB_LISTENING_PORT);
    byteArray = response.raw.getBytes();

    transportLayer.send(byteArray);
  }
}