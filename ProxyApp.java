import java.io.BufferedReader;
import java.io.InputStreamReader;

//This class represents the client application
public class ProxyApp {
  public static void main(String[] args) throws Exception {
    //create a new transport layer for proxy server (hence true), on the correct port
    TransportLayer transportLayer = new TransportLayer(true, TransportLayer.PROXY_LISTENING_PORT);
    //open a client connection to the server (hence false), on the correct port
    TransportLayer transportLayer2 = new TransportLayer(false, TransportLayer.WEB_LISTENING_PORT);

    while(true) {
      //get line from client
      byte[] byteArray = transportLayer.receive();
      if(byteArray == null)
          break;
	    System.out.println("From Client: " + byteArray);
      //send line to server
      transportLayer2.send(byteArray);
      //recieve line from server and forward to client
      byteArray = transportLayer2.receive();
      System.out.println("From Server: " + byteArray);
      transportLayer.send(byteArray);
    }
  }
}
