package endsystems;

import engines.ServerEngine;
import engines.HTTPEngine;
import layers.TransportLayer;
import extra.Resource;
import extra.ResourceManager;
import helpers.Helper;

import java.util.regex.*;

public class Proxy extends ServerEngine {
  ResourceManager manager;

  public Proxy () {
    super(TransportLayer.PROXY_LISTENING_PORT);

    manager = new ResourceManager();
  }

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
