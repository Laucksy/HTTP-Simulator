public class Request
{
    public String url;
    public Resource.Type type;

    public Request(String url, Resource.Type type) {
      this.url = url;
      this.type = type;
    }

    public String getUrl() {
      return this.url;
    }
}
