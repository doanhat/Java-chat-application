package data.resource_handle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class FileHandle<T> {
    public static final String EXTENSION = ".json";
    public static final String EXTENSION_IMAGE = ".jpg";

    ObjectMapper mapper = new ObjectMapper();
    private String path;

    private LocationType location;

    private FileType fileType;

    public FileHandle(LocationType location,FileType fileType) {
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "resource" + System.getProperty("file.separator") + location + System.getProperty("file.separator") + fileType + System.getProperty("file.separator");
        if (!Paths.get(filePath).toFile().exists() || !Paths.get(filePath).toFile().isDirectory()) {
            Paths.get(filePath).toFile().mkdirs();
        }
        this.path = filePath;
    }


    public List<T> readJSONFileToList(String fileName, Class<T> tClass){
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, tClass);
        String sysPath = this.path + fileName + EXTENSION;
        try {
            return mapper.readValue(Paths.get(sysPath).toFile(), listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<T> readAllJSONFilesToList(Class<T> tClass){
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            List<T> ts = new ArrayList<>();
            File directoryPath = new File(this.path);
            //List of all files and directories

            File[] filesList = directoryPath.listFiles((dir, name) -> (name.toLowerCase().endsWith(EXTENSION)));
            if(filesList != null) {
                for (File file : filesList) {
                    T t = mapper.readValue(Paths.get(file.getAbsolutePath()).toFile(), tClass);
                    ts.add(t);
                }
            }
            return ts;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public <T> T readJSONFileToObject(String fileName, Class<T> tClass){
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String sysPath = this.path + fileName + EXTENSION;
        try {
            return mapper.readValue(Paths.get(sysPath).toFile(), tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeJSONToFile(String fileName, Object object){
        try {
            mapper.writeValue(Paths.get(this.path + fileName + EXTENSION).toFile(), object);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void addObjectToFile(String fileName, Object object, Class<T> tClass){
        List<T> list = readJSONFileToList(fileName,tClass);
        list.add((T) object);
        writeJSONToFile(fileName,list);
    }

    public boolean deleteJSONFile(String fileName){
        String sysPath = this.path + fileName + EXTENSION;
        try {
            Files.delete(Paths.get(sysPath));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //Seulement JPG en ce moment
    public String readAvatarAsBase64String(String userId) throws IOException {
        String sysPath = this.path + userId + EXTENSION_IMAGE;
        File image = Paths.get(sysPath).toFile();
        byte[] bytes;
        try (FileInputStream fileInputStreamReader = new FileInputStream(image)) {
            bytes = new byte[(int) image.length()];
            fileInputStreamReader.read(bytes);
        }
        return new String(Base64.getEncoder().encode(bytes), StandardCharsets.UTF_8);
    }

    public String getAvatarPath(String userId) {
        String sysPath = this.path + userId + EXTENSION_IMAGE;
        if (Paths.get(sysPath).toFile().exists()){
            return System.getProperty("file.separator") + "resource" + System.getProperty("file.separator") + location+ System.getProperty("file.separator") + fileType + System.getProperty("file.separator") + userId + EXTENSION_IMAGE;
        }
        return null;
    }

    public void writeEncodedStringToFile(String encodedString, String fileName) throws IOException {
        String sysPath = this.path + fileName + EXTENSION_IMAGE;
        byte[] decodedBytes = Base64
                .getDecoder()
                .decode(encodedString);
        try (FileOutputStream stream = new FileOutputStream(Paths.get(sysPath).toFile())) {
            stream.write(decodedBytes);
        }
    }

    public String serialize(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
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
