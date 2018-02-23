package layers;

import extra.ResourceManager;
import extra.Resource;
import engines.HTTPEngine;
import layers.TransportLayer;
import runners.ClientApp;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Broswer - Implements the command line interface shown to the user through
 * the client app.
 *
 */

public class Browser {
  // Text colors
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";

  // Text Background Colors
  public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
  public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
  public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
  public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
  public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
  public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
  public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
  public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

  // Internal components
  private ResourceManager resourceManager = new ResourceManager(1, 100);
  private Resource currentResource;

  public Browser() {}

  /*
    run - runs the browser routine
  */
  public void run () {
    renderRoutine();
  }

  /*
    renderRoutine - Constantly checks for URL/href input from the user and
    renders the page they requested
  */
  public void renderRoutine() {
    Scanner sc = new Scanner(System.in);

    // Initial run
    renderPage("home");
    promptForURL();

    // Subsequent runs
    while(sc.hasNextLine()) {
      String command = sc.nextLine();

      // Built-in commands
      if (command.equals(""))
        command = "home";
      if (command.equals("exit"))
        System.exit(0);

      // Render the page
      renderPage(command);

      // Ask for a new URL
      promptForURL();
    }
  }

  /*
    promptForURL - Shows the message that asks user for input
  */
  public void promptForURL() {
    for (int i = 0; i <= 80; i++) {
      System.out.print("=");
    }
    System.out.println();
    System.out.println("Type URL or Hypertext Anchor ID to navigate: ");
    System.out.print("/");
  }

  /*
    renderPage - Loads and renders the frame around the page
  */
  public void renderPage(String url) {
      String pageTitle = "";

      if (url.equals("home"))
        pageTitle = "New Tab";
      else if (url.matches("-?\\d+(\\.\\d+)?") && this.currentResource != null)
        pageTitle = this.currentResource.findLink(Integer.parseInt(url) - 1);
      else
        pageTitle = url;

      // Clear screen
      System.out.print("\033[H\033[2J");
      System.out.flush();

      // Tab bar
      System.out.print("\u2589");
      System.out.print("\u2589");
      System.out.print("\u25E4");
      System.out.print(" " + pageTitle + " ");
      System.out.print("\u25E5");
      System.out.print("\u25E5");
      for (int i = 0; i <= (80 - pageTitle.length() - 4 - 5); i++) {
        System.out.print("\u2589");
      }
      System.out.println();

      // Render the pages based on their content
      if (url.equals("home")) // Home page
        renderHome();
      else if (url.matches("-?\\d+(\\.\\d+)?") && this.currentResource != null) // Href
        renderUrl(this.currentResource.findLink(Integer.parseInt(url) - 1));
      else // URL
        renderUrl(url);

       System.out.println();
       System.out.println();
  }

  /*
    renderHome - Render the home page

  */
  public void renderHome() {
    System.out.println();
    System.out.println();
    String appertureLogo = "\n"
                            + "                         .,-:;//;:=,\n"
                            + "                     . :H@@@MM@M#H/.,+%;,\n"
                            + "                  ,/X+ +M@@M@MM%=,-%HMMM@X/,\n"
                            + "                -+@MM; $M@@MH+-,;XMMMM@MMMM@+-\n"
                            + "               ;@M@@M- XM@X;. -+XXXXXHHH@M@M#@/.\n"
                            + "             ,%MM@@MH ,@%=             .---=-=:=,.\n"
                            + "             =@#@@@MX.,                -%HX$$%%%:;\n"
                            + "            =-./@M@M$                   .;@MMMM@MM:\n"
                            + "            X@/ -$MM/                    . +MM@@@M$\n"
                            + "           ,@M@H: :@:                    . =X#@@@@-     Apperture Browser\n"
                            + "           ,@@@MMX, .                    /H- ;@M@M=     v1.0\n"
                            + "           .H@@@@M@+,                    %MM+..%#$.     Copyright \u00A9 2018\n"
                            + "            /MMMM@MMH/.                  XM@MH; =;\n"
                            + "             /%+%$XHH@$=              , .H@@@@MX,\n"
                            + "              .=--------.           -%H.,@@@@@MX,\n"
                            + "              .%MM@@@HHHXX$$$%+- .:$MMX =M@@MM%.\n"
                            + "                =XMMM@MM@MM#H;,-+HMM@M+ /MMMX=\n"
                            + "                  =%@M@M#@$-.=$@MM@@@M; %M%=\n"
                            + "                    ,:+$+-,/H#MMMMMMM@= =,\n"
                            + "                          =++%%%%+/:-.";

     System.out.println(appertureLogo);
     System.out.println();
     System.out.println();
  }


  /*
    renderHome - 404 Page

  */
  public void render404() {
    System.out.println();
    System.out.println();
    String picture = "\n"
                      + "    _    _     ___     _    _        _       _\n"
                      + "   |:|  |:|   /:::\\   |:|  |:|      |_ | |  |_\n"
                      + "   |:|  |:|  /:/'\\:\\  |:|  |:|      |  | |_ |_\n"
                      + "   |:|__|:| |:|   |:| |:|__|:|           _  ___\n"
                      + "   |::::::| |:|   |:| |::::::|     |\\ | / \\  |\n"
                      + "   '\"\"\"\"|:| |:|   |:| '\"\"\"\"|:|     | \\| \\_/  |\n"
                      + "        |:| |:|   |:|      |:|   _  _            _\n"
                      + "        |:| |:\\   |:|      |:|  |_ / \\ | | |\\ | | \\\n"
                      + "        |:|  \\:\\_/:/       |:|  |  \\_/ \\_/ | \\| |_/\n"
                      + "        |:|   \\:::/        |:|\n"
                      + "        '\"'    '\"'         '\"'";

     System.out.println(picture);
     System.out.println();
     System.out.println();
  }

  /*
    renderPage - Loads and renders the given URL
  */
  public void renderUrl(String url) {
    Timer timer = new Timer();

    // The loader shows a progress bar as the page loads
    Loader loader = new Loader();

    timer.scheduleAtFixedRate(loader, /* initialDelay */ 0, /* interval */ 100);

    long start = System.currentTimeMillis();
    this.currentResource = resourceManager.getCachedResource(url, TransportLayer.PROXY_LISTENING_PORT);
    HTTPEngine.HTTPResponse response = this.currentResource.getResponse();

    // Finish loading
    timer.cancel();
    loader.fillRest();

    System.out.println();
    if (response.status == 404 && !ClientApp.DEBUG_MODE)
      render404();
    else
      System.out.println(this.currentResource);

    long end = System.currentTimeMillis();

    if (ClientApp.EXPERIMENT_MODE || ClientApp.DEBUG_MODE)
      System.out.println(ANSI_PURPLE + " -> Took Total : " + (end - start) + " millis" + ANSI_RESET);
  }

  // Shows a progress bar
  class Loader extends TimerTask {
    int progress = 0;

    public Loader () {
      super();
    }

    @Override
    public void run() {
      if (ClientApp.DEBUG_MODE)
        return;
      System.out.print(ANSI_BLUE + "=" + ANSI_RESET);
      progress++;
    }

    public void fillRest() {
      if (ClientApp.DEBUG_MODE)
        return;
      for (int i = progress; i < 80; i++) {
        System.out.print(ANSI_BLUE + "=" + ANSI_RESET);
      }
    }
  }
}
