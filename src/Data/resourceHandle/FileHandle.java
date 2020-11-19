package Data.resourceHandle;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class FileHandle<T> {
    private String path;

    public List<T> readJSON(String fileName, Class<T> tClass){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, tClass);
        String path = System.getProperty("user.dir") + "/projet-lo23a20d1/resource/" + fileName;
        try {
            List<T> ts = mapper.readValue(Paths.get(path).toFile(), listType);
            return ts;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

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
