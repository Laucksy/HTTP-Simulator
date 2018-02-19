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
