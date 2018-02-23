package layers;

import extra.Segment;
import extra.Connection;

import java.util.Random;

public class TransportLayer {
  //port that server will be listening on on localhost for webserver.
  public static final int WEB_LISTENING_PORT = 8888;
  //port that server will be listening on on localhost for proxy cache.
  public static final int PROXY_LISTENING_PORT = 8889;
  private NetworkLayer networkLayer;
  Random rand;
  Segment segment;

  //server is true if the application is a server (should listen)
  //or false if it is a client (should try and connect)
  //addr is the port number to listen or connect to
  public TransportLayer(boolean server, int addr, int trans, int prop) {
    networkLayer = new NetworkLayer(server, addr, trans, prop);
    rand = new Random();
  }

  public void connect () {
    segment = new Segment(0, WEB_LISTENING_PORT, rand.nextInt(10000));
    segment.setSYN();

    send(segment.format());
    receive();
  }

  public void close() {
    networkLayer.close();
  }

  public void send(byte[] payload) {

    if (segment.ack() == 1 && segment.syn() == 0) {

      byte[] tmp = segment.format();
      byte[] data = new byte[payload.length + tmp.length];
      for (int i = 0; i < data.length; i++) {
        if (i < 32) data[i] = tmp[i];
        else data[i] = payload[i - 32];
      }
      networkLayer.send(data);

    } else {
      networkLayer.send(payload);
    }

  }

  public byte[] receive() {
    byte[] payload = networkLayer.receive();

    byte[] header = new byte[32];
    byte[] data = new byte[payload.length - 32];

    int i = 0;
    for (; i < header.length; i++) {
      header[i] = payload[i];
    }

    for (; i < payload.length; i++) {
      data[i - 32] = payload[i];
    }


    segment = new Segment(header);

    if (segment.syn() == 1 && segment.ack() == 0) {
      segment.setACK();

      send(segment.format());
      return receive();
    } else if (segment.syn() == 1 && segment.ack() == 1) {
      segment.unsetSYN();
      return data;
    } else if (segment.syn() == 0 && segment.ack() == 1) {
      return data;
    }


    return data;
  }
}
