import java.util.HashMap;
import java.util.Deque;
import java.util.ArrayList;
import java.uril.Date.*;

enum ResourceType
{
    CLML, IMG, OTHER;
}

/**
 * ResourceManager - holds a queue of resources to request and dispatches
 * requests to load them
 *
 */

public class ResourceManager
{
    private class Request
    {
        private String uri;
        private ResourceType type;
    }
    private class Resource
    {
        private Request request;
        private boolean requested;
        private boolean loaded;
        private Timestamp requestedDate;
        private Timestamp loadedDate;
        
        private ArrayList<Request> getDependencies() {
            return new ArrayList();
        }
    }
    
    private class CLMLResource extends Resource
    {
        // Overwrite methods
    }
    
    private class IMGResource extends Resource
    {
        // Overwrite methods
    }
    
    private ArrayList<Resource> resources = new ArrayList<Resource>();    
    private Deque<Request> requests = new ArrayDeque<Request>();

    public ResourceManager()
    {
    }

    public void loadResource(String uri, ResourceType type)
    {
        Resource result = new Resource();
        // Make requests to load dependencies
        requests.addAll(result.getDependencies());
    }
}
