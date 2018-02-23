package extra;

import engines.HTTPEngine;
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

    private Resource loadUrl(String url) {
        Resource resource = new Resource(this, url);
        if (/* debugging mode */false)
          System.out.println("Loading resource " + url);
        this.resources.put(url, resource);
        resource.load();
        if (/* debugging mode */false)
          System.out.println("Finished loading resource " + url);
        return resource;
    }

    public Resource getCachedResource(String url) {
        if (this.resources.containsKey(url) /* && localCachingEnabled */) {
          if (/* debugging mode */ false)
            System.out.println("Found locally cached resource " + url);
          return this.resources.get(url);
        } else {
          if (/* debugging mode */ false)
            System.out.println("Did not find locally cached resource " + url);
          return this.loadUrl(url);
        }
    }

    public HTTPEngine httpClient() {
      return this.http;
    }
}
