import java.util.concurrent.TimeUnit;

public class NetworkLayer {
  private final long PROPAGATION_DELAY = 250;
  private final long TRANSMISSION_DELAY_RATE = 10;
  private LinkLayer linkLayer;

  public NetworkLayer(boolean server, int addr) {
    linkLayer = new LinkLayer(server, addr);
  }

  public void send(byte[] payload) {
    try {
      TimeUnit.SECONDS.sleep(TRANSMISSION_DELAY_RATE * payload.length);
    } catch(InterruptedException e) {}
    linkLayer.send(payload);
  }

  public byte[] receive() {
    byte[] payload = linkLayer.receive();
    try {
      TimeUnit.SECONDS.sleep(PROPAGATION_DELAY);
    } catch(InterruptedException e) {}
    return payload;
  }
}
