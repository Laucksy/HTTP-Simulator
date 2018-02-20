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
         Pattern resourcePattern = Pattern.compile("\\*\\*\\*[a-zA-Z]+\\s[a-zA-Z]+\\s[a-zA-Z]*\\s\\*\\*\\*");
         m = resourcePattern.matcher(doc);
         System.out.println(m);
    }
}
