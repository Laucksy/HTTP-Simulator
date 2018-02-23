package endsystems;

import engines.ServerEngine;
import engines.HTTPEngine;
import layers.TransportLayer;
import extra.Resource;
import extra.ResourceManager;
import helpers.Helper;

import java.util.regex.*;

/*
  Proxy - Endsystem for the proxy implementation. Implements the ServerEngine
  class and creates a specialized version of it, containing an added caching
  stage

*/

public class Proxy extends ServerEngine {
  ResourceManager manager;

  /*
    Constructor - Creates the proxy server
    @param trans Transmission delay
    @param prop Propagation delay
  */
  public Proxy (int trans, int prop) {
    super(TransportLayer.PROXY_LISTENING_PORT, trans, prop);

    manager = new ResourceManager(trans, prop);
  }

  /*
    Method load (@Override) - Manages the proxy's cache (implemented through the 
    ResourceManager abstraction), fetches responses from the server and
    transmits the requested files to the client.
  */
  @Override
  public void load (String version, String uri, String raw) {

    Resource resource = manager.getCachedResource(uri, TransportLayer.WEB_LISTENING_PORT);
    HTTPEngine.HTTPResponse response = resource.getResponse();

    Pattern resourcePattern = Pattern.compile("If-Modified-Since: (\\d+)");
    Matcher m = resourcePattern.matcher(raw);

    long ifModified = -1;
    while (m.find()) ifModified = Long.parseLong(m.group(1));

    String type = Helper.instance().type("website_example/" + uri);
    String tmp = "";

    if (resource.loadedDate < ifModified) {
      tmp = createResponse(
          response.version,
          304,
          type,
          resource.file,
          uri,
          resource.loadedDate
      );
    } else {
      tmp = createResponse(
          response.version,
          response.status == 304 ? 200 : response.status,
          type,
          resource.file,
          uri,
          resource.loadedDate
      );
    }
    http.send(tmp, this.port);
  }
}
