package IHMChannel.tools;

import common.sharedData.UserLite;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;

public class AddMemberCell extends ListCell<UserLite> {

    @FXML
    Button addBtn;

    @FXML
    ImageView profilePicture;

    @FXML
    Text username;

    public AddMemberCell(){
        loadFXML();
    }

    private void loadFXML(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddMemberCell.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(UserLite item, boolean empty) {
        super.updateItem(item, empty);

        if(empty) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
        else {
            username.setText(item.getNickName());
            //TODO voir pour l'image
            //profilePicture.setImage(new Image(item.getAvatar()));

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }

    public void addUser(){
        System.out.println("ajout de l'user");
        //TODO implémenter la méthode
        //Il faut récupérer l'utilisateur concerné /!\
    }
}
