public class Request
{
    public String url;
    public ResourceType type;

    public Request(String url, ResourceType type) {
      this.url = url;
      this.type = type;
    }

    public String execute() {
        // String response = http.get("1.0", this.url, TransportLayer.WEB_LISTENING_PORT);
        return "";
    }
}
