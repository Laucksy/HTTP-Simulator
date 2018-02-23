package layers;

public class LinkLayer {
  private PhysicalLayer physicalLayer;

  public LinkLayer(boolean server, int addr) {
    physicalLayer = new PhysicalLayer(server, addr);
  }

  public void close() {
    physicalLayer.close();
  }

  public void send(byte[] payload) {
    physicalLayer.send(payload);
  }

  public byte[] receive() {
    byte[] payload = physicalLayer.receive();
    // System.out.println("Link Layer received " + payload.length + " bytes");
    return payload;
  }
}
