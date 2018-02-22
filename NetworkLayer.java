import java.util.concurrent.TimeUnit;

public class NetworkLayer {
  private final long PROPAGATION_DELAY = 250;
  private final long TRANSMISSION_DELAY_RATE = 10;
  private LinkLayer linkLayer;

  public NetworkLayer(boolean server, int addr) {
    linkLayer = new LinkLayer(server, addr);
  }

  public void close() {
    linkLayer.close();
  }

  public void send(byte[] payload) {
    try {
      TimeUnit.MILLISECONDS.sleep(TRANSMISSION_DELAY_RATE * payload.length);
    } catch(InterruptedException e) {
      System.out.println(e);
    }

    linkLayer.send(payload);
  }

  public byte[] receive() {
    byte[] payload = linkLayer.receive();

    // System.out.println("Network Layer received " + payload.length + " bytes");

    try {
      TimeUnit.MILLISECONDS.sleep(PROPAGATION_DELAY);
    } catch(InterruptedException e) {
      e.printStackTrace();
    }
    return payload;
  }
}
