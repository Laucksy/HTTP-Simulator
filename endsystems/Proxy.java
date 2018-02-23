package endsystems;

import engines.ServerEngine;
import engines.HTTPEngine;
import layers.TransportLayer;

import extra.Resource;
import extra.ResourceManager;

public class Proxy extends ServerEngine {
  HTTPEngine http;
  ResourceManager manager;

  public Proxy () {
    super(TransportLayer.PROXY_LISTENING_PORT);

    http = new HTTPEngine();
    manager = new ResourceManager();
  }

  public void load (String version, String uri, byte[] byteArray) {

    Resource resource = manager.getCachedResource(uri, TransportLayer.WEB_LISTENING_PORT);
    HTTPEngine.HTTPResponse response = resource.getResponse();

    transportLayer.send(response.raw.getBytes());
  }
}