import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;

/**
 * CLMLParser - Parses and displays the CLML files
 *
 */
public class CLMLParser
{
    private ArrayList<String> links;
    private ArrayList<String> images;
    private Matcher m;

    public static void main (String[] args) {
        String content = Helper.instance().read(args[0]);
        
        CLMLParser parser = new CLMLParser(content);
    }

    /**
     * Constructor for objects of class CLMLParser
     */
    public CLMLParser(String doc)
    {
         Pattern resourcePattern = Pattern.compile("\\*{3} +([^ ]+) +([^ ]+) +([^ ]+ +)?\\*{3}");
         m = resourcePattern.matcher(doc);
         
         while (m.find()) {
             System.out.println("\u001B[33m ===================== \u001B[0m\n");
             System.out.println("\u001B[31m Type: " + m.group(1) + "\u001B[0m");
             System.out.println("\u001B[34m Source: " + m.group(2) + "\u001B[0m");
             if(m.group(3) != null)
                System.out.println("\u001B[32m Title: " + m.group(3) + "\u001B[0m");

         }
         
         System.out.println("\n\u001B[32m---------- Parsing is done ----------\u001B[0m");
    }
}
