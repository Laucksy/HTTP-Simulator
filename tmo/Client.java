package endsystems;

import layers.Browser;

public class Client {
  Browser browser;

  public Client () {
    browser = new Browser();
  }

  public void browse () {
    browser.run();
  }

}