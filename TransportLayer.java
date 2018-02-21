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
    System.out.println("Opening TCP Connection: \n");
    Segment segment = new Segment(0, WEB_LISTENING_PORT, rand.nextInt(10000));
    segment.setSYN();

    System.out.println("\nSending TCP segment to network Layer:\n");
    System.out.println(segment);

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
    
    byte[] header = new byte[32];
    byte[] data = new byte[payload.length - 32];
 
    System.out.println("Transport Layer received " + payload.length + " bytes");

    int i = 0;
    for (; i < header.length; i++) {
      header[i] = payload[i];
    }

    for (; i < payload.length; i++) {
      data[i - 32] = payload[i];
    }


    Segment segment = new Segment(header);

    // if (segment.syn() == 1 && segment.ack() == 0) {
    //   segment.setACK();
    //   send(segment.format());
    //   return null;
    // } else if (segment.syn() == 1 && segment.ack() == 1) {
    //   segment.unsetSYN();
    //   return null;
    // }

    System.out.println("\nReceived a packet with following TCP headers: \n");
    System.out.println(segment);

    return data;
  }
}
