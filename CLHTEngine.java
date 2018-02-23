  import java.util.ArrayList;
  import java.util.Scanner;
  import java.util.regex.*;

  /**
   * CLHTEngine - Parses and displays the CLHT files
   *
   */
  public class CLHTEngine
  {
      public class Link {
        String url;
        String title;

        public Link(String url, String title) {
          this.url = url;
          this.title = title;
        }
      }

      public ArrayList<Link> links;
      public ArrayList<String> images;

      /**
       * Constructor for objects of class CLHTEngine
       */
      public CLHTEngine(String doc)
      {
          this.links = new ArrayList<Link>();
          this.images = new ArrayList<String>();

           Pattern resourcePattern = Pattern.compile("\\*{3} +([^ ]+) +([^ ]+) +([^ ]+ +)?\\*{3}");
           Matcher m = resourcePattern.matcher(doc);

            while (m.find()) {
              switch (m.group(1)) {
                case "img":
                  images.add(m.group(2));
                  break;
                case "href":
                  links.add(new Link(m.group(2), m.group(3)));
                  break;
              }
            }
      }

      public String render()
      {
        return "";
      }
  }
