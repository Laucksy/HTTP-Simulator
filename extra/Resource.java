package extra;

import engines.CLHTEngine;
import engines.HTTPEngine;
import layers.Browser;
import layers.TransportLayer;
import runners.ClientApp;

import java.util.ArrayList;
import java.sql.Timestamp;

public class Resource
{
    protected ResourceManager resourceManager;
    protected String url;
    protected String type;
    protected String file;
    protected boolean loaded;
    protected int port;
    protected Timestamp requestedDate;
    protected Timestamp loadedDate;
    protected HTTPEngine.HTTPResponse response;

    private CLHTEngine clhtEngine;

    public Resource(ResourceManager resourceManager, String url, int port) {
      this.url = url;
      this.resourceManager = resourceManager;
      this.requestedDate = new Timestamp(System.currentTimeMillis());
      this.port = port;
    }

    public void load() {
      this.response = this.resourceManager.httpClient()
                        .get(
                            /* http version */ "1.1",
                            /* url */ this.url,
                            /* port */ this.port
                        );

      this.loadedDate = new Timestamp(System.currentTimeMillis());
      this.loaded = true;

      this.type = this.response.getHeader("Content-Type");

      if (this.type.equals("text/clht"))
        this.clhtEngine = new CLHTEngine(this.resourceManager, this.response.data, this.port);
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
      if (this.type.equals("text/clht")) {
        if (ClientApp.DEBUG_MODE)
          System.out.println(this.response.toString());
        return this.clhtEngine.render();
      } else
        return ClientApp.DEBUG_MODE ? this.response.toString() : this.response.data;
    }

    public HTTPEngine.HTTPResponse getResponse() {
      return this.response;
    }
}
