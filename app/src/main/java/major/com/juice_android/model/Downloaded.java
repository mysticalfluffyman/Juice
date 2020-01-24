package major.com.juice_android.model;

public class Downloaded {
    private    String Name;
    private String url;
    private String id;


    public Downloaded(String name, String url, String id) {
        Name = name;
        this.url = url;
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public  String getId(){return id; }

    public  void setId(String id){this.id = id;};
}
