package runners;

import endsystems.Proxy;

public class ProxyApp {
  public static void main(String[] args) throws Exception {
    int TRANSMISSION_DELAY_RATE = 1;
    int PROPAGATION_DELAY = 100;
    if (args.length >= 2) {
      TRANSMISSION_DELAY_RATE = Integer.parseInt(args[0]);
      PROPAGATION_DELAY = Integer.parseInt(args[1]);
    }

    Proxy  proxy = new Proxy(TRANSMISSION_DELAY_RATE, PROPAGATION_DELAY);
    proxy.run();
  }
}
