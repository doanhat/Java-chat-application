package IHMChannel.tools.AddMemberCellFactory;

import IHMChannel.controllers.ChannelController;
import IHMChannel.controllers.SendInvitePopUpController;
import common.shared_data.Channel;
import common.shared_data.UserLite;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.management.RuntimeErrorException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddMemberCell extends ListCell<UserLite> {

    @FXML
    Button addBtn;
    @FXML
    ImageView profilePicture;
    @FXML
    Text username;

    UserLite user;

    ChannelController channelController;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * Constructeur
     * Appelé par la CellFactory
     */
    public AddMemberCell(ChannelController channelController){
        loadFXML();
        this.channelController = channelController;
    }

    public void initialize(){
        //Action sur le bouton
        addBtn.setOnAction((ActionEvent event) -> {
            FXMLLoader fxmlLoader =
                    new FXMLLoader(getClass().getResource("../../views/SendInvitePopUp.fxml"));
            Parent root = null;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Channel channel = this.channelController.getCurrentChannel();

            //Pop-up init
            SendInvitePopUpController sendInvitePopUpController = fxmlLoader.getController();
            sendInvitePopUpController.setText(user,channel);
            Stage popUpWindow = new Stage();
            popUpWindow.initModality(Modality.APPLICATION_MODAL);
            popUpWindow.setTitle("Envoyer une invitation");
            popUpWindow.setScene(new Scene(root));
            popUpWindow.setResizable(false);
            popUpWindow.show();

            /**
             * Gestion du clic sur le bouton annuler
             */
            sendInvitePopUpController.getCancelBtn().setOnAction(e -> popUpWindow.close());

            /**
             * Gestion du clic sur le bouton confirmer
             */
            sendInvitePopUpController.getSendInviteBtn().setOnAction(e -> {
                logger.log(Level.INFO, String.format("You have invited {} to channel {}\nMessage : {}", user.getNickName(), channel.getName(), sendInvitePopUpController.getInvitationMessage()));
                channelController.getIhmChannelController().getInterfaceToCommunication().sendInvite(user,channel, sendInvitePopUpController.getInvitationMessage());
                popUpWindow.close();
            });
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

    /**
     * Méthode qui permet de compléter la cellule de liste
     * Impliquée par l'utilisation de Cell Factory
     * @param item
     * @param empty
     */
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
            if(!item.getAvatar().equals("")){
                profilePicture.setImage(new Image(item.getAvatar()));
            }else{ //picture by default
                profilePicture.setImage(new Image("IHMChannel/tools/AddMemberCellFactory/avatar.jpg", 30, 30, true, false));
            }

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }
}
