package Data.resourceHandle;

public class dataClientFileHandle {
    private String resourcePath;

    private static final ObjectMap

    public dataClientFileHandle(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public dataClientFileHandle() {
        this.resourcePath = System.getProperty("user.dir") + "/projet-lo23a20d1/resource";
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getResourcePath(){
        return resourcePath;
    }

    void writeFile
}
