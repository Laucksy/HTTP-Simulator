package extra;

import engines.HTTPEngine;
import runners.ClientApp;
import java.util.HashMap;

/**
 * ResourceManager - holds a queue of resources to request and dispatches
 * requests to load them
 *
 */

public class ResourceManager
{
    private HTTPEngine http;

    private HashMap<String, Resource> resources = new HashMap<String, Resource>();

    public ResourceManager() {
      this.http = new HTTPEngine();
    }

    private Resource loadUrl(String url, int port) {
        Resource resource = new Resource(this, url, port);
        if (ClientApp.DEBUG_MODE)
          System.out.println("Loading resource " + url);
        this.resources.put(url, resource);
        resource.load();
        if (ClientApp.DEBUG_MODE)
          System.out.println("Finished loading resource " + url);
        return resource;
    }

    public Resource getCachedResource(String url, int port) {
        long start = System.currentTimeMillis();
        if (this.resources.containsKey(url) /* && localCachingEnabled */) {
          if (ClientApp.DEBUG_MODE)
            System.out.println("Found locally cached resource " + url);
          Resource result = this.resources.get(url);
          long end = System.currentTimeMillis();
          if (ClientApp.DEBUG_MODE)
            System.out.println("Took : " + (end - start) + " millis");
          
          return result;
        } else {
          if (ClientApp.DEBUG_MODE)
            System.out.println("Did not find locally cached resource " + url);
          Resource result = this.loadUrl(url, port);
          long end = System.currentTimeMillis();
          if (ClientApp.DEBUG_MODE)
            System.out.println("Took : " + (end - start) + " millis");
          return result;
        }
    }

    public HTTPEngine httpClient() {
      return this.http;
    }
}
