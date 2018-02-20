import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

//This class represents the server application
public class ServerApp {
  public static void main(String[] args) throws Exception {
    //create a new transport layer for server (hence true) with server adddress
    TransportLayer transportLayer = new TransportLayer(true, TransportLayer.WEB_LISTENING_PORT);
    while(true) {
      System.out.println("While loop");
      //receive message from client, and send the "received" message back.
      byte[] byteArray = transportLayer.receive();

      //if client disconnected
      if(byteArray == null) {
        TimeUnit.MILLISECONDS.sleep(250);
        continue;
      }
      String str = new String ( byteArray );
      System.out.println(str);
      String line = "received";
      byteArray = line.getBytes();
      transportLayer.send(byteArray);
    }
  }
}
