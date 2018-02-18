import java.util.concurrent.TimeUnit;

public class NetworkLayer {
  private final float PROPAGATION_DELAY = 0.25;
  private final float TRANSMISSION_DELAY_RATE = 0.05;
  private LinkLayer linkLayer;

  public NetworkLayer(boolean server, int addr) {
    linkLayer = new LinkLayer(server, addr);
  }

  public void send(byte[] payload) {
    TimeUnit.SECONDS.sleep(TRANSMISSION_DELAY_RATE * payload.length);
    linkLayer.send(payload);
  }

  public byte[] receive() {
    byte[] payload = linkLayer.receive();
    TimeUnit.SECONDS.sleep(PROPAGATION_DELAY);
    return payload;
  }
}
