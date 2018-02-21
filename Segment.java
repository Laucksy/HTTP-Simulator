public class Segment {
  private int[] flags;
  private int source;
  private int destination;
  private int sequenceNumber;
  private int ackNumber;

  public Segment (int source, int destination, int sequenceNumber) {
    // SYN, FIN, ACK flags are not set initially
    flags = new int[]{0, 0, 0};

    this.source = source;
    this.destination = destination;
    this.sequenceNumber = sequenceNumber;
    this.ackNumber = -1;
  }

  public Segment (int source, int destination, int sequenceNumber, int ackNumber) {
    // SYN, FIN, ACK flags are not set initially
    flags = new int[]{0, 0, 0};

    this.source = source;
    this.destination = destination;
    this.sequenceNumber = sequenceNumber;
    this.ackNumber = ackNumber;
  }

  public Segment (byte[] segment) {
    // SYN, FIN, ACK flags are not set initially
    flags = new int[]{0, 0, 0};
    int a, b, c, d;

    a = segment[13] >= 0 ? segment[13] : 256 + segment[13];
    b = segment[12] >= 0 ? segment[12] : 256 + segment[12];
    source = b | (a << 8);

    a = segment[15] >= 0 ? segment[15] : 256 + segment[15];
    b = segment[14] >= 0 ? segment[14] : 256 + segment[14];
    destination = b | (a << 8);
 
    // Sequence number
    a = segment[19] >= 0 ? segment[19] : 256 + segment[19];
    b = segment[18] >= 0 ? segment[18] : 256 + segment[18];
    c = segment[17] >= 0 ? segment[17] : 256 + segment[17];
    d = segment[16] >= 0 ? segment[16] : 256 + segment[16];
    sequenceNumber = d | ((c | ((b | (a << 8)) << 8)) << 8);

    // ACK number
    a = segment[23] >= 0 ? segment[23] : 256 + segment[23];
    b = segment[22] >= 0 ? segment[22] : 256 + segment[22];
    c = segment[21] >= 0 ? segment[21] : 256 + segment[21];
    d = segment[20] >= 0 ? segment[20] : 256 + segment[20];
    ackNumber = d | ((c | ((b | (a << 8)) << 8)) << 8);

    flags[0] = (segment[25] >> 0) & 1;
    flags[1] = (segment[25] >> 2) & 1;
    flags[2] = (segment[25] >> 5) & 1;


  }

  public int source () {
    return this.source;
  }

  public int destination () {
    return this.destination;
  }

  public int sequenceNumber () {
    return this.sequenceNumber;
  }

  public void setSYN () {
    flags[0] = 1;
  }

  public void unsetSYN () {
    flags[0] = 0;
  }

  public void setFIN () {
    flags[1] = 1;
  }

  public void unsetFIN () {
    flags[1] = 0;
  }

  public void setACK () {
    flags[2] = 1;
  }

  public void unsetACK () {
    flags[2] = 0;
  }

  public int syn () {
    return flags[0];
  }

  public int fin () {
    return flags[1];
  }

  public int ack () {
    return flags[2];
  }


  /**
   * |              Source Address              |
   * |            Destination Address           |
   * |        Zeros | Protocol | TCP length     |
   * |        Source Port | Destination Port    |
   * |             Sequence Number              |
   * |          Aknowledgement Number           |
   * |  Data offset | Reserved | Flags | Window |
   * |      Checksum    |     Urgent Pointer    |
   * |              Options                     |
   */
  public byte[] format () {
    byte[] segment = new byte[32];

    segment[0] = segment[1] = segment[2] = segment[3] = 0;
    segment[4] = segment[5] = segment[6] = segment[7] = 0;
    segment[8] = segment[9] = segment[10] = segment[11] = 0;

    // Source Port
    segment[12] = (byte)(source & 0xFF);
    segment[13] = (byte)((source >> 8) & 0xFF);

    // Destination Port
    segment[14] = (byte)(destination & 0xFF);
    segment[15] = (byte)((destination >> 8) & 0xFF);


    // Sequence number
    segment[16] = (byte)(sequenceNumber & 0xFF);
    segment[17] = (byte)((sequenceNumber >> 8) & 0xFF);
    segment[18] = (byte)((sequenceNumber >> 16) & 0xFF);
    segment[19] = (byte)((sequenceNumber >> 24) & 0xFF);

    for (int i = 20; i < segment.length; i++) {
      segment[i] = 0;
    }

    if (ackNumber != -1) {
      segment[20] = (byte)(ackNumber & 0xFF);
      segment[21] = (byte)((ackNumber >> 8) & 0xFF);
      segment[22] = (byte)((ackNumber >> 16) & 0xFF);
      segment[23] = (byte)((ackNumber >> 24) & 0xFF);
    }

    segment[25] = (byte)(flags[2] * 16 + flags[1] * 2 + flags[0]);

    return segment;
  }

  public String toString () {
    String content = "";

    content = "Source Port: "       + source          + '\n' +
              "Destination Port:"   + destination     + '\n' +
              "Sequence Number:"    + sequenceNumber  + '\n' +
              "ACK Number:"         + ackNumber       + '\n' +
              "Flags:"  + "\tSYN: " + flags[0] + "\tFIN: " + flags[1] + "\tACK: " + flags[2];


    return content;
  }

}
