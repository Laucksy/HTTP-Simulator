package helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public final class Helper {

  private static final Helper instance = new Helper();

  private Helper () {}

  public static Helper instance () {
    return instance;
  }

  public String read (String url) {
    String content = "";

    try {
      File file = new File(url);
      Scanner sc = new Scanner(file);

      while (sc.hasNextLine()) {
        content += sc.nextLine() + '\n';
      }

      sc.close();
    } catch (FileNotFoundException e) {
      return "Not Found";
    } catch (Exception e) {
      e.printStackTrace();
    }

    return content;
  }

  public String type (String url) {
    return url.lastIndexOf('.') > 0 ?
            "text/" + url.substring(url.lastIndexOf('.') + 1) :
            "application/octet-stream";
  }

}
