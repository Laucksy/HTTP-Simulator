import java.util.HashMap;
import java.util.Deque;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.sql.Timestamp;

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
        
        private String response() {
            return "";
        }
    }
    
    private class Resource
    {
        protected Request request;
        protected boolean requested;
        protected boolean loaded;
        protected Timestamp requestedDate;
        protected Timestamp loadedDate;
        
        public ArrayList<Request> getDependencies() {
            return new ArrayList();
        }
    }
    
    private class CLMLResource extends Resource
    {
        // Overwrite methods
        @Override
        public ArrayList<Request> getDependencies() {
            
            return new ArrayList();
        }
    }
    
    private class IMGResource extends Resource
    {
        // Overwrite methods
        @Override
        public ArrayList<Request> getDependencies() {
            return new ArrayList();
        }
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
