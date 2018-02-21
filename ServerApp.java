import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

//This class represents the server application
public class ServerApp {
  public static void main(String[] args) throws Exception {
    //create a new transport layer for server (hence true) with server adddress
    TransportLayer transportLayer = new TransportLayer(true, TransportLayer.WEB_LISTENING_PORT);
    while(true) {
      //receive message from client, and send the "received" message back.
      byte[] byteArray = transportLayer.receive();

      //if client disconnected
      if(byteArray == null) {
        TimeUnit.MILLISECONDS.sleep(250);
        continue;
      }
      String str = new String ( byteArray );
      System.out.println("JUST RECEIVED" + str);

      if (str.indexOf("HTTP") >= 0) {
        String fileData = Helper.instance().read("website_example/" + str);

        int code = fileData.equals("Not Found") ? 404 : 200;
        String data = fileData.equals("Not Found") ? "" : fileData;

        System.out.println("HEREEEE" + str);
        int index = str.indexOf("HTTP/") + 5;
        System.out.println(index);
        System.out.println(str.substring(index, index + 3));
        String response = createResponse(str.substring(index, index + 3), code, data);
        System.out.println("SENDING RESPONSE" + response);

        //String line = "received";
        byteArray = response.getBytes();
        transportLayer.send(byteArray);
      } else {
        String response = "ACK";
        byteArray = response.getBytes();
        transportLayer.send(byteArray);
      }
    }
  }

  public static String createResponse(String version, int code, String data) {
    System.out.println("createResponse" + version);
    String msg = "Ok";
    if (code == 404) msg = "Not Found";
    if (code == 304) msg = "Not Modified";

    String response = "HTTP/" + version + " " + code + " " + msg + "\n";
    response += "Connection: " + (version.equals("1.1") ? "keep-alive" : "close") + "\n";
    //TODO: Add Date and Last-Modified
    response += "Content-Length: " + data.length() + "\n";
    response += "Content-Type: text/clht\n";
    response += data;

    return response;
  }
}
