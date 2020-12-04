package IHMChannel.tools.AddMemberCellFactory;

import common.sharedData.UserLite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
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

    UserLite user;

    /**
     * Constructeur
     */
    public AddMemberCell(){
        loadFXML();
    }

    public void initialize(){
        //Action sur le bouton
        addBtn.setOnAction((ActionEvent event) -> {
            //TODO appeler l'interface pour ajouter l'utilisateur au channel
            System.out.println("Ajout de : " + user.getNickName());
            addBtn.setDisable(true); //désactivation du bouton : on ajoute une seule fois l'utilisateur
        });
    }

    /**
     * Chargement du FXML "AddMemberCell" qui contient la structure d'une liste de la liste
     */
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
        this.user = item;

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
}
