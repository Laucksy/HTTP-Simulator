import java.util.ArrayList;
import java.sql.Timestamp;

public class Resource
{
    public enum Type
    {
        CLML, IMG, OTHER;
    }

    protected ResourceManager resourceManager;
    protected Request request;
    protected String file;
    protected boolean loaded;
    protected Timestamp requestedDate;
    protected Timestamp loadedDate;
    protected HTTP.HTTPResponse response;

    public Resource(ResourceManager resourceManager, Request request) {
      this.request = request;
      this.resourceManager = resourceManager;
      this.requestedDate = new Timestamp(System.currentTimeMillis());
    }

    public ArrayList<Request> getDependencies() {
        switch (this.request.type) {
          case CLML:
            // use CLMLParser
            // return this.
        }
        return new ArrayList();
    }

    public void load() {
      this.response = this.resourceManager.httpClient()
                        .get(
                            /* http version */ "1.1",
                            /* url */ this.request.getUrl(),
                            /* port */ TransportLayer.PROXY_LISTENING_PORT
                        );
      // http error handling
      this.loadedDate = new Timestamp(System.currentTimeMillis());
      this.loaded = true;
      ArrayList<Request> dependencies = this.getDependencies();
      for (Request dependencyRequest: dependencies) {
        resourceManager.loadUrl(dependencyRequest);
      }
    }

    public String toString() {
      return this.response.toString();
    }
}
