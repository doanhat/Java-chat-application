package Data.resourceHandle;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import common.sharedData.OwnedChannel;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileHandle<T> {
    ObjectMapper mapper = new ObjectMapper();;
    private String path;

    public FileHandle() {
        this.path = System.getProperty("user.dir") + "/projet-lo23a20d1/resource/";
    }

    public FileHandle(String resourcePath) {
        this.path = resourcePath;
    }


    public List<T> readJSONFileToList(String fileName, Class<T> tClass){
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, tClass);
        String sysPath = this.path + fileName + ".json";
        try {
            List<T> ts = mapper.readValue(Paths.get(sysPath).toFile(), listType);
            return ts;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T readJSONFileToObject(String fileName, Class<T> tClass){
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String sysPath = this.path + fileName + ".json";
        try {
            T t = mapper.readValue(Paths.get(sysPath).toFile(), tClass);
            return t;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeJSONToFile(String fileName, Object object){
        ObjectMapper mapper = new ObjectMapper();
        String path = System.getProperty("user.dir") + "/projet-lo23a20d1/resource/" + fileName + ".json";
        try {
            mapper.writeValue(Paths.get(path).toFile(), object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath(){
        return path;
    }

}
