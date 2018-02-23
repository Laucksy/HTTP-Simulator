package extra;

import engines.CLHTEngine;
import engines.HTTPEngine;
import layers.Browser;
import layers.TransportLayer;

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

    private CLHTEngine clhtEngine;

    public Resource(ResourceManager resourceManager, String url) {
      this.url = url;
      this.resourceManager = resourceManager;
      this.requestedDate = new Timestamp(System.currentTimeMillis());
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
        this.clhtEngine = new CLHTEngine(this.resourceManager, this.response.data);
    }

    public String findLink(int id) {
      if (this.type.equals("text/clht")
          && this.clhtEngine != null
          && this.clhtEngine.findLink(id) != null)
        return this.clhtEngine.findLink(id).url;
      else
        return "home";
    }

    public String toString() {
      if (this.type.equals("text/clht"))
        return this.clhtEngine.render();
      else
        return this.response.data;
    }

    public HTTPEngine.HTTPResponse getResponse() {
      return this.response;
    }
}
