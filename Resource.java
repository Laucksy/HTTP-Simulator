import java.util.ArrayList;
import java.sql.Timestamp;

public class Resource
{
    protected ResourceManager resourceManager;
    protected String url;
    protected String type;
    protected String file;
    protected boolean loaded;
    protected Timestamp requestedDate;
    protected Timestamp loadedDate;
    protected HTTPEngine.HTTPResponse response;
    protected ArrayList<String> dependencies;

    private CLHTEngine clhtEngine;

    public Resource(ResourceManager resourceManager, String url) {
      this.url = url;
      this.resourceManager = resourceManager;
      this.requestedDate = new Timestamp(System.currentTimeMillis());
    }

    public ArrayList<String> getDependencies() {
        switch (this.type) {
          case "text/clht":
            ArrayList<String> dependencies = new ArrayList<String>();
            for (String image : this.clhtEngine.images) {
              dependencies.add(image);
            }
          default:
            return new ArrayList<String>();
        }
    }

    public void load() {
      this.response = this.resourceManager.httpClient()
                        .get(
                            /* http version */ "1.1",
                            /* url */ this.url,
                            /* port */ TransportLayer.PROXY_LISTENING_PORT
                        );

      this.loadedDate = new Timestamp(System.currentTimeMillis());
      this.loaded = true;

      this.type = this.response.getHeader("Content-Type");
      
      if (this.type.equals("text/clht"))
        this.clhtEngine = new CLHTEngine(this.response.data);

      this.dependencies = this.getDependencies();
      for (String dependency: dependencies) {
        resourceManager.loadUrl(dependency);
      }
    }

    public String toString() {
      return this.render();
    }

    public HTTPEngine.HTTPResponse getResponse() {
      return this.response;
    }

    public String render() {
      return this.clhtEngine.render();
    }
}
