package app;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MainDataTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        //df.setTimeZone(TimeZone.getTimeZone("UTC"));

        String toParse = "20-12-2014";
        Date date = df.parse(toParse);
        Event event = new Event("party", date);

        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(event);
        System.out.printf(result);
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
