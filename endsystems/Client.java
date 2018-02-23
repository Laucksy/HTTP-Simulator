package endsystems;

import layers.Browser;

/*
  Client - Endsystem for the browser implementation, creates a new browser
  and runs it.

*/

public class Client {
  Browser browser;

  public Client () {
    browser = new Browser();
  }

  public void browse () {
    browser.run();
  }

}
