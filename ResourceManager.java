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
      http = new HTTP();
    }

    public Resource loadUrl(Request request) {
        // Make requests to load dependencies
        Resource resource = new Resource(this, request);
        this.resources.add(resource);
        resource.load();
        return resource;
    }
}
