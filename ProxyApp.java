import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

//This class represents the client application
public class ProxyApp {
  public static void main(String[] args) throws Exception {
    //create a new transport layer for proxy server (hence true), on the correct port
    TransportLayer transportLayer = new TransportLayer(true, TransportLayer.PROXY_LISTENING_PORT);
    //open a client connection to the server (hence false), on the correct port
    // TransportLayer transportLayer2 = new TransportLayer(false, TransportLayer.WEB_LISTENING_PORT);


    while(true) {
      //get line from client
      byte[] byteArray = transportLayer.receive();
      if(byteArray == null) {
        TimeUnit.MILLISECONDS.sleep(250);
        continue;
      }

      HTTP http = new HTTP();
      String response = http.get("1.0", "test", 8888);

      System.out.println(response);

      System.out.println("From Server: " + response);
      transportLayer.send(response.getBytes());
    }
  }
}
