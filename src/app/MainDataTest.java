package app;

import Data.resourceHandle.FileHandle;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import common.sharedData.*;
import javafx.application.Application;
import javafx.stage.Stage;

import java.nio.file.Paths;
import java.util.*;

public class MainDataTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        User user1 = new User(
                UUID.randomUUID(),
                "user1",
                "avatar1",
                "password1",
                "last1",
                "first1",
                "06-03-1999"
        );
        UserLite user1Lite = user1.getUserLite();

        User user2 = new User(
                UUID.randomUUID(),
                "user2",
                "avatar2",
                "password2",
                "last2",
                "first2",
                "05-03-1999"
        );

        UserLite user2Lite = user2.getUserLite();
        List<User> listUser = Arrays.asList(user1,user2);
        OwnedChannel ownedChannel = new OwnedChannel(
                UUID.randomUUID(),
                "chan1",
                user1Lite,
                "desc",
                Visibility.PRIVATE
        );
        ownedChannel.addUser(user2Lite);

        Message msg1 = new Message(
                UUID.randomUUID(),
                "Yooo!",
                user1Lite
        );
        Message msg2 = new Message(
                UUID.randomUUID(),
                "Allez!",
                user2Lite
        );

        ownedChannel.addMessage(msg1);
        ownedChannel.addMessage(msg2);
        ObjectMapper mapper = new ObjectMapper();
        FileHandle handler = new FileHandle();
        handler.writeJSONToFile("channel",ownedChannel);
        handler.writeJSONToFile("users",listUser);
        List<User> listUserRead = handler.readJSONFileToList("users",User.class);
        /*for (User user : listUserRead){
            System.out.println(new ObjectMapper().writeValueAsString(user));
        }*/

        OwnedChannel channelRead = (OwnedChannel) handler.readJSONFileToObject("channel",OwnedChannel.class);

        //String path = System.getProperty("user.dir") + "/projet-lo23a20d1/resource/channel.json";
        //OwnedChannel channelRead = mapper.readValue(Paths.get(path).toFile(), OwnedChannel.class);
        System.out.println(channelRead.getMessages().get(0).getMessage());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
