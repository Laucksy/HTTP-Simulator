public class NetworkLayer {
  private LinkLayer linkLayer;

  public NetworkLayer(boolean server, int addr) {
    linkLayer = new LinkLayer(server, addr);
  }

  public void send(byte[] payload) {
    linkLayer.send(payload);
  }

  public byte[] receive() {
    byte[] payload = linkLayer.receive();
    return payload;
  }
}
