public class Segment {
  private int[] flags;
  private int _source;
  private int _destination;
  private int _sequenceNumber;
  private int _ackNumber;

  public Segment (int source, int destination, int sequenceNumber) { 
    // SYN, FIN, ACK flags are not set initially
    flags = new int[]{0, 0, 0};

    _source = source;
    _destination = destination;
    _sequenceNumber = sequenceNumber;
  }

  public Segment (int source, int destination, int sequenceNumber, int ackNumber) { 
    // SYN, FIN, ACK flags are not set initially
    flags = new int[]{0, 0, 0};

    _source = source;
    _destination = destination;
    _sequenceNumber = sequenceNumber;
    _ackNumber = ackNumber;
  }

  public int source () {
    return _source;
  }

  public int destination () {
    return _destination;
  }

  public int sequenceNumber () {
    return _sequenceNumber;
  }

  public void setSYN () {
    flags[0] = 1;
  }

  public void unsetSYN () {
    flags[0] = 0;
  }

  public void setFIN () {
    flags[0] = 1;
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
    segment[12] = (byte)(_source & 0xFF); 
    segment[13] = (byte)((_source >> 8) & 0xFF);

    // Destination Port
    segment[14] = (byte)(_destination & 0xFF); 
    segment[15] = (byte)((_destination >> 8) & 0xFF);
    

    // Sequence number
    segment[16] = (byte)(_sequenceNumber & 0xFF); 
    segment[17] = (byte)((_sequenceNumber >> 8) & 0xFF); 
    segment[18] = (byte)((_sequenceNumber >> 16) & 0xFF); 
    segment[19] = (byte)((_sequenceNumber >> 24) & 0xFF); 

    for (int i = 20; i < segment.length; i++) {
      segment[i] = 0;
    }

    if (_ackNumber != null) {
      segment[20] = (byte)(_ackNumber & 0xFF); 
      segment[21] = (byte)((_ackNumber >> 8) & 0xFF); 
      segment[22] = (byte)((_ackNumber >> 16) & 0xFF); 
      segment[23] = (byte)((_ackNumber >> 24) & 0xFF); 
    }

    return segment;
  }

}
