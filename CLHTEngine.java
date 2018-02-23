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
        String url;
        String title;

        public Link(String url, String title) {
          this.url = url;
          this.title = title;
        }
      }

      private String doc;

      public ArrayList<Link> links;
      public ArrayList<String> images;

      /**
       * Constructor for objects of class CLHTEngine
       */
      public CLHTEngine(String doc)
      {
          this.doc = doc;
          
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
        String copy = doc;        
        Pattern resourcePattern = Pattern.compile("\\*{3} +([^ ]+) +([^ ]+) +([^ ]+ +)?\\*{3}");
        Matcher m = resourcePattern.matcher(doc);
        HashMap<String, String> tmp = new HashMap<String, String>();

        tmp.put("g", "hello there");
        tmp.put("c", "bye");
        tmp.put("a", "hmm");

        for (int i = 0; i < links.size(); i++) {
          System.out.println(links.get(i).url);
        }


        while (m.find()) {
          System.out.println(m.group(0));
          copy = copy.replace(m.group(0), tmp.get(m.group(2).substring(0, 1)));
        }

       
        System.out.println(copy);

        return "";
      }
  }
