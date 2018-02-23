package endsystems;

import engines.ServerEngine;
import engines.HTTPEngine;
import layers.TransportLayer;
import extra.Resource;
import extra.ResourceManager;
import helpers.Helper;

import java.util.regex.*;

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

    Pattern resourcePattern = Pattern.compile("If-Modified-Since: (\\d+)");
    Matcher m = resourcePattern.matcher(new String(byteArray));



    long ifModified = -1;
    while (m.find()) ifModified = Long.parseLong(m.group(1));

    String type = Helper.instance().type("website_example/" + uri);
    String tmp = "";

    if (resource.loadedDate < ifModified) {
      tmp = createResponse( response.version,  304, type, resource.file, uri, resource.loadedDate);
    } else {
      tmp = createResponse( response.version,  200, type, resource.file, uri, resource.loadedDate);
    }
    transportLayer.send(tmp.getBytes());
  }
}