import java.util.Random;

public class TransportLayer {
  //port that server will be listening on on localhost for webserver.
  public static final int WEB_LISTENING_PORT = 8888;
  //port that server will be listening on on localhost for proxy cache.
  public static final int PROXY_LISTENING_PORT = 8889;
  private NetworkLayer networkLayer;
  Random rand;

  //server is true if the application is a server (should listen)
  //or false if it is a client (should try and connect)
  //addr is the port number to listen or connect to
  public TransportLayer(boolean server, int addr) {
    networkLayer = new NetworkLayer(server, addr);
    rand = new Random();
  }

  public void connect () {
    Segment segment = new Segment(0, WEB_LISTENING_PORT, rand.nextInt(10000));
    segment.setSYN();
    segment.format();
    System.out.println("Sending TCP segment to network Layer");
    networkLayer.send(segment.format());
  }

  public void close() {
    networkLayer.close();
  }

  public void send(byte[] payload) {
    System.out.println("SENDING DATA");
    networkLayer.send(payload);
  }

  public byte[] receive() {
    byte[] payload = networkLayer.receive();
    // System.out.println(payload);
    return payload;
  }
}
