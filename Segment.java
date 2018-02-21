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

    return segment;
  }

}
