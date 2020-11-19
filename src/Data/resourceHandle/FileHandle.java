package Data.resourceHandle;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FileHandle {
    private String path;

    private static final ObjectMapper mapper = new ObjectMapper();

    public FileHandle(String resourcePath) {
        this.path = resourcePath;
    }

    public FileHandle() {
        this.path = System.getProperty("user.dir") + "/projet-lo23a20d1/resource";
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath(){
        return path;
    }

}
