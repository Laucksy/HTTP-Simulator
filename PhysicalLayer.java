
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;


// A class that simulates the physical layer. In real life we are using a TCP socket
public class PhysicalLayer
{
    //port that server will be listening on on localhost.
    private static final int LISTENING_PORT = 8888;
    //the server socket
    ServerSocket serverSocket;
    //the connection socket after the connection is established
    Socket senderSocket, connectionSocket;
    //stream to write out data
    DataOutputStream socketOut;
    //stream to read in data from socket
    InputStream inputStream;

    public PhysicalLayer(boolean server)
    {
        //if this is a server
        if(server)
        {
            try
            {
                //create socket
                serverSocket = new ServerSocket( LISTENING_PORT );
                //accept connetcion from client
                connectionSocket=serverSocket.accept();
                //create an outputstream for writing data
                socketOut = new DataOutputStream(connectionSocket.getOutputStream());
                //create an inputstream for reading data
                inputStream = connectionSocket.getInputStream();

            } catch (IOException e) {}
        }
        else
        {
            try
            {
                //create socket
                senderSocket = new Socket("localhost", LISTENING_PORT);
                //create an outputstream for writing data
                socketOut = new DataOutputStream(senderSocket.getOutputStream());
                //create an inputstream for reading data
                inputStream = senderSocket.getInputStream();

            } catch (IOException e) {
                //will run if server was not listening
                System.out.println("Cannot Connect to server");
                System.exit(1); }
            }

        }


        public void send(byte[] payload)
        {
        try
        {
            //send bytes out to socket
            socketOut.write(payload);
        }
        catch(Exception ex){}
    }

    //read bytes from socket
    public byte[] receive()
    {
        byte[] bytesRecieved = null;
        try
        {
            
            byte[] bytes = new byte[1024];
            int numBytes = inputStream.read(bytes);
            if ( numBytes > 0)
            {
                bytesRecieved = new byte[numBytes];
                System.arraycopy(bytes, 0, bytesRecieved, 0, numBytes );
            }
        } catch (IOException e) {}

        return bytesRecieved;
    }
}
