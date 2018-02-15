public class LinkLayer
{
    private PhysicalLayer physicalLayer;

    public LinkLayer(boolean server)
    {
        physicalLayer = new PhysicalLayer(server);
    }

    public void send(byte[] payload)
    {
        physicalLayer.send( payload );
    }

    public byte[] receive()
    {
        byte[] payload = physicalLayer.receive();
        return payload;
    }
}
