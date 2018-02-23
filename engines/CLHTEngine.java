package engines;

import extra.ResourceManager;
import extra.Resource;
import runners.ClientApp;
import layers.Browser;
import layers.TransportLayer;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;
import java.util.HashMap;

import javax.print.attribute.HashAttributeSet;

  /**
   * CLHTEngine - Parses and displays the CLHT files
   *
   */
  public class CLHTEngine
  {
      public class Link {
        public String url;
        public String title;

        public Link(String url, String title) {
          this.url = url;
          this.title = title;
        }
      }

      private String doc;
      private ResourceManager resourceManager;
      private ArrayList<Link> links;
      private int port; 


      /**
       * Constructor for objects of class CLHTEngine
       */
      public CLHTEngine(ResourceManager resourceManager, String doc, int port)
      {
          this.doc = doc;
          this.resourceManager = resourceManager;
          this.links = new ArrayList<Link>();
          this.port = port;
      }

      public ArrayList<Link> getLinks() {
        return this.links;
      }

      /**
       * get the link from Link array list
       */
      public Link findLink(int id) {
        return this.links.size() <= id ? null : this.links.get(id);
      }

      /**
       * Render file after parsing and getting all the nested files, replace those file urls
       * with their content and display in the browser
       * 
       * @return String
       */
      public String render()
      {
        String renderString = doc;
        Pattern resourcePattern = Pattern.compile("\\*{3} +([^ ]+) +([^ ]+) +(.+)?\\*{3}");
        Matcher m = resourcePattern.matcher(doc);
        HashMap<String, String> tmp = new HashMap<String, String>();

        // Start replacing links with the file content
        // replace .img with the image content kept in resource manager
        int numLinks = 1;
        while (m.find()) {
          switch (m.group(1)) {
            case "img":
              Resource resource = this.resourceManager.getCachedResource(m.group(2), this.port);
              if (ClientApp.DEBUG_MODE)
                System.out.println(resource);
              renderString = renderString.replace(m.group(0), resource.toString());
              break;
            case "href":
              if (findLink(numLinks - 1) == null) {
                this.links.add(new Link(m.group(2), m.group(3)));
              }
              renderString = renderString.replace(m.group(0), Browser.ANSI_CYAN + "[👉🏻 " + numLinks + "] " + m.group(3) + Browser.ANSI_RESET);
              numLinks++;
              break;
          }
        }

        return ClientApp.DEBUG_MODE ? "" : renderString;
      }
  }
