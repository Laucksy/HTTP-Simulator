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

    // Pattern resourcePattern = Pattern.compile("If-Modified-Since: (\\d+)");
    // Matcher m = resourcePattern.matcher(new String(byteArray));


    // TODO check if resource loaded date is more then m.group(1); if sosend new data otherwsise 304

    // long ifModified = -1;
    // while (m.find()) ifModified = Long.parseLong(m.group(1));

    // String fileData = Helper.instance().read("website_example/" + uri);
    // String type = Helper.instance().type("website_example/" + uri);
    
    // int code = fileData.equals("Not Found") ? 404 : 200;
    // String data = fileData.equals("Not Found") ? "" : fileData;

    // String response = createResponse(version, code, type, data, uri, ifModified);
    // byteArray = response.getBytes();
    // transportLayer.send(byteArray);

    transportLayer.send(response.raw.getBytes());
  }
}