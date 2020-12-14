package Data.resourceHandle;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileHandle<T> {
    ObjectMapper mapper = new ObjectMapper();
    private String path;

    private LocationType location;

    private FileType fileType;

    public FileHandle(LocationType location,FileType fileType) {
        String filePath = System.getProperty("user.dir") + "/resource/"+location+"/"+fileType+"/";
        if (!Paths.get(filePath).toFile().exists() || !Paths.get(filePath).toFile().isDirectory()) {
            Paths.get(filePath).toFile().mkdirs();
        }
        this.path = filePath;
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

    public List<T> readAllJSONFilesToList(Class<T> tClass){
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            List<T> ts = new ArrayList<>();
            File directoryPath = new File(this.path);
            //List of all files and directories

            File filesList[] = directoryPath.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    if (name.toLowerCase().endsWith(".json")) return true;
                    return false;
                }
            });
            if(filesList != null) {
                for (File file : filesList) {
                    //System.out.println(Paths.get(file.getAbsolutePath()));
                    T t = mapper.readValue(Paths.get(file.getAbsolutePath()).toFile(), tClass);
                    ts.add(t);
                }
            }
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
        String path = this.path + fileName + ".json";
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

    public LocationType getLocation() {
        return location;
    }

    public void setLocation(LocationType location) {
        this.location = location;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }
}
