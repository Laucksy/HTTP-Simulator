import java.util.ArrayList;

/**
 * ResourceManager - holds a queue of resources to request and dispatches
 * requests to load them
 *
 */

public class ResourceManager
{
    private HTTP http;

    private ArrayList<Resource> resources = new ArrayList<Resource>();

    public ResourceManager() {
      this.http = new HTTP();
    }

    public Resource loadUrl(String url) {
        // Make requests to load dependencies
        Resource resource = new Resource(this, url);
        this.resources.add(resource);
        resource.load();
        return resource;
    }

    public HTTP httpClient() {
      return this.http;
    }
}
