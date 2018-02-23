package extra;

import engines.HTTPEngine;
import runners.ClientApp;
import java.util.HashMap;

/**
 * ResourceManager - holds a cache of loaded resources and all the information
 * about them (url, data, request, etc.). Serves as the cache manager for both
 * the proxy server and the client app
 *
 */

public class ResourceManager
{
    private HTTPEngine http;

    private HashMap<String, Resource> resources = new HashMap<String, Resource>();

    public ResourceManager(int trans, int props) {
      this.http = new HTTPEngine(trans, props);
    }

    /*
      loadURl - Loads a resource from the remote server

      @param url the location of the object
      @param port the port of the remote server
    */
    private Resource loadUrl(String url, int port) {
        Resource resource = new Resource(this, url, port);
        if (ClientApp.DEBUG_MODE)
          System.out.println("Loading resource " + url);
        this.resources.put(url, resource);
        resource.load();
        // We don't cache files that 404
        if (resource.getResponse().status == 404)
          this.resources.remove(url);
        if (ClientApp.DEBUG_MODE)
          System.out.println("Finished loading resource " + url);
        return resource;
    }

    public Resource getCachedResource(String url, int port) {
        long start = System.currentTimeMillis();

        // Check if the requested resource exists in the cache

        if (this.resources.containsKey(url) /* && localCachingEnabled */) {

          // If it does exist, then we request the resource from the server to
          // see if it was modified

          if (ClientApp.DEBUG_MODE)
            System.out.println("Found locally cached resource " + url);
          Resource result = this.resources.get(url);

          HTTPEngine.HTTPResponse res =  http.get("1.1", url, port, result.loadedDate);

          // If the server return 304, then the file already exists in the cache
          // so we don't overwrite it
          if (res.status != 304) {
            result.loadedDate = System.currentTimeMillis();
            result.file = res.data;
          }

          result.response = res;

          // Print out how long it took to process the request
          long end = System.currentTimeMillis();
          if (ClientApp.DEBUG_MODE)
            System.out.println("Took : " + (end - start) + " millis");

          return result;
        } else {

          // If it doesn't then we request it from the server for the first time

          if (ClientApp.DEBUG_MODE)
            System.out.println("Did not find locally cached resource " + url);

          Resource result = this.loadUrl(url, port);

          // Print out how long it took to process the request
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
