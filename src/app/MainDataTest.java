package app;

import Data.resourceHandle.FileHandle;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.sharedData.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

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

        OwnedChannel ownedChannel = new OwnedChannel(
                UUID.randomUUID(),
                "chan1",
                user1Lite,
                "desc",
                Visibility.PRIVATE
        );
        ownedChannel.addUser(user2);

        Message msg1 = new Message(
                UUID.randomUUID(),
                "Yooo!",
                user1Lite
        );
        Message msg2 = new Message(
                UUID.randomUUID(),
                "Yoooooo!",
                user2Lite
        );

        ownedChannel.addMessage(msg1);
        ownedChannel.addMessage(msg2);

        ObjectMapper mapper = new ObjectMapper();
        FileHandle handler = new FileHandle();
        handler.writeJSONToFile("channel",ownedChannel);
    }

    public class Event {
        public String name;

        @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        public Date eventDate;

        public Event(String party, Date date) {
            this.name = party;
            this.eventDate = date;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
