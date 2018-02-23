package engines;

import extra.ResourceManager;

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


      /**
       * Constructor for objects of class CLHTEngine
       */
      public CLHTEngine(ResourceManager resourceManager, String doc)
      {
          this.doc = doc;
          this.resourceManager = resourceManager;
          this.links = new ArrayList<Link>();
      }

      public ArrayList<Link> getLinks() {
        return this.links;
      }

      public Link findLink(int id) {
        return this.links.size() <= id ? null : this.links.get(id);
      }

      public String render()
      {
        String renderString = doc;
        Pattern resourcePattern = Pattern.compile("\\*{3} +([^ ]+) +([^ ]+) +(.+)?\\*{3}");
        Matcher m = resourcePattern.matcher(doc);
        HashMap<String, String> tmp = new HashMap<String, String>();


        while (m.find()) {
          switch (m.group(1)) {
            case "img":
              renderString = renderString.replace(m.group(0), this.resourceManager.getCachedResource(m.group(2)).toString());
              break;
            case "href":
              this.links.add(new Link(m.group(2), m.group(3)));
              renderString = renderString.replace(m.group(0), "[👉🏻 " + this.links.size() + "] " + m.group(3));
              break;
          }
        }

        return renderString;
      }
  }
