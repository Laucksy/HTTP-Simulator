package runners;

import layers.TransportLayer;
import helpers.Helper;

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

      int index = str.indexOf("HTTP/") + 5;
      String version = str.substring(index, index + 3);
      String uri = str.substring(str.indexOf(" ") + 1, index - 6);

      String fileData = Helper.instance().read("website_example/" + uri);
      String type = Helper.instance().type("website_example/" + uri);
      
      int code = fileData.equals("Not Found") ? 404 : 200;
      String data = fileData.equals("Not Found") ? "" : fileData;

      String response = createResponse(version, code, type, data);
      byteArray = response.getBytes();
      transportLayer.send(byteArray);
    }
  }

  public static String createResponse(String version, int code, String type, String data) {
    String msg = "Ok";
    if (code == 404) msg = "Not Found";
    if (code == 304) msg = "Not Modified";

    String response = "HTTP/" + version + " " + code + " " + msg + "\n";
    response += "Connection: " + (version.equals("1.1") ? "keep-alive" : "close") + "\n";
    //TODO: Add Date and Last-Modified
    response += "Content-Length: " + data.length() + "\n";
    response += "Content-Type: " + type + "\n";
    response += "\n";
    response += data;

    return response;
  }
}
