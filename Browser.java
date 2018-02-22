import java.util.Scanner;

//This class represents the client application
public class Browser {
  // Text colors
  private static final String ANSI_RESET = "\u001B[0m";
  private static final String ANSI_BLACK = "\u001B[30m";
  private static final String ANSI_RED = "\u001B[31m";
  private static final String ANSI_GREEN = "\u001B[32m";
  private static final String ANSI_YELLOW = "\u001B[33m";
  private static final String ANSI_BLUE = "\u001B[34m";
  private static final String ANSI_PURPLE = "\u001B[35m";
  private static final String ANSI_CYAN = "\u001B[36m";
  private static final String ANSI_WHITE = "\u001B[37m";

  // Text Background Colors
  private static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
  private static final String ANSI_RED_BACKGROUND = "\u001B[41m";
  private static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
  private static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
  private static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
  private static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
  private static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
  private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

  // Internal components
  private ResourceManager resourceManager = new ResourceManager();


  public static void main(String[] args) throws Exception {
    Browser broswer = new Browser();
  }

  public Browser() {
    renderRoutine();
  }

  public void renderRoutine() {
    renderPage("home");
    promptForURL();
    Scanner sc = new Scanner(System.in);
    while(sc.hasNextLine()) {
      String command = sc.nextLine();
      if (command.equals(""))
        command = "home";
      if (command.equals("exit"))
        System.exit(0);
      renderPage(command);
      promptForURL();
    }
  }

  public void promptForURL() {
    System.out.println("Type URL or Hypertext Anchor ID to navigate: ");
    System.out.print(" ");
    for (int i = 0; i <= 80; i++) {
      System.out.print("_");
    }
    System.out.println();
    System.out.print("|");
  }

  public void renderPage(String url) {
      String pageTitle = url.equals("home") ? "New Tab" : url;

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
      for (int i = 0; i <= 60; i++) {
        System.out.print("\u2589");
      }
      System.out.println();

      if (url.equals("home"))
        renderHome();
      else
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

  public void renderUrl(String url) {
    Resource page = resourceManager.loadUrl(new Request(url, Resource.Type.CLML));
    System.out.println(page);
  }
}
