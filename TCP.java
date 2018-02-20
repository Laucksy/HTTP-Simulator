public class TCP {
  private int[] flags;
  private int _source;
  private int _destination;
  private int _sequenceNumber;

  public TCP (int source, int destination, int sequenceNumber) { 
    // SYN, FIN, ACK flags are not set initially
    flags = new int[]{0, 0, 0};

    _source = source;
    _destination = destination;
    _sequenceNumber = sequenceNumber;
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


}