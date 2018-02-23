package extra;

import engines.HTTPEngine;
import java.util.ArrayList;

/**
 * ResourceManager - holds a queue of resources to request and dispatches
 * requests to load them
 *
 */

public class ResourceManager
{
    private HTTPEngine http;

    private ArrayList<Resource> resources = new ArrayList<Resource>();

    public ResourceManager() {
      this.http = new HTTPEngine();
    }

    public Resource loadUrl(String url) {
        // Make requests to load dependencies
        Resource resource = new Resource(this, url);
        this.resources.add(resource);
        resource.load();
        return resource;
    }

    public HTTPEngine httpClient() {
      return this.http;
    }
}
